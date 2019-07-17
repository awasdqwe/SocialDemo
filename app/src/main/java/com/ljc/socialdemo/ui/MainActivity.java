package com.ljc.socialdemo.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ljc.socialdemo.Constant;
import com.ljc.socialdemo.R;
import com.ljc.socialdemo.db.Test;
import com.ljc.socialdemo.ui.discove.DiscoveFragment;
import com.ljc.socialdemo.ui.discove.PubActivity;
import com.ljc.socialdemo.ui.home.HomeFragment;
import com.ljc.socialdemo.ui.me.MineFragment;
import com.ljc.socialdemo.ui.message.MessageFragment;
import com.ljc.socialdemo.utils.PreferencesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fl_fragments)
    FrameLayout  flFragments;
    @BindView(R.id.tv_home)
    TextView     tvHome;
    @BindView(R.id.tv_message)
    TextView     tvMessage;
    @BindView(R.id.iv_pub)
    ImageView    ivPub;
    @BindView(R.id.tv_discove)
    TextView     tvDiscove;
    @BindView(R.id.tv_me)
    TextView     tvMe;
    @BindView(R.id.rg_bottom)
    LinearLayout rgBottom;
    @BindView(R.id.bottom)
    LinearLayout bottom;
    private HomeFragment    homeFragment;
    private MessageFragment messageFragment;
    private DiscoveFragment discoveFragment;
    private MineFragment    mineFragment;

    private static final int showTabNum      = 0;
    private              int currentTabIndex = showTabNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        openTabFragemt(currentTabIndex);

        boolean isInsert = PreferencesUtils.getBoolean(this, Constant.INSERT_DISCOVE);
        try {
            if (!isInsert) {
                //插入数据
                Test.insertAutoDiscove();
                PreferencesUtils.putBoolean(this, Constant.INSERT_DISCOVE,true);
            }
        } catch (Exception e) {
        }
    }


    public void setCheckedFragment(int index) {
        if (index == 0) {
            tvHome.setSelected(true);
            tvMessage.setSelected(false);
            tvDiscove.setSelected(false);
            tvMe.setSelected(false);
        } else if (index == 1) {
            tvMessage.setSelected(true);
            tvHome.setSelected(false);
            tvDiscove.setSelected(false);
            tvMe.setSelected(false);
        } else if (index == 2) {
            tvDiscove.setSelected(true);
            tvHome.setSelected(false);
            tvMessage.setSelected(false);
            tvMe.setSelected(false);
        } else if (index == 3) {
            tvMe.setSelected(true);
            tvHome.setSelected(false);
            tvMessage.setSelected(false);
            tvDiscove.setSelected(false);
        }
    }


    public void openTabFragemt(int index) {
        FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
        hideFragment(trx);
        switch (index) {
            case 0:
                if (null == homeFragment) {
                    homeFragment = HomeFragment.newsIntance();
                    trx.add(R.id.fl_fragments, homeFragment);
                } else {
                    trx.show(homeFragment);
                }
                setCheckedFragment(index);
                break;
            case 1:
                if (null == messageFragment) {
                    messageFragment = MessageFragment.newsIntance();
                    trx.add(R.id.fl_fragments, messageFragment);
                } else {
                    trx.show(messageFragment);
                }
                setCheckedFragment(index);
                break;
            case 2:
                if (null == discoveFragment) {
                    discoveFragment = DiscoveFragment.newsIntance();
                    trx.add(R.id.fl_fragments, discoveFragment);
                } else {
                    trx.show(discoveFragment);
                }
                setCheckedFragment(index);
                break;
            case 3:
                if (null == mineFragment) {
                    mineFragment = MineFragment.newsIntance();
                    trx.add(R.id.fl_fragments, mineFragment);
                } else {
                    trx.show(mineFragment);
                }
                setCheckedFragment(index);
                break;
            default:
                break;

        }
        currentTabIndex = index;
        trx.commitAllowingStateLoss();
    }


    private void hideFragment(FragmentTransaction transaction) {
        if (null != homeFragment) {
            transaction.hide(homeFragment);
        }
        if (null != messageFragment) {
            transaction.hide(messageFragment);
        }

        if (null != discoveFragment) {
            transaction.hide(discoveFragment);
        }

        if (null != mineFragment) {
            transaction.hide(mineFragment);
        }
    }


    @OnClick({R.id.tv_home, R.id.tv_message, R.id.iv_pub, R.id.tv_discove, R.id.tv_me})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_home:
                openTabFragemt(0);
                break;
            case R.id.tv_message:
                openTabFragemt(1);
                break;
            case R.id.iv_pub:
                //跳转发布界面
                startActivity(new Intent(this,PubActivity.class));
                break;
            case R.id.tv_discove:
                openTabFragemt(2);
                break;
            case R.id.tv_me:
                openTabFragemt(3);
                break;
        }
    }
}
