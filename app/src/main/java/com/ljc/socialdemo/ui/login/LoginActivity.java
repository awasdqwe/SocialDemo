package com.ljc.socialdemo.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ljc.socialdemo.Constant;
import com.ljc.socialdemo.R;
import com.ljc.socialdemo.SociaDemoApplication;
import com.ljc.socialdemo.db.dao.User;
import com.ljc.socialdemo.ui.MainActivity;
import com.ljc.socialdemo.utils.LogUtils;
import com.ljc.socialdemo.utils.PreferencesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import greendao.DaoSession;
import greendao.UserDao;

/**
 * Created by 18075 on 2018/11/8.
 */

public class LoginActivity extends Activity {

    @BindView(R.id.user_phone_et)
    EditText  userPhoneEt;
    @BindView(R.id.clear_phone_text)
    ImageView clearPhoneText;
    @BindView(R.id.user_password_et)
    EditText  userPasswordEt;
    @BindView(R.id.clear_psw_text)
    ImageView clearPswText;
    @BindView(R.id.user_show_password)
    ImageView userShowPassword;
    @BindView(R.id.user_login_btn)
    Button    userLoginBtn;
    @BindView(R.id.user_register_btn)
    Button    userRegisterBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initEvent();
    }

    private void initEvent() {
        userPhoneEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String str = editable.toString();
                if (null != str && str.length() > 0) {
                    clearPhoneText.setVisibility(View.VISIBLE);
                } else {
                    clearPhoneText.setVisibility(View.GONE);
                }
            }
        });

        userPasswordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String str = editable.toString();
                if (null != str && str.length() > 0) {
                    clearPswText.setVisibility(View.VISIBLE);
                    userShowPassword.setVisibility(View.VISIBLE);
                } else {
                    clearPswText.setVisibility(View.GONE);
                    userShowPassword.setVisibility(View.GONE);
                }
            }
        });
    }

    @OnClick({R.id.clear_phone_text, R.id.clear_psw_text, R.id.user_show_password, R.id.user_login_btn, R.id.user_register_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.clear_phone_text:
                userPhoneEt.setText("");
                break;
            case R.id.clear_psw_text:
                userPasswordEt.setText("");
                break;
            case R.id.user_show_password:
                userPasswordEt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                break;
            case R.id.user_login_btn:
                //查询数据库,效验账号密码是否正确
                verifyUser();
                break;
            case R.id.user_register_btn:
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
                break;
        }
    }

    /**
     * 效验用户
     */
    private void verifyUser() {
        DaoSession daoSession = SociaDemoApplication.getInstances().getDaoSession();
        UserDao userDao = daoSession.getUserDao();
        String account = userPhoneEt.getText().toString().trim();
        User user = userDao.queryBuilder().where(UserDao.Properties.Account.eq(account)).build().unique();
        if (user != null) {
            LogUtils.d(user.toString());
            startActivity(new Intent(this,MainActivity.class));
            PreferencesUtils.saveBeanByFastJson(this, User.class.getSimpleName(), user);
            PreferencesUtils.putLong(this, Constant.USERID_KEY, user.getUserId());
        }else {
            Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
        }
    }
}
