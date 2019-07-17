package com.ljc.socialdemo.db;

import android.text.TextUtils;

import com.ljc.socialdemo.Constant;
import com.ljc.socialdemo.SociaDemoApplication;
import com.ljc.socialdemo.db.dao.Discove;
import com.ljc.socialdemo.db.dao.Message;
import com.ljc.socialdemo.db.dao.Notification;
import com.ljc.socialdemo.db.dao.UserInfo;
import com.ljc.socialdemo.utils.LogUtils;
import com.ljc.socialdemo.utils.PreferencesUtils;

import java.util.List;

import greendao.DaoSession;
import greendao.DiscoveDao;
import greendao.MessageDao;
import greendao.NotificationDao;
import greendao.UserInfoDao;

/**
 * Created by 18075 on 2018/11/8.
 */

public class Test {
    public static void insertMessage(){
        DaoSession daoSession = SociaDemoApplication.getInstances().getDaoSession();
        MessageDao messageDao = daoSession.getMessageDao();
        List<Message> messageList = TestDao.getMessageList();
        for (Message message : messageList) {
            messageDao.insert(message);
        }
        LogUtils.d("执行完成: insertMessage");
    }

    public static void insertNotification(){
        DaoSession daoSession = SociaDemoApplication.getInstances().getDaoSession();
        NotificationDao notificationDao = daoSession.getNotificationDao();
        List<Notification> notificationList = TestDao2.getNotificationList();
        for (Notification notification : notificationList) {
            notificationDao.insert(notification);
        }
        LogUtils.d("执行完成: insertNotification");
    }


    /**
     * 需要登录后才能插入
     */
    public static  void  insertAutoDiscove(){
        DaoSession daoSession = SociaDemoApplication.getInstances().getDaoSession();
        long userId = PreferencesUtils.getLong(SociaDemoApplication.mAppContext, Constant.USERID_KEY);
        //查询当前的用户信息
        UserInfo unique = daoSession.getUserInfoDao().queryBuilder().where(UserInfoDao.Properties.UserId.eq(userId)).build().unique();
        if (unique == null) return;
        LogUtils.d("默认发布3条说说:  "+ unique.toString());
        //写入数据
        String name = unique.getName();
        String head = unique.getHead();
        String content = "";
        for (int i = 0; i < 3; i++) {
            content = "我是默认数据,第" +(i+1)+ "条";
            Discove discove = new Discove(content, name, TextUtils.isEmpty(head) ? "" : head, String.valueOf(System.currentTimeMillis()),
                    "", 0, null,  false,0,userId, null);
            daoSession.getDiscoveDao().insert(discove);
        }
    }
}
