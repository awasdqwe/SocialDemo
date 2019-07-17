package com.ljc.socialdemo.ui.discove;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ljc.socialdemo.Constant;
import com.ljc.socialdemo.R;
import com.ljc.socialdemo.SociaDemoApplication;
import com.ljc.socialdemo.db.dao.Discove;
import com.ljc.socialdemo.db.dao.UserInfo;
import com.ljc.socialdemo.db.dao.Zan;
import com.ljc.socialdemo.ui.IListDao;
import com.ljc.socialdemo.ui.ListViewImpl;
import com.ljc.socialdemo.ui.discove.adapter.DiscoveAdapter;
import com.ljc.socialdemo.utils.LogUtils;
import com.ljc.socialdemo.utils.PreferencesUtils;
import com.ljc.socialdemo.utils.UIUtils;
import com.ljc.socialdemo.widget.MyToolBar;

import org.greenrobot.greendao.query.LazyList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import greendao.DaoSession;
import greendao.DiscoveDao;
import greendao.UserInfoDao;
import greendao.ZanDao;

/**
 * Created by 18075 on 2018/11/16.
 */

public class DiscoveSearchActivity extends Activity implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {
    @BindView(R.id.toolbar)
    MyToolBar    toolbar;
    @BindView(R.id.et_search)
    EditText     etSearch;
    @BindView(R.id.iv_search)
    ImageView    ivSearch;
    @BindView(R.id.rv)
    RecyclerView rv;
    private String         content;
    private DiscoveAdapter discoveAdapter;
    private IListDao       iListDao;
    private ListViewImpl   listView;
    private List<Discove>  discoveList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discove_search);
        ButterKnife.bind(this);
        content = getIntent().getStringExtra(Constant.SEARCH);
        discoveList = new ArrayList<>();
        queryData();
        initEvent();
    }

    private void queryData() {
        DiscoveDao discoveDao = SociaDemoApplication.getInstances().getDaoSession().getDiscoveDao();
        LazyList<Discove> discoves = discoveDao.queryBuilder().listLazy();
        discoveList.clear();
        for (Discove discove : discoves) {
            if (discove.getContent().contains(content)) {
                discoveList.add(discove);
            }
        }
    }

    private void initEvent() {
        toolbar.setTitleLeftClickListener(v -> finish());
        etSearch.setText(content);
        discoveAdapter = new DiscoveAdapter();
        rv.setLayoutManager(new LinearLayoutManager(UIUtils.getContext()));
        discoveAdapter.setOnItemClickListener(this);
        discoveAdapter.setOnItemChildClickListener(this);
        iListDao = new IListDao();
        iListDao.setObjects(discoveList);
        listView = new ListViewImpl(iListDao, rv, discoveAdapter, null, true);
        listView.onRefresh();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        //跳转发现详情页面
        Discove discove = (Discove) adapter.getData().get(position);
        Intent intent = new Intent(this, DiscoveDetaiActivity.class);
        intent.putExtra(Constant.DISCOVE_ID, discove.getId());
        startActivity(intent);
        finish();
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
                Intent intent = new Intent(this, DiscoveDetaiActivity.class);
                intent.putExtra(Constant.COMMENT, true);
                intent.putExtra(Constant.DISCOVE_ID, discove.getId());
                startActivity(intent);
                finish();
                break;
            case R.id.tv_zan:
                //刷新数据
                boolean isZan = discove.getIsZan();
                int zanNum = discove.getZanNum();
                long userId = PreferencesUtils.getLong(this, Constant.USERID_KEY);
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

    @OnClick(R.id.iv_search)
    public void onViewClicked() {
        //查询内容
        String trim = etSearch.getText().toString().trim();
        if (TextUtils.isEmpty(trim)) {
            Toast.makeText(this, "点击查询: ", Toast.LENGTH_SHORT).show();
            return;
        }
        content = trim;
        queryData();
        listView.onRefresh();
        Toast.makeText(this, "查询内容: " + trim, Toast.LENGTH_SHORT).show();
    }
}
