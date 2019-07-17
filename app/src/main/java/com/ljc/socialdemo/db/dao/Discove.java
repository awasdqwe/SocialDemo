package com.ljc.socialdemo.db.dao;

import android.support.annotation.NonNull;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.util.List;

/**
 * Created by 18075 on 2018/11/13.
 */

@Entity
public class Discove {
    @NonNull
    private String content; //内容
    private String name; //发布人的名字
    private String header; //发布人的头像
    private String createTime; //发布的时间
    private String imgs; //图片  {1.jpg,2.jpg}
    private int    commentNum; //评论数
    private String commentId; //评论的用户信息及内容   {张三,李四}  未使用
    private boolean isZan;//点赞        {张三,李四}
    private int    zanNum; //点赞数
    private Long userId;//用户的id
    @Id(autoincrement = true) //自动增长
    private Long   id;
    @Generated(hash = 1453871298)
    public Discove(@NonNull String content, String name, String header,
            String createTime, String imgs, int commentNum, String commentId,
            boolean isZan, int zanNum, Long userId, Long id) {
        this.content = content;
        this.name = name;
        this.header = header;
        this.createTime = createTime;
        this.imgs = imgs;
        this.commentNum = commentNum;
        this.commentId = commentId;
        this.isZan = isZan;
        this.zanNum = zanNum;
        this.userId = userId;
        this.id = id;
    }
    @Generated(hash = 945798038)
    public Discove() {
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
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
    public String getImgs() {
        return this.imgs;
    }
    public void setImgs(String imgs) {
        this.imgs = imgs;
    }
    public int getCommentNum() {
        return this.commentNum;
    }
    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }
    public String getCommentId() {
        return this.commentId;
    }
    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }
    public boolean getIsZan() {
        return this.isZan;
    }
    public void setIsZan(boolean isZan) {
        this.isZan = isZan;
    }
    public int getZanNum() {
        return this.zanNum;
    }
    public void setZanNum(int zanNum) {
        this.zanNum = zanNum;
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
