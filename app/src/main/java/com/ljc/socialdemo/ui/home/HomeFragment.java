package com.ljc.socialdemo.ui.home;


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
 * 首页
 */

public class HomeFragment extends BaseFragment {
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.vp)
    ViewPager vp;
    Unbinder unbinder;
    private Fragment1 fragment1;
    private Fragment1 fragment2;
    private Fragment1 fragment3;
    private Fragment1 fragment4;
    private Fragment1 fragment5;
    private Fragment1 fragment6;
    private String[] title = new String[]{"热门","新闻","辟谣","趣味","养身","母婴"};
    private List<Fragment> fragments;

    @Override
    protected void loadData() {
        init();
        vp.setAdapter(new BaseFragmentPagerAdapter(getFragmentManager(), fragments, Arrays.asList(title)));
        vp.setCurrentItem(0);
        tablayout.setupWithViewPager(vp);
    }

    private void init() {
        fragment1 = Fragment1.newsIntance(1);
        fragment2 = Fragment1.newsIntance(2);
        fragment3 = Fragment1.newsIntance(3);
        fragment4 = Fragment1.newsIntance(4);
        fragment5 = Fragment1.newsIntance(5);
        fragment6 = Fragment1.newsIntance(6);
        fragments = Arrays.asList(fragment1, fragment2, fragment3, fragment4, fragment5, fragment6);
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_home;
    }

    public static HomeFragment newsIntance() {
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
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
