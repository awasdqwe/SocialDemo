package com.ljc.socialdemo.ui.discove;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ljc.socialdemo.Constant;
import com.ljc.socialdemo.R;
import com.ljc.socialdemo.SociaDemoApplication;
import com.ljc.socialdemo.db.dao.Zan;
import com.ljc.socialdemo.event.NormalEvent;
import com.ljc.socialdemo.ui.BaseFragment;
import com.ljc.socialdemo.ui.IListDao;
import com.ljc.socialdemo.ui.ListViewImpl;
import com.ljc.socialdemo.ui.discove.adapter.DiscoveAdapter;
import com.ljc.socialdemo.ui.discove.adapter.ZanAdapter;
import com.ljc.socialdemo.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.greendao.query.LazyList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import greendao.CommenDao;
import greendao.ZanDao;

/**
 * Created by 18075 on 2018/11/13.
 */

public class ZanFragment extends BaseFragment {

    @BindView(R.id.rv)
    RecyclerView rv;
    Unbinder unbinder;
    private LazyList<Zan> listZan;
    private ZanAdapter    zanAdapter;
    private IListDao      iListDao;
    private ListViewImpl  listView;
    private long id;

    @Override
    protected void loadData() {
        id = getArguments().getLong(ID);
        listZan = SociaDemoApplication.getInstances().getDaoSession().getZanDao().queryBuilder().where(ZanDao.Properties.DiscoveId.eq(id)).build().listLazy();
        zanAdapter = new ZanAdapter();
        rv.setLayoutManager(new LinearLayoutManager(UIUtils.getContext()));
        iListDao = new IListDao();
        iListDao.setObjects(listZan);
        listView = new ListViewImpl(iListDao, rv, zanAdapter, null, true);
        listView.onRefresh();
        EventBus.getDefault().register(this);
    }

    @Override
    public int layoutId() {
        return R.layout.common_recyclerview;
    }

    public static final String ID = "id";

    public static ZanFragment newsIntance(Long id) {
        Bundle bundle = new Bundle();
        bundle.putLong(ID, id);
        ZanFragment zanFragment = new ZanFragment();
        zanFragment.setArguments(bundle);
        return zanFragment;
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


    //EventBus处理事件
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void disposeRefresh(NormalEvent normalEvent) {
        if (normalEvent.getType() == Constant.PUB_ZAN) {
            listZan = SociaDemoApplication.getInstances().getDaoSession().getZanDao().queryBuilder().where(ZanDao.Properties.DiscoveId.eq(id)).build().listLazy();
            iListDao.setObjects(listZan);
            listView.onRefresh();
        }
    }
}
