package org.yansou.crawl.wd.tielu1.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.yansou.crawl.wd.core.BasicSite;
import org.yansou.crawl.wd.zhaopin.dao.ZhaopinPosition;

public class TestZhaopinPosioion {

    @Test
    public void testApply() {
        ZhaopinPosition chain = new ZhaopinPosition();
        DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
        capabilities.setJavascriptEnabled( false );
        PhantomJSDriver wd = new PhantomJSDriver( capabilities );
        BasicSite site = new BasicSite( wd );
        JSONObject task = JSON.parseObject( "{'url':'http://jobs.51job.com/hangzhou-jgq/91297362.html','pub_time':1111111111111}" );
        chain.apply( task, site );
    }
}
