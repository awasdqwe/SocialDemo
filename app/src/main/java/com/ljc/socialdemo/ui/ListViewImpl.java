package com.ljc.socialdemo.ui;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ljc.socialdemo.R;
import com.ljc.socialdemo.SociaDemoApplication;
import com.ljc.socialdemo.utils.UIUtils;

import org.greenrobot.greendao.query.LazyList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by chejiangwei on 2017/10/9.
 * Describe:负责托管列表的组件相关
 */

public class ListViewImpl implements IListView {
    private IListDao           iListDao;
    private RecyclerView       recyclerView;
    private BaseQuickAdapter   adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean realLoad      = false;
    private int     count         = 1; //数据多页加载计数器

    /**
     * @param showFoot true 不显示脚布局,false显示
     */
    public void setShowFoot(boolean showFoot) {
        if (showFoot) {
            footView.setVisibility(View.GONE);
        } else {
            footView.setVisibility(View.VISIBLE);
        }
    }


    private boolean isFetch;//是否是下拉下载更多(默认上拉加载更多)
    private View    footView;

    public ListViewImpl(IListDao iListDao, RecyclerView recyclerView, BaseQuickAdapter adapter, SwipeRefreshLayout swipeRefreshLayout) {
        this(iListDao, recyclerView, adapter, swipeRefreshLayout,  false);
    }

    public ListViewImpl(IListDao iListDao, RecyclerView recyclerView, BaseQuickAdapter adapter, SwipeRefreshLayout swipeRefreshLayout, boolean realLoad) {
        this.iListDao = iListDao;
        this.recyclerView = recyclerView;
        this.adapter = adapter;
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.realLoad = realLoad;
        enableSwipe(false);
        adapter.bindToRecyclerView(recyclerView);
        footView = LayoutInflater.from(SociaDemoApplication.mAppContext).inflate(R.layout.foot_view, null);
        adapter.setFooterView(footView);
        setShowFoot(true);
        count = 1;
    }

    @Override
    public void onRefresh() {
        List list = new ArrayList<>();
        List objects = iListDao.getObjects();
        boolean isLast = false;
        if (realLoad) {
            list.addAll(objects);
            fillData(isLast, list, true);
        } else {
            if (objects.size() <= 20) {
                isLast = true;  //数据没有20条,不能加载更多
                list.addAll(objects);
            } else {
                //超过20条,获取前20条数据
                for (int i = 0; i < objects.size(); i++) {
                    if (i < 20) {
                        list.add(objects.get(i));
                    } else break;
                }
            }
            fillData(isLast, list, true);
        }
    }

    public List queryData() {
        return null;
    }

    @Override
    public void onLoadMore() {
        List list = new ArrayList<>();
        List objects = iListDao.getObjects();
        boolean isLast = false;
        if (realLoad) {
            //真的有数据去加载
            List c = queryData();
            if (c != null && c.size() > 0)
                list.addAll(c);
            else
                isLast = true;
        } else {
            if ((objects.size() / 20 + 0.5f) <= count) {
                isLast = true;  //数据除以20小于或等于计数器,不能加载更多   例如: 55/20+0.5 = 3.25 < 4
            } else {
                count++;  //可以分页继续加载
            }
            for (int i = 0; i < objects.size(); i++) {
                if (i < 20 * (count) && i > 20 * (count - 1)) {
                    list.add(objects.get(i));
                }
            }
        }
        fillData(isLast, list, false);

    }

    public void fillData(boolean isLast, List list, boolean isNew) {
        if (list == null) return;
        //如果是下拉加载更多，数据要倒序
        if (isFetch) {
            Collections.reverse(list);
        }
        if (isNew) {
            adapter.setNewData(list);
            if (isFetch) recyclerView.smoothScrollToPosition(list.size() - 1);
        } else {
            if (isFetch) adapter.addData(0, list);
            else
                adapter.addData(list);
            loadMoreComplete();
        }
        if (isLast) {
            setShowFoot(false);
            if (isFetch)
                adapter.setUpFetchEnable(false);
            else
                adapter.loadMoreEnd(true);
        }
    }

    private void loadMoreComplete() {
        if (isFetch)
            adapter.setUpFetching(false);
        else
            adapter.loadMoreComplete();
    }

    /**
     * 默认初始化
     */
    public ListViewImpl defaultInit() {
        enableSwipe(true);
        enableLoadMore(true);
        return this;
    }

    /**
     * 默认初始化,并刷新
     */
    public ListViewImpl defaultRefresh() {
        defaultInit();
        onRefresh();
        return this;
    }

    /**
     * 是否可以下拉刷新
     *
     * @param enable
     */
    public ListViewImpl enableSwipe(boolean enable) {
        if (swipeRefreshLayout == null) return this;
        swipeRefreshLayout.setEnabled(enable);
        if (enable) {
            swipeRefreshLayout.setOnRefreshListener(() -> {
                ListViewImpl.this.onRefresh();
                new Handler().postDelayed(() -> {
                    swipeRefreshLayout.setRefreshing(false);
                }, 2000);
            });
        }
        return this;
    }

    /**
     * 设置上拉加载
     *
     * @param isFetch
     * @return
     */
    public ListViewImpl fetch(boolean isFetch) {
        this.isFetch = isFetch;
        return this;
    }


    /**
     * 是否可以上拉加载
     *
     * @param enable
     */
    public ListViewImpl enableLoadMore(boolean enable) {
        if (isFetch) {
            adapter.setUpFetchEnable(enable);
            if (enable) {
                adapter.setUpFetchListener(() -> {
                    adapter.setUpFetching(true);
                    ListViewImpl.this.onLoadMore();
                });
            } else {
                adapter.setUpFetchListener(null);
            }
            return this;
        }


        adapter.setEnableLoadMore(enable);
        if (enable) {
            adapter.setOnLoadMoreListener(() -> ListViewImpl.this.onLoadMore(), recyclerView);
        } else {
            adapter.setOnLoadMoreListener(null, recyclerView);
        }
        return this;
    }

}
