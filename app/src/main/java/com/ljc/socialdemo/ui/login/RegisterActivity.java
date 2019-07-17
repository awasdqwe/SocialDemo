package com.ljc.socialdemo.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ljc.socialdemo.Constant;
import com.ljc.socialdemo.R;
import com.ljc.socialdemo.SociaDemoApplication;
import com.ljc.socialdemo.db.dao.User;
import com.ljc.socialdemo.db.dao.UserInfo;
import com.ljc.socialdemo.ui.MainActivity;
import com.ljc.socialdemo.utils.LogUtils;
import com.ljc.socialdemo.utils.PreferencesUtils;
import com.ljc.socialdemo.widget.MyToolBar;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import greendao.DaoSession;
import greendao.UserDao;
import greendao.UserInfoDao;

/**
 * Created by 18075 on 2018/11/8.
 */

public class RegisterActivity extends Activity {
    @BindView(R.id.toolbar)
    MyToolBar toolbar;
    @BindView(R.id.user_password_first)
    EditText  userPasswordFirst;
    @BindView(R.id.user_show_password_first)
    ImageView userShowPasswordFirst;
    @BindView(R.id.user_password_scend)
    EditText  userPasswordScend;
    @BindView(R.id.user_show_password_scend)
    ImageView userShowPasswordScend;
    @BindView(R.id.user_login_next)
    Button    userLoginNext;
    @BindView(R.id.user_account)
    EditText  userAccount;
    private boolean eyeOpen      = true;
    private boolean eyeOpenScned = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        toolbar.setTitleText("用户注册").setTitleLeftClickListener(v -> finish());
    }

    @OnClick({R.id.user_show_password_first, R.id.user_show_password_scend, R.id.user_login_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_show_password_first:
                if (!eyeOpen) {
                    userPasswordFirst.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    userShowPasswordFirst.setImageResource(R.drawable.login_icon_hidepsw);
                    eyeOpen = true;
                } else {
                    //明文
                    userPasswordFirst.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    userShowPasswordFirst.setImageResource(R.drawable.login_icon_showpsw);
                    eyeOpen = false;
                }
                break;
            case R.id.user_show_password_scend:
                if (!eyeOpenScned) {
                    userPasswordScend.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    userShowPasswordScend.setImageResource(R.drawable.login_icon_hidepsw);
                    eyeOpenScned = true;
                } else {
                    //明文
                    userPasswordScend.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    userShowPasswordScend.setImageResource(R.drawable.login_icon_showpsw);
                    eyeOpenScned = false;
                }
                break;
            case R.id.user_login_next:
                //判断是否有该用户
                String account = userAccount.getText().toString().trim();
                String passwordFirst = userPasswordFirst.getText().toString();
                String passwordSecond = userPasswordScend.getText().toString();
                if (hasUser(account, passwordFirst, passwordSecond)) {
                    //是新用户,插入数据
                    User user = new User(null, account, passwordFirst);

                    DaoSession daoSession = SociaDemoApplication.getInstances().getDaoSession();
                    UserDao userDao = daoSession.getUserDao();
                    userDao.insert(user);

                    //查询数据
                    User unique = userDao.queryBuilder().where(UserDao.Properties.Account.eq(account)).build().unique();
                    LogUtils.d("存储用户后: 查询出来的数据:   "+unique.toString());

                    //插入用户信息
                    UserInfoDao userInfoDao = daoSession.getUserInfoDao();
                    UserInfo userInfo = new UserInfo("WeiLang_" + UUID.randomUUID(),"","未完善",-1,"未完善","未完善",unique.getUserId());
                    userInfoDao.insert(userInfo);

                    //持续化存储用户
                    PreferencesUtils.saveBeanByFastJson(this, User.class.getSimpleName(), unique);
                    PreferencesUtils.putLong(this, Constant.USERID_KEY, unique.getUserId());
                    //直接跳主界面
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }
                break;
        }
    }

    private boolean hasUser(String account, String passwordFirst, String passwordSecond) {
        if (TextUtils.isEmpty(account)) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(passwordFirst) || TextUtils.isEmpty(passwordSecond)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!passwordFirst.equals(passwordSecond)) {
            Toast.makeText(this, "两次密码不一致,请重新输入", Toast.LENGTH_SHORT).show();
            userPasswordFirst.setText("");
            userPasswordScend.setText("");
            return false;
        }
        User unique = SociaDemoApplication.getInstances().getDaoSession().getUserDao().queryBuilder().where(UserDao.Properties.Account.eq(account)).build().unique();
        if (unique!= null){
            Toast.makeText(this, "用户名已存在", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
