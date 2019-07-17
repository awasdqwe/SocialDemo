package com.ljc.socialdemo.db.dao;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * Created by 18075 on 2018/11/13.
 */

@Entity
public class Commen {
    @NonNull
    private String name;//评论的名字
    @NonNull
    private String content;//评论的内容
    private String header;//评论人呢的头像
    private String createTime; //评论的时间
    private Long   discoveId;//对应的说说id
    @Id(autoincrement = true)
    private Long   id;

    @Generated(hash = 683602546)
    public Commen(@NonNull String name, @NonNull String content, String header,
                  String createTime, Long discoveId, Long id) {
        this.name = name;
        this.content = content;
        this.header = header;
        this.createTime = createTime;
        this.discoveId = discoveId;
        this.id = id;
    }

    @Generated(hash = 549390411)
    public Commen() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHeader() {
        return this.header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Long getDiscoveId() {
        return this.discoveId;
    }

    public void setDiscoveId(Long discoveId) {
        this.discoveId = discoveId;
    }
}
