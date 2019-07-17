package com.ljc.socialdemo.ui.me;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jaiky.imagespickers.ImageConfig;
import com.jaiky.imagespickers.ImageLoader;
import com.jaiky.imagespickers.ImageSelector;
import com.jaiky.imagespickers.ImageSelectorActivity;
import com.ljc.socialdemo.Constant;
import com.ljc.socialdemo.R;
import com.ljc.socialdemo.SociaDemoApplication;
import com.ljc.socialdemo.db.dao.Commen;
import com.ljc.socialdemo.db.dao.Discove;
import com.ljc.socialdemo.db.dao.UserInfo;
import com.ljc.socialdemo.db.dao.Zan;
import com.ljc.socialdemo.event.NameEvent;
import com.ljc.socialdemo.event.PhotoEvent;
import com.ljc.socialdemo.ui.MainActivity;
import com.ljc.socialdemo.utils.LogUtils;
import com.ljc.socialdemo.utils.PreferencesUtils;
import com.ljc.socialdemo.widget.MyToolBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.greendao.query.LazyList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.OptionPicker;
import greendao.CommenDao;
import greendao.DaoSession;
import greendao.DiscoveDao;
import greendao.UserInfoDao;
import greendao.ZanDao;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by 18075 on 2018/11/9.
 */

public class UserInfoActivity extends Activity implements EasyPermissions.PermissionCallbacks {

    @BindView(R.id.toolbar)
    MyToolBar toolbar;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_edit_head)
    TextView  tvEditHead;
    @BindView(R.id.tv_name)
    TextView  tvName;
    @BindView(R.id.tv_sex)
    TextView  tvSex;
    @BindView(R.id.tv_age)
    TextView  tvAge;
    @BindView(R.id.tv_brithday)
    TextView  tvBrithday;
    @BindView(R.id.tv_hobby)
    TextView  tvHobby;

    public static final int NAME  = 101;
    public static final int HOBBY = 102;
    private UserInfoDao userInfoDao;
    private UserInfo load;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        ButterKnife.bind(this);
        loadData();
        initEvent();
    }

    private void loadData() {
        //查询数据库数据
        userInfoDao = SociaDemoApplication.getInstances().getDaoSession().getUserInfoDao();
        load = userInfoDao.load(PreferencesUtils.getLong(this, Constant.USERID_KEY));
        updateUI(load);
    }

    /**
     * 更新UI
     *
     * @param load
     */
    private void updateUI(UserInfo load) {
        Glide.with(this)
                .load(load.getHead())
                .placeholder(R.drawable.icon_user)
                .centerCrop()
                .into(ivHead);
        tvName.setText(load.getName());
        if (load.getAge() != -1)
            tvAge.setText(String.valueOf(load.getAge()));
        tvSex.setText(load.getSex());
        tvBrithday.setText(load.getBirthday());
        tvHobby.setText(load.getHobby());
    }

    private void initEvent() {
        toolbar.setTitleLeftClickListener(v -> finish());
    }

    @OnClick({R.id.tv_edit_head, R.id.tv_name, R.id.tv_sex, R.id.tv_age, R.id.tv_brithday, R.id.tv_hobby})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_edit_head:
                initPermission();
                break;
            case R.id.tv_name:
                Intent intent = new Intent(this, EditUserInfoActivity.class);
                intent.putExtra(Constant.TYPE, 1);
                startActivityForResult(intent, NAME);
                break;
            case R.id.tv_sex:
                OptionPicker picker = new OptionPicker(this, new String[]{
                        "男", "女",});
                picker.setOffset(2);
                picker.setSelectedIndex(10);
                picker.setTextSize(25);
                picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(int index, String item) {
                        tvSex.setText(item);
                        load.setSex(item);
                        userInfoDao.update(load);
                    }
                });
                picker.show();
                break;
            case R.id.tv_age:
                String[] ages = new String[99];
                for (int i = 0; i < 99; i++) {
                    ages[i] = String.valueOf(i + 1);
                }
                OptionPicker picker1 = new OptionPicker(this, ages);
                picker1.setOffset(2);
                picker1.setSelectedIndex(10);
                picker1.setTextSize(22);
                picker1.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(int index, String item) {
                        tvAge.setText(item);
                        load.setAge(Integer.valueOf(item));
                        userInfoDao.update(load);
                    }
                });
                picker1.show();
                break;
            case R.id.tv_brithday:
                DatePicker datePicker = new DatePicker(this, DatePicker.YEAR_MONTH_DAY);
                datePicker.setRange(1980, 2018);//年份范围
                datePicker.setTextSize(20);
                datePicker.setOffset(2);
                datePicker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        String contents = year + "-" + month + "-" + day;
                        LogUtils.d(contents);
                        tvBrithday.setText(contents);
                        load.setBirthday(contents);
                        userInfoDao.update(load);
                    }
                });
                datePicker.show();
                break;
            case R.id.tv_hobby:
                Intent intent1 = new Intent(this, EditUserInfoActivity.class);
                intent1.putExtra(Constant.TYPE, 2);
                startActivityForResult(intent1, HOBBY);
                break;
        }
    }

    private void selectPhotos() {
        ImageConfig imageConfig = new ImageConfig.Builder(new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                Glide.with(context)
                        .load(path)
                        .placeholder(R.drawable.icon_user)
                        .centerCrop()
                        .into(imageView);
            }
        })
                .steepToolBarColor(getResources().getColor(R.color.blue))
                .titleBgColor(getResources().getColor(R.color.blue))
                .titleSubmitTextColor(getResources().getColor(R.color.white))
                .titleTextColor(getResources().getColor(R.color.white))
                // 开启单选   （默认为多选）   
                .singleSelect()
                // 开启拍照功能 （默认关闭）  
                .showCamera()
                // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）  
                .filePath("/socia/picture")
                .build();
        ImageSelector.open(this, imageConfig);
    }

    private void initPermission() {
        //所要申请的权限
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {//检查是否获取该权限
            LogUtils.i("已获取权限");
            selectPhotos();
        } else {
            //第二个参数是被拒绝后再次申请该权限的解释
            //第三个参数是请求码
            //第四个参数是要申请的权限
            EasyPermissions.requestPermissions(this, "必要的权限", 0, perms);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case NAME:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    String name = data.getStringExtra(Constant.NAME);
                    tvName.setText(name);
                    EventBus.getDefault().post(new NameEvent(name));
                    //吧所有表的名字都需要改一遍   userinfo  discove  comment  zan
                    updateName(load.getName(),name);
                    load.setName(name);
                    userInfoDao.update(load);
                }
                break;
            case HOBBY:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    String hobby = data.getStringExtra(Constant.HOBBY);
                    tvHobby.setText(hobby);
                    load.setHobby(hobby);
                    userInfoDao.update(load);
                }
                break;
            case ImageSelector.IMAGE_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    // 获取选中的图片路径列表 Get Images Path List  
                    List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
                    for (String path : pathList) {
                        Glide.with(this)
                                .load(path)
                                .placeholder(com.jaiky.imagespickers.R.drawable.global_img_default)
                                .centerCrop()
                                .into(ivHead);
                        //保存数据到数据库
                        load.setHead(path);
                        userInfoDao.update(load);
                        EventBus.getDefault().post(new PhotoEvent(path));
                    }
                }
                break;
        }
    }

    /**
     *   //把所有表的名字都需要改一遍   userinfo  discove  comment  zan
     * @param oldName
     * @param newName
     */
    private void updateName(String oldName, String newName) {
        DaoSession daoSession = SociaDemoApplication.getInstances().getDaoSession();
        //discove
        DiscoveDao discoveDao = daoSession.getDiscoveDao();
        LazyList<Discove> discoves = discoveDao.queryBuilder().listLazy();
        if (discoves!= null && discoves.size()>0) {
            for (Discove discove : discoves) {
                if (discove.getName().equals(oldName)){
                    discove.setName(newName);
                    discoveDao.update(discove);
                }
            }
        }

        //comment
        CommenDao commenDao = daoSession.getCommenDao();
        LazyList<Commen> commen = commenDao.queryBuilder().listLazy();
        if (commen!= null && commen.size()>0) {
            for (Commen comman : commen) {
                if (comman.getName().equals(oldName)){
                    comman.setName(newName);
                    commenDao.update(comman);
                }
            }
        }

        //zan
        ZanDao zanDao = daoSession.getZanDao();
        LazyList<Zan> zans = zanDao.queryBuilder().listLazy();
        if (zans!= null && zans.size()>0) {
            for (Zan zan : zans) {
                if (zan.getName().equals(oldName)){
                    zan.setName(newName);
                    zanDao.update(zan);
                }
            }
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        LogUtils.d("获取权限成功");
        selectPhotos();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        LogUtils.d("获取权限失败");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //把申请权限的回调交由EasyPermissions处理
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
