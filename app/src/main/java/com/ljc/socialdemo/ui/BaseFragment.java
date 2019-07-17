package com.ljc.socialdemo.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 18075 on 2018/11/8.
 */

public abstract class BaseFragment extends Fragment {
    private View     viewRoot;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewRoot = inflater.inflate(layoutId(), container, false);
        return viewRoot;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData();//根据获取的数据来调用showView()切换界面
    }

    /**
     * 根据获取的数据返回，每一个子类的获取数据返回的都不一样，所以要交给子类去完成
     */
    protected abstract void loadData();

    /**
     * 每个子类的布局都不一样
     * @return
     */
    public abstract int layoutId();
}
