package org.yansou.crawl.wd.tielu1.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.yansou.common.util.SleepUtils;
import org.yansou.crawl.wd.core.BasicSite;
import org.yansou.crawl.wd.core.ChainHandler;
import org.yansou.crawl.wd.core.Saver;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

public class Tielu1WdCrawl {
	public static void main(String[] args) {
//		DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
//		// 关闭JS。
//		capabilities.setJavascriptEnabled(false);
//		PhantomJSDriver wd = new PhantomJSDriver(capabilities);
		System.setProperty("webdriver.gecko.driver", "./geckodriver.exe");
		FirefoxDriver wd = new FirefoxDriver();

		new Tielu1WdCrawl().run(wd);
	}

	public void run(RemoteWebDriver wd) {
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setUrl(
				"jdbc:mysql://localhost:3306/guotest?useUnicode=true&characterEncoding=utf-8");
		dataSource.setUser("root");
		dataSource.setPassword("root");
		BasicSite site = new MyBasicSite(wd);

		site.setUpdate(true);
		site.offerTask(JSON.parseObject(
				"{'_STATUS_':{'SEED':true,'LIST':true},'url':'http://zgtlgcjz.cn/list.php?fid=142&page=1'}"));
		List<ChainHandler> chainHandlerList = Arrays.asList(new Tielu1List(), new Tielu1Parse(),new ZhaopinPosition());
		for (;;) {
			JSONObject task = site.poolTask();
			if (null == task) {
				// 没有更多任务
				break;
			}
			for (int i = 0; i < chainHandlerList.size(); i++) {
				ChainHandler chainHandler = chainHandlerList.get(i);
				if (Boolean.FALSE.equals(  chainHandler.apply(task, site))) {
					break;
				}
			}
			int sleeptime = RandomUtils.nextInt(0, 2 );
			System.out.println("sleep time:" + sleeptime);
			SleepUtils.sleep(sleeptime);
		}
	}

	private static class MyBasicSite extends BasicSite {
		public MyBasicSite(RemoteWebDriver wd, Saver saver) {
			super(wd, saver);
		}

		public MyBasicSite(RemoteWebDriver wd) {
			super(wd);
		}

		private Map<String, LinkedBlockingQueue<JSONObject>> queueMap = new HashMap<>();

		@Override
		public void offerTask(JSONObject task) {
			String type = task.getString("TYPE");
			LinkedBlockingQueue<JSONObject> queue = queueMap.getOrDefault(type, new LinkedBlockingQueue<JSONObject>());
			queueMap.put(type, queue);
			queue.offer(task);
		}

		@Override
		public JSONObject poolTask() {
			LinkedBlockingQueue<JSONObject> maxQueue = null;
			//从很多个map中找个一个queue.size最大的来作为出队 队列？？？？
			for (LinkedBlockingQueue<JSONObject> queue : queueMap.values()) {
				if (null == maxQueue || queue.size() > maxQueue.size()) {
					maxQueue = queue;
				}
			}
			if (null != maxQueue) {
				return maxQueue.poll();
			}
			return null;
		}
	}
}
