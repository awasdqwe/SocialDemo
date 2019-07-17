package com.ljc.socialdemo.ui.discove;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ljc.socialdemo.Constant;
import com.ljc.socialdemo.R;
import com.ljc.socialdemo.SociaDemoApplication;
import com.ljc.socialdemo.db.dao.Commen;
import com.ljc.socialdemo.db.dao.Discove;
import com.ljc.socialdemo.db.dao.User;
import com.ljc.socialdemo.db.dao.UserInfo;
import com.ljc.socialdemo.db.dao.Zan;
import com.ljc.socialdemo.event.NormalEvent;
import com.ljc.socialdemo.utils.LogUtils;
import com.ljc.socialdemo.utils.PreferencesUtils;
import com.ljc.socialdemo.utils.TimeUtil;
import com.ljc.socialdemo.widget.BaseFragmentPagerAdapter;
import com.ljc.socialdemo.widget.CommentPop;
import com.ljc.socialdemo.widget.GlideRoundTransUtils;
import com.ljc.socialdemo.widget.MyToolBar;
import com.ljc.socialdemo.widget.NormalPagerAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import greendao.DaoSession;
import greendao.DiscoveDao;
import greendao.UserInfoDao;
import greendao.ZanDao;

/**
 * Created by 18075 on 2018/11/13.
 */

public class DiscoveDetaiActivity extends FragmentActivity {


    @BindView(R.id.toolbar)
    MyToolBar toolbar;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_name)
    TextView  tvName;
    @BindView(R.id.tv_content)
    TextView  tvContent;
    @BindView(R.id.tv_time)
    TextView  tvTime;
    @BindView(R.id.tv1)
    TextView  tv1;
    @BindView(R.id.view1)
    View      view1;
    @BindView(R.id.tv2)
    TextView  tv2;
    @BindView(R.id.view2)
    View      view2;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.tv_comment)
    TextView  tvComment;
    @BindView(R.id.tv_zan)
    TextView  tvZan;
    @BindView(R.id.vp_header)
    ViewPager vpHeader;
    private CommentPop mCommentPop; //键盘弹框
    private Discove    discove;
    private DaoSession daoSession;
    private long       discoveId;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discove_detail);
        ButterKnife.bind(this);
        init();
        //判断是否需要打开评论 //默认不需要打开
        initUI();
    }


    private void init() {
        toolbar.setTitleLeftClickListener(v -> finish());
        discoveId = getIntent().getLongExtra(Constant.DISCOVE_ID, 0);
        userId = PreferencesUtils.getLong(this, Constant.USERID_KEY);
        //查询数据
        daoSession = SociaDemoApplication.getInstances().getDaoSession();
        discove = daoSession.getDiscoveDao().queryBuilder().where(DiscoveDao.Properties.Id.eq(discoveId)).build().unique();
        if (discove == null) {
            Toast.makeText(this, "该数据不存在", Toast.LENGTH_SHORT).show();
            finish();
        }

        List<Fragment> fragments = Arrays.asList(CommentFragment.newsIntance(discove.getId()), ZanFragment.newsIntance(discove.getId()));
        vp.setAdapter(new BaseFragmentPagerAdapter(getSupportFragmentManager(), fragments));
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    view1.setVisibility(View.VISIBLE);
                    view2.setVisibility(View.GONE);
                } else {
                    view1.setVisibility(View.GONE);
                    view2.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        view1.setVisibility(View.VISIBLE);
        view2.setVisibility(View.GONE);
        vp.setCurrentItem(0);
    }

    private void initUI() {
        Glide.with(this)
                .load(discove.getHeader())
                .placeholder(R.drawable.icon_user)
                .error(R.drawable.icon_user)
                .centerCrop()
                .transform(new GlideRoundTransUtils(this, 5))
                .into(ivHead);
        tvName.setText(discove.getName());

        String imgs = discove.getImgs();
        if (!TextUtils.isEmpty(imgs)) {
            String[] split = imgs.split(",");
            vpHeader.setAdapter(new NormalPagerAdapter(Arrays.asList(split), null, this));
        } else {
            vpHeader.setVisibility(View.GONE);
        }

        tvContent.setText(discove.getContent());
        tvTime.setText(TimeUtil.getDescriptionTimeFromTimestamp(Long.valueOf(discove.getCreateTime())));

        int commentNum = discove.getCommentNum();
        int zanNum = discove.getZanNum();

        if (commentNum > 0) tv1.setText("评论(" + commentNum + ")");
        else tv1.setText("评论");

        if (zanNum > 0) tv2.setText("赞(" + zanNum + ")");
        else tv2.setText("赞");

        if (discove.getIsZan() && userId == discoveId)
            tvZan.setSelected(true);
        else  tvZan.setSelected(false);

    }

    @Override
    protected void onResume() {
        super.onResume();
        isOpen();
    }

    private void isOpen() {
        boolean isOpenComment = getIntent().getBooleanExtra(Constant.COMMENT, false);
        if (isOpenComment) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    showPop();
                }
            },1000);

        }
    }

    private void showPop() {
        if (mCommentPop == null) {
            mCommentPop = new CommentPop(this);
        }
        mCommentPop.setListener(text -> {
            //评论完成 刷新数据
            int commentNum = discove.getCommentNum();
            tv1.setText("评论(" + (++commentNum) + ")");

            //查询当前的用户信息
            UserInfo unique = daoSession.getUserInfoDao().queryBuilder().where(UserInfoDao.Properties.UserId.eq(userId)).build().unique();
            LogUtils.d("评论:  " + unique.toString());
            //写入数据
            String name = unique.getName();
            String head = unique.getHead();

            Commen commen = new Commen(name, text, head, String.valueOf(System.currentTimeMillis()), discoveId, null);
            daoSession.getCommenDao().insert(commen);

            //刷新数据库的说说数据
            discove.setCommentNum(commentNum);
            daoSession.getDiscoveDao().update(discove);
            EventBus.getDefault().post(new NormalEvent(Constant.PUB_COMMENT));
        });
        mCommentPop.showPopupWindow();
    }

    @OnClick({R.id.tv1, R.id.tv2, R.id.tv_comment, R.id.tv_zan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv1:
                view1.setVisibility(View.VISIBLE);
                view2.setVisibility(View.GONE);
                vp.setCurrentItem(0);
                break;
            case R.id.tv2:
                view1.setVisibility(View.GONE);
                view2.setVisibility(View.VISIBLE);
                vp.setCurrentItem(1);
                break;
            case R.id.tv_comment:
                showPop();
                break;
            case R.id.tv_zan:
                //刷新数据
                boolean isZan = discove.getIsZan();
                int zanNum = discove.getZanNum();

                ZanDao zanDao = daoSession.getZanDao();
                if (isZan && userId == discove.getUserId()) { //已经点赞
                    discove.setZanNum(--zanNum);
                    //删除数据
                    Zan unique = zanDao.queryBuilder().where(ZanDao.Properties.DiscoveId.eq(discove.getId()), ZanDao.Properties.UserId.eq(userId)).build().unique();
                    zanDao.delete(unique);
                    tvZan.setSelected(false);
                } else {
                    tvZan.setSelected(true);
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
                if (zanNum > 0)
                    tv2.setText("赞(" + zanNum + ")");
                else tv2.setText("赞");


                //数据库刷新
                daoSession.getDiscoveDao().update(discove);
                EventBus.getDefault().post(new NormalEvent(Constant.PUB_ZAN));
                break;
        }
    }
}
