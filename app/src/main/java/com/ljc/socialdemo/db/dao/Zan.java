package com.ljc.socialdemo.db.dao;

import android.support.annotation.NonNull;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by 18075 on 2018/11/13.
 */
@Entity
public class Zan {
    @NonNull
    private String name;//赞的名字
    private String header;//赞的头像
    private String createTime; //赞的时间
    private Long   discoveId;//对应的说说id
    private Long userId;//对应的用户   每个用户对每一条说说只能有一个赞
    @Id(autoincrement = true)
    private Long   id;
    @Generated(hash = 1555106731)
    public Zan(@NonNull String name, String header, String createTime,
            Long discoveId, Long userId, Long id) {
        this.name = name;
        this.header = header;
        this.createTime = createTime;
        this.discoveId = discoveId;
        this.userId = userId;
        this.id = id;
    }
    @Generated(hash = 785301865)
    public Zan() {
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getHeader() {
        return this.header;
    }
    public void setHeader(String header) {
        this.header = header;
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
    public Long getUserId() {
        return this.userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }

}
