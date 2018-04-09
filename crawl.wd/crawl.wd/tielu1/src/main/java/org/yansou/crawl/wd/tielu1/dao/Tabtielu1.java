package org.yansou.crawl.wd.tielu1.dao;


import org.yansou.crawl.wd.core.DBField;
import org.yansou.crawl.wd.core.DBRowKey;
import org.yansou.crawl.wd.core.DBTable;



@DBTable("tab_tielu1")
public class Tabtielu1 {
    //ID
    @DBField(name = "id", type = "int unsigned not null auto_increment primary key")
    public long id;
    @DBField(name = "titile", type = "varchar(800)")
    public String titile;
    //正文
    @DBField(name = "context", type = "longtext")
    public String context;
    //备注
    @DBField(name = "remark", type = "varchar(100)")
    public String remark;
    //锚文本
    @DBField(name = "anchor_text", type = "longtext")
    public String anchor_text;
    //URL
    @DBRowKey
    @DBField(name = "url", type = "VARCHAR(255) DEFAULT NULL")
    public String url;

    @DBField(name = "update_time", type = "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    public String update_time;
    @DBField(name = "insert_time", type = "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP")
    public String insert_time;
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
