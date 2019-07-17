package com.ljc.socialdemo.db.dao;

import android.support.annotation.NonNull;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by 18075 on 2018/11/9.
 */

@Entity
public class UserInfo {
    @NonNull
    private String name;
    private String head;
    @NonNull
    private String sex;
    private int age;
    private String birthday;
    private String hobby;
    @Id
    private Long userId;
    @Generated(hash = 338937373)
    public UserInfo(@NonNull String name, String head, @NonNull String sex, int age,
            String birthday, String hobby, Long userId) {
        this.name = name;
        this.head = head;
        this.sex = sex;
        this.age = age;
        this.birthday = birthday;
        this.hobby = hobby;
        this.userId = userId;
    }
    @Generated(hash = 1279772520)
    public UserInfo() {
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getHead() {
        return this.head;
    }
    public void setHead(String head) {
        this.head = head;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public int getAge() {
        return this.age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getBirthday() {
        return this.birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public String getHobby() {
        return this.hobby;
    }
    public void setHobby(String hobby) {
        this.hobby = hobby;
    }
    public Long getUserId() {
        return this.userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
