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
import com.ljc.socialdemo.db.dao.Commen;
import com.ljc.socialdemo.event.NormalEvent;
import com.ljc.socialdemo.ui.BaseFragment;
import com.ljc.socialdemo.ui.IListDao;
import com.ljc.socialdemo.ui.ListViewImpl;
import com.ljc.socialdemo.ui.discove.adapter.CommentAdapter;
import com.ljc.socialdemo.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import greendao.CommenDao;

/**
 * Created by 18075 on 2018/11/13.
 */

public class CommentFragment extends BaseFragment {

    @BindView(R.id.rv)
    RecyclerView rv;
    Unbinder unbinder;
    private List<Commen>   listComment;
    private CommentAdapter commentAdapter;
    private long           id;
    private IListDao       iListDao;
    private ListViewImpl   listView;

    @Override
    protected void loadData() {
        id = getArguments().getLong(ID);
        listComment = SociaDemoApplication.getInstances().getDaoSession().getCommenDao().queryBuilder().where(CommenDao.Properties.DiscoveId.eq(id)).build().listLazy();
        commentAdapter = new CommentAdapter();
        rv.setLayoutManager(new LinearLayoutManager(UIUtils.getContext()));
        iListDao = new IListDao();
        iListDao.setObjects(listComment);
        listView = new ListViewImpl(iListDao, rv, commentAdapter, null, true);
        listView.onRefresh();
        EventBus.getDefault().register(this);
    }

    @Override
    public int layoutId() {
        return R.layout.common_recyclerview;
    }

    public static final String ID = "id";

    public static CommentFragment newsIntance(Long id) {
        Bundle bundle = new Bundle();
        bundle.putLong(ID, id);
        CommentFragment commentFragment = new CommentFragment();
        commentFragment.setArguments(bundle);
        return commentFragment;
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
        if (normalEvent.getType() == Constant.PUB_COMMENT) {
            listComment = SociaDemoApplication.getInstances().getDaoSession().getCommenDao().queryBuilder().where(CommenDao.Properties.DiscoveId.eq(id)).build().listLazy();
            iListDao.setObjects(listComment);
            listView.onRefresh();
        }
    }
}
