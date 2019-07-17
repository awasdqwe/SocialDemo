package com.ljc.socialdemo.ui.me;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ljc.socialdemo.Constant;
import com.ljc.socialdemo.R;
import com.ljc.socialdemo.SociaDemoApplication;
import com.ljc.socialdemo.db.dao.User;
import com.ljc.socialdemo.db.dao.UserInfo;
import com.ljc.socialdemo.event.NameEvent;
import com.ljc.socialdemo.event.PhotoEvent;
import com.ljc.socialdemo.ui.BaseFragment;
import com.ljc.socialdemo.ui.MainActivity;
import com.ljc.socialdemo.ui.login.LoginActivity;
import com.ljc.socialdemo.utils.PreferencesUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import greendao.UserInfoDao;

/**
 * Created by 18075 on 2018/11/8.
 */

public class MineFragment extends BaseFragment {
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_name)
    TextView  tvName;
    @BindView(R.id.tv_info)
    TextView  tvInfo;
    @BindView(R.id.tv_vote)
    TextView  tvVote;
    @BindView(R.id.tv_share)
    TextView  tvShare;
    @BindView(R.id.tv_advise)
    TextView  tvAdvise;
    @BindView(R.id.tv_about)
    TextView  tvAbout;
    Unbinder unbinder;

    UserInfo userInfo;
    @BindView(R.id.tv_login_out)
    TextView tvLoginOut;

    @Override
    protected void loadData() {
        Long userId = PreferencesUtils.getLong(getActivity(), Constant.USERID_KEY, -1);
        UserInfoDao userInfoDao = SociaDemoApplication.getInstances().getDaoSession().getUserInfoDao();
        UserInfo unique = userInfoDao.queryBuilder().where(UserInfoDao.Properties.UserId.eq(userId)).build().unique();
        if (unique == null || TextUtils.isEmpty(unique.getName())) {
            tvName.setText("WeiLang" + UUID.randomUUID());
            userInfo = new UserInfo();
        } else {
            tvName.setText(unique.getName());
            userInfo = unique;
        }
        EventBus.getDefault().register(this);
        updateUI();
    }

    private void updateUI() {
        Glide.with(getActivity())
                .load(userInfo.getHead())
                .error(R.drawable.icon_user)
                .placeholder(R.drawable.icon_user)
                .into(ivHead);
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_mine;
    }

    public static MineFragment newsIntance() {
        MineFragment mineFragment = new MineFragment();
        return mineFragment;
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

    @OnClick({R.id.tv_info, R.id.tv_vote, R.id.tv_share, R.id.tv_advise, R.id.tv_about, R.id.tv_login_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_info:
                startActivity(new Intent(getActivity(), UserInfoActivity.class));
                break;
            case R.id.tv_vote:
                Toast.makeText(getActivity(), "投票功能暂未开放", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_share:
                Toast.makeText(getActivity(), "分享功能暂未开放", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_advise: //建议
                startActivity(new Intent(getActivity(), AdviseActivity.class));
                break;
            case R.id.tv_about://关于
                startActivity(new Intent(getActivity(), AboutActivity.class));
                break;
            case R.id.tv_login_out://退出登录
                PreferencesUtils.saveBeanByFastJson(getActivity(), User.class.getSimpleName(),null);
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
        }
    }

    //EventBus处理事件
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void disposeName(NameEvent nameEvent) {
        tvName.setText(nameEvent.getName());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void disposePhoto(PhotoEvent photoEvent) {
        Glide.with(getActivity())
                .load(photoEvent.getPhotoPath())
                .error(R.drawable.icon_user)
                .placeholder(R.drawable.icon_user)
                .into(ivHead);

    }
}
