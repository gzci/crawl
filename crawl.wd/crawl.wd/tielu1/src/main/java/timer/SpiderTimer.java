package timer;

//添加quartz依赖
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

public class SpiderTimer {
	public static void main(String[] args) {
		try {
			//1获取一个默认调度器
			Scheduler defaultScheduler = StdSchedulerFactory.getDefaultScheduler();
			//2开启调度器
			defaultScheduler.start();
			
			//封装要调度的任务
			String simpleName = SpiderTaobao.class.getSimpleName();
			JobDetail jobDetail = new JobDetail(simpleName,Scheduler.DEFAULT_GROUP, SpiderTaobao.class);
			//表示设置定时操作(每隔5秒执行一次)
			CronTrigger trigger = new CronTrigger(simpleName,Scheduler.DEFAULT_GROUP, "0 0/1 * * * ?");
			//3执行调度任务
			defaultScheduler.scheduleJob(jobDetail, trigger);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}