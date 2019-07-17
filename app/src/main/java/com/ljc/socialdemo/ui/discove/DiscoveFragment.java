package com.ljc.socialdemo.ui.discove;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ljc.socialdemo.Constant;
import com.ljc.socialdemo.R;
import com.ljc.socialdemo.SociaDemoApplication;
import com.ljc.socialdemo.db.dao.Discove;
import com.ljc.socialdemo.db.dao.UserInfo;
import com.ljc.socialdemo.db.dao.Zan;
import com.ljc.socialdemo.event.NameEvent;
import com.ljc.socialdemo.event.NormalEvent;
import com.ljc.socialdemo.ui.BaseFragment;
import com.ljc.socialdemo.ui.IListDao;
import com.ljc.socialdemo.ui.ListViewImpl;
import com.ljc.socialdemo.ui.discove.adapter.DiscoveAdapter;
import com.ljc.socialdemo.utils.DensityUtil;
import com.ljc.socialdemo.utils.LogUtils;
import com.ljc.socialdemo.utils.PreferencesUtils;
import com.ljc.socialdemo.utils.UIUtils;
import com.ljc.socialdemo.widget.NormalPagerAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import greendao.DaoSession;
import greendao.DiscoveDao;
import greendao.UserInfoDao;
import greendao.ZanDao;

/**
 * Created by 18075 on 2018/11/8.
 */

public class DiscoveFragment extends BaseFragment implements BaseQuickAdapter.OnItemChildClickListener, View.OnClickListener, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.et_search)
    EditText           etSearch;
    @BindView(R.id.iv_search)
    ImageView          ivSearch;
    @BindView(R.id.rv)
    RecyclerView       rv;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    Unbinder unbinder;
    private DiscoveAdapter discoveAdapter;
    private List<Discove>  discoveList;
    private List<Integer>  headerImgs;
    private ListViewImpl   listView;
    private IListDao       iListDao;
    private DiscoveDao     discoveDao;

    @Override
    protected void loadData() {
        initData();
        initEvent();
        EventBus.getDefault().register(this);
    }

    private void initData() {
        headerImgs = new ArrayList<>();

        headerImgs.add(R.drawable.viewpage_1);
        headerImgs.add(R.drawable.viewpage_2);
        headerImgs.add(R.drawable.viewpage_3);

        discoveDao = SociaDemoApplication.getInstances().getDaoSession().getDiscoveDao();
        discoveList = discoveDao.queryBuilder().listLazy();
        if (discoveList == null && discoveList.size() == 0) {
            discoveList = new ArrayList<>();
        }
    }

    private void initEvent() {
        discoveAdapter = new DiscoveAdapter();
        rv.setLayoutManager(new LinearLayoutManager(UIUtils.getContext()));
        discoveAdapter.setOnItemClickListener(this);
        discoveAdapter.setOnItemChildClickListener(this);
        addHeader();
        iListDao = new IListDao();
        iListDao.setObjects(discoveList);
        listView = new ListViewImpl(iListDao, rv, discoveAdapter, swipe, true).defaultRefresh();
    }

    //添加头部
    private void addHeader() {
        View headerView = getLayoutInflater().inflate(R.layout.header_discove, null);
        headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        discoveAdapter.addHeaderView(headerView);

        ViewPager vp = headerView.findViewById(R.id.vp);
        LinearLayout ll_1 = headerView.findViewById(R.id.ll_1);
        LinearLayout ll_2 = headerView.findViewById(R.id.ll_2);
        LinearLayout ll_3 = headerView.findViewById(R.id.ll_3);
        LinearLayout ll_4 = headerView.findViewById(R.id.ll_4);
        LinearLayout ll_5 = headerView.findViewById(R.id.ll_5);
        LinearLayout ll_6 = headerView.findViewById(R.id.ll_6);
        ll_1.setOnClickListener(this);
        ll_2.setOnClickListener(this);
        ll_3.setOnClickListener(this);
        ll_4.setOnClickListener(this);
        ll_5.setOnClickListener(this);
        ll_6.setOnClickListener(this);

        vp.setAdapter(new NormalPagerAdapter(headerImgs, null, getActivity()));
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_discove;
    }

    public static DiscoveFragment newsIntance() {
        DiscoveFragment discoveFragment = new DiscoveFragment();
        return discoveFragment;
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
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        List data = adapter.getData();
        Discove discove = (Discove) data.get(position);
        DaoSession daoSession = SociaDemoApplication.getInstances().getDaoSession();
        DiscoveDao discoveDao = daoSession.getDiscoveDao();
        switch (view.getId()) {
            case R.id.tv_comment:
                //刷新数据
                //跳转发现详情页面
                Intent intent = new Intent(getActivity(), DiscoveDetaiActivity.class);
                intent.putExtra(Constant.COMMENT, true);
                intent.putExtra(Constant.DISCOVE_ID, discove.getId());
                startActivity(intent);
                break;
            case R.id.tv_zan:
                //刷新数据
                boolean isZan = discove.getIsZan();
                int zanNum = discove.getZanNum();
                long userId = PreferencesUtils.getLong(getActivity(), Constant.USERID_KEY);
                ZanDao zanDao = daoSession.getZanDao();
                if (isZan && userId == discove.getUserId()) { //已经点赞
                    discove.setZanNum(--zanNum);
                    Zan unique = zanDao.queryBuilder().where(ZanDao.Properties.DiscoveId.eq(discove.getId()), ZanDao.Properties.UserId.eq(userId)).build().unique();
                    zanDao.delete(unique);
                } else {
                    discove.setZanNum(++zanNum);
                    //插入赞的数据
                    UserInfo unique = daoSession.getUserInfoDao().queryBuilder().where(UserInfoDao.Properties.UserId.eq(userId)).build().unique();
                    LogUtils.d("点赞:  " + unique.toString());
                    //写入数据
                    String name = unique.getName();
                    String head = unique.getHead();
                    Zan zan = new Zan(name, head, String.valueOf(System.currentTimeMillis()), discove.getId(), userId, null);
                    zanDao.insert(zan);
                }
                discove.setIsZan(!isZan);
                adapter.notifyDataSetChanged();
                //数据库刷新
                discoveDao.update(discove);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        //跳转发现详情页面
        Discove discove = (Discove) adapter.getData().get(position);
        Intent intent = new Intent(getActivity(), DiscoveDetaiActivity.class);
        intent.putExtra(Constant.DISCOVE_ID, discove.getId());
        startActivity(intent);
    }

    @OnClick(R.id.iv_search)
    public void onViewClicked() {
        //查询内容
        String trim = etSearch.getText().toString().trim();
        if (TextUtils.isEmpty(trim)) {
            Toast.makeText(getActivity(), "点击查询: ", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(getActivity(), DiscoveSearchActivity.class);
        intent.putExtra(Constant.SEARCH,trim);
        startActivity(intent);
        etSearch.setText("");
        Toast.makeText(getActivity(), "查询内容: " + trim, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_1: //直播
                Toast.makeText(getActivity(), "直播", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_2://赛事
                Toast.makeText(getActivity(), "赛事", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_3://电影
                Toast.makeText(getActivity(), "电影", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_4://美食
                Toast.makeText(getActivity(), "美食", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_5://音乐
                Toast.makeText(getActivity(), "音乐", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_6://更多
                Toast.makeText(getActivity(), "更多", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    //EventBus处理事件
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void disposeRefresh(NormalEvent normalEvent) {
        discoveList = discoveDao.queryBuilder().listLazy();
        if (discoveList == null && discoveList.size() == 0) {
            discoveList = new ArrayList<>();
        }
        iListDao.setObjects(discoveList);
        listView.onRefresh();
    }
}
