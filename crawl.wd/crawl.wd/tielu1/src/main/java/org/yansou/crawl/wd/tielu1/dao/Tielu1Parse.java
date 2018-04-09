package org.yansou.crawl.wd.tielu1.dao;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.yansou.common.crawl.util.JSOUPUtils;
import org.yansou.common.crawl.util.WdUtils;
import org.yansou.crawl.wd.core.ChainHandler;
import org.yansou.crawl.wd.core.Site;

public class Tielu1Parse implements ChainHandler {

	EntityDao dao = new EntityDao(Tabtielu1.class);
	EntityDao pDao = new EntityDao(Tab51JobPosition.class);

	@Override
	public Boolean apply(JSONObject task, Site site) {
		String url = task.getString("url");
		System.out.println("content:"+url);
		if (StringUtils.isNotBlank(url) && url.matches("http://[^/]+/bencandy.+")) {
			site.wd().get(url);
			WdUtils.waitPageLoad(site.wd());
			String pageSource = site.wd().getPageSource();
			Document dom = Jsoup.parse(pageSource, site.wd().getCurrentUrl());

			// 获得公司信息并入库
			Tabtielu1 t = new Tabtielu1();
			t.url = url;
			t.anchor_text = pageSource;
			t.context = JSOUPUtils.find(dom, ".rnr2").map(e -> e.text()).orElse(null);
			t.remark ="招标投标-施工招标";
			t.titile=  JSOUPUtils.find(dom, ".rnr1").map(e -> e.text()).orElse(null);
//			t.companyAddress = JSOUPUtils.find(dom, ".fp").map(e -> e.text()).orElse(null);
//			t.companyNature = JSOUPUtils.find(dom, ".ltype").map(e -> e.text())
//					.map(txt -> RegexUtils.regex("[^\\|]+", txt, 0)).map(txt -> txt.replaceAll("[^\u4e00-\u9fa5]", ""))
//					.orElse(null);
//			t.companyScale = JSOUPUtils.find(dom, ".ltype").map(e -> e.text())
//					.map(txt -> RegexUtils.regex("\\|([^\\|]+)", txt, 1)).map(txt -> txt).orElse(null);
//			t.companyScopeOfBusiness = JSOUPUtils.find(dom, ".ltype").map(e -> e.text())
//					.map(txt -> RegexUtils.regex("\\|([^\\|]+)|([^\\|]+)", txt, 2)).orElse(null);
//			t.companyIntroduction = JSOUPUtils.find(dom, ".con_msg").map(e -> e.html()).orElse(null);
			// 集中检查字段

			if (dao.isInsert(t.getUrl())) {
				dao.ins(t);
			}
		}
		return true;
	}

}
