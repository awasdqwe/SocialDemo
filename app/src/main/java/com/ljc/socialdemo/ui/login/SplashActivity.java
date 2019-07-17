package com.ljc.socialdemo.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.TextView;

import com.ljc.socialdemo.Constant;
import com.ljc.socialdemo.R;
import com.ljc.socialdemo.db.Test;
import com.ljc.socialdemo.db.dao.User;
import com.ljc.socialdemo.ui.MainActivity;
import com.ljc.socialdemo.utils.PreferencesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 18075 on 2018/11/8.
 */

public class SplashActivity extends Activity {
    @BindView(R.id.splash)
    TextView splash;
    @BindView(R.id.tv_seconds)
    TextView tvSeconds;

    private int     time    = 5;
    private boolean isStart = false;

    private Handler handler = new Handler();

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (time <= 0) {
                isStart = true;
                toHome();
                return;
            }
            tvSeconds.setText("跳过(" + --time + ")");
            handler.postDelayed(runnable, 1000);
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        tvSeconds.setText("跳过(" + time + ")");
        handler.postDelayed(runnable, 1000);
        boolean isInsert = PreferencesUtils.getBoolean(this, Constant.INSERT_MESSAGE);
        try {
            if (!isInsert) {
                //插入数据
                Test.insertMessage();
                Test.insertNotification();
                 PreferencesUtils.putBoolean(this, Constant.INSERT_MESSAGE,true);
            }
        } catch (Exception e) {
        }
    }

    @OnClick(R.id.tv_seconds)
    public void onViewClicked() {
        if (time > 0 && !isStart) {
            toHome();
            handler.removeCallbacksAndMessages(null);
        }
    }


    private void toHome() {
        User beanByFastJson = PreferencesUtils.getBeanByFastJson(this, User.class.getSimpleName(), "", User.class);
        if (null == beanByFastJson || TextUtils.isEmpty(beanByFastJson.getAccount()) || TextUtils.isEmpty(beanByFastJson.getPassword()))
            startActivity(new Intent(this, LoginActivity.class));
        else
            startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
