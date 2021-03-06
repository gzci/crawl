package org.yansou.crawl.wd.tielu1.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.yansou.common.crawl.util.JSOUPUtils;
import org.yansou.common.crawl.util.WdUtils;
import org.yansou.crawl.wd.core.ChainHandler;
import org.yansou.crawl.wd.core.Site;

import java.util.List;
import java.util.stream.Collectors;

public class Tielu1List implements ChainHandler {
    @Override
    public Boolean apply(JSONObject task, Site site) {
        String url = task.getString("url");
        if (StringUtils.isNotBlank(url) && url.matches("http://[^/]+/list[^/].*")) {
            site.wd().get(url);
            WdUtils.waitPageLoad(site.wd(), 300);
            String pageSource = site.wd().getPageSource(); //获得公司连接
            Document dom = Jsoup.parse(pageSource, site.wd().getCurrentUrl());
            JSOUPUtils.finds(dom, "div.right2a a[href*=bencandy]").forEach(e -> {
                String href = "http://zgtlgcjz.cn/"+e.attr("href");
                JSONObject newTask = new JSONObject();
                newTask.put("url", href);
                newTask.put("TYPE", "COMPLNY");
                site.offerTask(newTask);
                System.out.println("add :" + href);
            });
            String nextUrl = JSOUPUtils.find(dom, "a:matches(下一页)").map(e -> e.attr("href")).orElse(null);
            System.out.println("nexturl:"+nextUrl);
            JSONObject newTask = new JSONObject();
            newTask.put("TYPE", "NEXT");
            newTask.put("url", "http://zgtlgcjz.cn/"+nextUrl);
            site.offerTask(newTask);
        }
        return true;
    }

    /**
     * 登陆逻辑
     *
     * @param wd
     * @param pageSource
     */
    private void autoLogin(RemoteWebDriver wd, String pageSource) {
        if (JSOUPUtils.find(pageSource, ".loginBtn").isPresent()) {
            WebElement element = wd.findElementByClassName("loginBtn");
            element.click();
        }
        for (; ; ) {
            System.out.println("等待扫码登陆...");
            if (WdUtils.waitFindByCss(wd, ".imgShow", 2000).findAny().isPresent()) {
                WdUtils.waitPageLoad(wd, 300);
                System.out.println("登陆完成...");
                break;
            }
        }
    }

}
