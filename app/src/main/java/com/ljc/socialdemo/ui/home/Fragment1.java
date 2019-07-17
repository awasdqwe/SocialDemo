package com.ljc.socialdemo.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.ljc.socialdemo.Constant;
import com.ljc.socialdemo.R;
import com.ljc.socialdemo.db.dao.Message;
import com.ljc.socialdemo.moudle.HomeBanner;
import com.ljc.socialdemo.moudle.HomeContent;
import com.ljc.socialdemo.ui.BaseFragment;
import com.ljc.socialdemo.ui.IListDao;
import com.ljc.socialdemo.ui.ListViewImpl;
import com.ljc.socialdemo.ui.WebActivity;
import com.ljc.socialdemo.ui.home.adapter.HomeAdapter;
import com.ljc.socialdemo.ui.message.adapter.AdviceAdapter;
import com.ljc.socialdemo.utils.LogUtils;
import com.ljc.socialdemo.utils.UIUtils;
import com.ljc.socialdemo.widget.BaseFragmentPagerAdapter;
import com.ljc.socialdemo.widget.NormalPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 18075 on 2018/11/8.
 * 热门
 */

public class Fragment1 extends BaseFragment implements BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.rv)
    RecyclerView       rv;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    Unbinder unbinder;


    HomeBanner  homeBanner;
    HomeContent homeContent;

    private List<String> headerImgs; //所有的头部图片
    private List<String> headerUrls; //所有的头部图片网址
    private HomeAdapter  homeAdapter;
    private int          type;


    @Override
    protected void loadData() {
        headerImgs = new ArrayList<>();
        headerUrls = new ArrayList<>();
        initData();
        initEvent();
    }


    private void initData() {
        type = getArguments().getInt(Constant.HOME_TYPE, 1);
        String assetsBanner = "";
        String assetsContent = "";
        switch (type) {//1.热门, 2.新闻, 3.辟谣, 4.趣味. 5.养身, 6.母婴
            case 1:
                assetsBanner = "remen_banner";
                assetsContent = "remen_content";
                break;
            case 2:
                assetsBanner = "news_banner";
                assetsContent = "news_content";
                break;
            case 3:
                assetsBanner = "biyao_banner";
                assetsContent = "biyao_content";
                break;
            case 4:
                assetsBanner = "quwei_banner";
                assetsContent = "quwe_content";
                break;
            case 5:
                assetsBanner = "yangshen_banner";
                assetsContent = "yangshen_content";
                break;
            case 6:
                assetsBanner = "muyin_banner";
                assetsContent = "muyin_content";
                break;
            default:
                break;
        }

        String banner = UIUtils.getJson(assetsBanner, getActivity());
        String content = UIUtils.getJson(assetsContent, getActivity());
        Gson gson = new Gson();
        homeBanner = gson.fromJson(banner, HomeBanner.class);
        homeContent = gson.fromJson(content, HomeContent.class);

        List<HomeBanner.DataBean> data = homeBanner.getData();
        if (data != null && data.size() > 0) {
            for (HomeBanner.DataBean datum : data) {
                headerImgs.add(datum.getNews_photo());
                headerUrls.add(datum.getNews_url());
            }
        }

        LogUtils.d(homeBanner.toString());
        LogUtils.d(homeContent.getData().toString());
    }

    private void initEvent() {
        homeAdapter = new HomeAdapter();
        rv.setLayoutManager(new LinearLayoutManager(UIUtils.getContext()));
        homeAdapter.setOnItemChildClickListener(this);
        addHeader();
        IListDao iListDao = new IListDao();
        iListDao.setObjects(homeContent.getData());
        new ListViewImpl(iListDao, rv, homeAdapter, swipe,  true) {
            @Override
            public List queryData() {
                String assetsContent_GengDuo = "";
                switch (type) {//1.热门, 2.新闻, 3.辟谣, 4.趣味. 5.养身, 6.母婴
                    case 1:
                        assetsContent_GengDuo = "remen_content_genduo";
                        break;
                    case 2:
                        assetsContent_GengDuo = "news_content_genduo";
                        break;
                    case 3:
                        assetsContent_GengDuo = "biyao_content_genduo";
                        break;
                    case 4:
                        assetsContent_GengDuo = "quwei_content_genduo";
                        break;
                    case 5:
                        assetsContent_GengDuo = "yangshen_content_genduo";
                        break;
                    case 6:
                        assetsContent_GengDuo = "muyin_content_genduo";
                        break;
                    default:
                        break;
                }

                String content = UIUtils.getJson(assetsContent_GengDuo, getActivity());
                Gson gson = new Gson();
                HomeContent homeContent = gson.fromJson(content, HomeContent.class);
                return homeContent.getData();
            }
        }.defaultRefresh();
    }

    //添加头部
    private void addHeader() {
        View headerView = getLayoutInflater().inflate(R.layout.rv_header, null);
        headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        homeAdapter.addHeaderView(headerView);

        ViewPager vp = headerView.findViewById(R.id.vp);
        vp.setAdapter(new NormalPagerAdapter(headerImgs, headerUrls, getActivity()));
    }

    @Override
    public int layoutId() {
        return R.layout.common_swipe_with_recycler;
    }

    public static Fragment1 newsIntance(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.HOME_TYPE, type);
        Fragment1 fragment1 = new Fragment1();
        fragment1.setArguments(bundle);
        return fragment1;
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

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        List<HomeContent.DataBean> data = homeContent.getData();
        String detail_url = data.get(position).getDetail_url();
        Intent intent = new Intent(getActivity(), WebActivity.class);
        intent.putExtra(Constant.WEB_URL, detail_url);
        startActivity(intent);
    }
}
