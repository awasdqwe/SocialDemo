package com.ljc.socialdemo.ui.message;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ljc.socialdemo.R;
import com.ljc.socialdemo.ui.BaseFragment;
import com.ljc.socialdemo.widget.BaseFragmentPagerAdapter;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 18075 on 2018/11/8.
 * 消息
 */

public class MessageFragment extends BaseFragment {


    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.vp)
    ViewPager vp;
    Unbinder unbinder;
    private String[] title = new String[]{"通知", "消息","通知", "消息"};
    private NotificationFragment notificationFragment;
    private AdviceFragment       adviceFragment;

    @Override
    protected void loadData() {
        notificationFragment = NotificationFragment.newsIntance();
        adviceFragment = AdviceFragment.newsIntance();
        List<Fragment> fragments = Arrays.asList(notificationFragment, adviceFragment);
        vp.setAdapter(new BaseFragmentPagerAdapter(getChildFragmentManager(), fragments, Arrays.asList(title)));
        vp.setCurrentItem(0);
        tablayout.setupWithViewPager(vp);
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_messae;
    }

    public static MessageFragment newsIntance() {
        MessageFragment messageFragment = new MessageFragment();
        return messageFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
