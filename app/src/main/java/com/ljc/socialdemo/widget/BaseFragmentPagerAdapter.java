package com.ljc.socialdemo.widget;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by chejiangwei on 2017/7/5.
 * Describe:
 */

public class BaseFragmentPagerAdapter extends FragmentStatePagerAdapter {


    List<? extends Fragment> fragments;
    List<String>             titles;

    public BaseFragmentPagerAdapter(FragmentManager fragmentManager, List<Fragment> list) {
        this(fragmentManager, list,null);
    }
    // 标题数组
    public BaseFragmentPagerAdapter(FragmentManager fm, List<? extends Fragment> fragments, List<String> titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }



    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titles== null || titles.size() == 0) return super.getPageTitle(position);
        return titles.get(position);
    }



    @Override
    public int getItemPosition(Object object) {
        //此Item不再显示
        return POSITION_NONE;
    }

}
