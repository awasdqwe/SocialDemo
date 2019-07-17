package com.ljc.socialdemo.db.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by 18075 on 2018/11/9.
 */


@Entity
public class Notification {
    @Id(autoincrement = true) //自动增长
    private Long   id;
    private String content; //内容

    @Generated(hash = 1587117758)
    public Notification(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    @Generated(hash = 1855225820)
    public Notification() {
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
