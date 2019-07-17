package com.ljc.socialdemo.db.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by 18075 on 2018/11/9.
 */

@Entity
public class Message {
    @Id(autoincrement = true) //自动增长
    private Long   id;
    private String head;//默认的图片
    private String title;;//标题
    private String content;;//内容
    private String time;
    @Generated(hash = 286109766)
    public Message(Long id, String head, String title, String content,
            String time) {
        this.id = id;
        this.head = head;
        this.title = title;
        this.content = content;
        this.time = time;
    }
    @Generated(hash = 637306882)
    public Message() {
    }
    public String getHead() {
        return this.head;
    }
    public void setHead(String head) {
        this.head = head;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    };//日期  2018-10-12
}
