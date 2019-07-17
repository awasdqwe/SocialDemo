package com.ljc.socialdemo.db.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by 18075 on 2018/11/8.
 */

@Entity
public class User {
    @Id(autoincrement = true)//设置自增长
    private Long userId;
    @NotNull
    @Index(unique = true)//设置唯一性
    private String account; //账号
    @NotNull
    private String password;//密码


    @Generated(hash = 664552053)
    public User(Long userId, @NotNull String account, @NotNull String password) {
        this.userId = userId;
        this.account = account;
        this.password = password;
    }


    @Generated(hash = 586692638)
    public User() {
    }
 

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                '}';
    }


    public Long getUserId() {
        return this.userId;
    }


    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public String getAccount() {
        return this.account;
    }


    public void setAccount(String account) {
        this.account = account;
    }


    public String getPassword() {
        return this.password;
    }


    public void setPassword(String password) {
        this.password = password;
    }
}
