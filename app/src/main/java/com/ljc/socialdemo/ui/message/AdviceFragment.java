package com.ljc.socialdemo.ui.message;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ljc.socialdemo.R;
import com.ljc.socialdemo.SociaDemoApplication;
import com.ljc.socialdemo.db.dao.Message;
import com.ljc.socialdemo.ui.BaseFragment;
import com.ljc.socialdemo.ui.IListDao;
import com.ljc.socialdemo.ui.ListViewImpl;
import com.ljc.socialdemo.ui.message.adapter.AdviceAdapter;
import com.ljc.socialdemo.utils.UIUtils;

import org.greenrobot.greendao.query.LazyList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import greendao.MessageDao;

/**
 * Created by 18075 on 2018/11/8.
 * 消息fragment里面的消息
 */

public class AdviceFragment extends BaseFragment implements BaseQuickAdapter.OnItemChildClickListener {
    @BindView(R.id.rv)
    RecyclerView       rv;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    Unbinder unbinder;

    @Override
    protected void loadData() {
        rv.setLayoutManager(new LinearLayoutManager(UIUtils.getContext()));
        List<Message> messages = initData();
        AdviceAdapter adviceAdapter = new AdviceAdapter();
        adviceAdapter.setOnItemChildClickListener(this);
        IListDao iListDao = new IListDao();
        iListDao.setObjects(messages);
        new ListViewImpl(iListDao, rv, adviceAdapter, swipe).defaultRefresh();
    }

    private List<Message> initData() {
        MessageDao messageDao = SociaDemoApplication.getInstances().getDaoSession().getMessageDao();
        LazyList<Message> list = messageDao.queryBuilder().listLazy();
        List<Message> messages = new ArrayList<>();
        if (list == null || list.size() == 0) {
            return messages;
        }
        Random random = new Random();
        int i = random.nextInt(list.size());
        for (int j = 0; j < i; j++) {
            messages.add(list.get(j));
        }
        return messages;
    }

    @Override
    public int layoutId() {
        return R.layout.common_swipe_with_recycler;
    }

    public static AdviceFragment newsIntance() {
        AdviceFragment adviceFragment = new AdviceFragment();
        return adviceFragment;
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
        Toast.makeText(getActivity(), "点击了", Toast.LENGTH_SHORT).show();
    }
}
