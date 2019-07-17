package com.ljc.socialdemo.ui.discove;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jaiky.imagespickers.ImageConfig;
import com.jaiky.imagespickers.ImageLoader;
import com.jaiky.imagespickers.ImageSelector;
import com.jaiky.imagespickers.ImageSelectorActivity;
import com.ljc.socialdemo.Constant;
import com.ljc.socialdemo.R;
import com.ljc.socialdemo.SociaDemoApplication;
import com.ljc.socialdemo.db.dao.Discove;
import com.ljc.socialdemo.db.dao.UserInfo;
import com.ljc.socialdemo.event.NameEvent;
import com.ljc.socialdemo.event.NormalEvent;
import com.ljc.socialdemo.event.PhotoEvent;
import com.ljc.socialdemo.utils.LogUtils;
import com.ljc.socialdemo.utils.PreferencesUtils;
import com.ljc.socialdemo.widget.MyToolBar;
import com.ljc.socialdemo.widget.UpLoadView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import greendao.DaoSession;
import greendao.UserInfoDao;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by 18075 on 2018/11/13.
 */

public class PubActivity extends Activity {


    @BindView(R.id.toolbar)
    MyToolBar  toolbar;
    @BindView(R.id.et_content)
    EditText   etContent;
    @BindView(R.id.grid_pics)
    GridLayout gridPics;
    @BindView(R.id.tv_count)
    TextView   tvCount;

    private List<String> paths     = new ArrayList<>();
    private int          PIC_LIMIT = 6;//默认添加的最大图片数量

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub);
        ButterKnife.bind(this);
        toolbar.setTitleLeftClickListener(v -> finish()).setTitleRightClickListener(v -> {
            //保存数据到数据库,并在发现界面去刷新数据为第一条
            String content = etContent.getText().toString();
            if (TextUtils.isEmpty(content)) {
                Toast.makeText(this, "提交内容不能为空", Toast.LENGTH_SHORT).show();
                return;
            }

            String imgs = "";
            //有图片添加
            if (paths.size() > 0) {
                StringBuffer sb = new StringBuffer();
                for (String path : paths) {
                    sb.append(path + ",");
                }
                imgs = sb.substring(0, sb.length() - 1);
            }

            DaoSession daoSession = SociaDemoApplication.getInstances().getDaoSession();
            long userId = PreferencesUtils.getLong(this, Constant.USERID_KEY);
            //查询当前的用户信息
            UserInfo unique = daoSession.getUserInfoDao().queryBuilder().where(UserInfoDao.Properties.UserId.eq(userId)).build().unique();
            LogUtils.d("发布:  "+ unique.toString());
            //写入数据
            String name = unique.getName();
            String head = unique.getHead();

            Discove discove = new Discove(content, name, TextUtils.isEmpty(head) ? "" : head, String.valueOf(System.currentTimeMillis()),
                    imgs, 0,  null,false, 0,userId,null);
            daoSession.getDiscoveDao().insert(discove);

            //刷新
            EventBus.getDefault().post(new NormalEvent(Constant.PUB));
            finish();
        });

        etContent.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 150) {
                    Toast.makeText(PubActivity.this, "亲,不能更多了~", Toast.LENGTH_SHORT).show();
                    s.delete(s.length() - 1, s.length());
                }
                tvCount.setText(s.length() + "/200");
            }
        });
        addViewToGrid();
    }

    private void addViewToGrid() {
        UpLoadView upLoadView = new UpLoadView(this);
        upLoadView.setListerner(tag -> {
            //去选择照片
            if (tag == null)
                initPermission();
        });
        upLoadView.setOnDeleteListener(tag -> {
            gridPics.removeViewAt(Integer.valueOf(tag));
            paths.remove(Integer.valueOf(tag));
        });
        gridPics.addView(upLoadView);
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
                // 开启多选
                .mutiSelect()
                // 多选时的最大数量   （默认 9 张）
                .mutiSelectMaxSize(PIC_LIMIT)
                // 已选择的图片路径
                .pathList((ArrayList<String>) paths)
                // 开启拍照功能 （默认关闭）  
                .showCamera()
                // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）  
                .filePath("/socia/picture")
                .build();
        ImageSelector.open(this, imageConfig);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ImageSelector.IMAGE_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    // 获取选中的图片路径列表 Get Images Path List  
                    List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
                    paths.clear();
                    paths.addAll(pathList);
                    gridPics.removeAllViews();
                    for (int i = 0; i < paths.size(); i++) {
                        UpLoadView upLoadView = new UpLoadView(this);
                        upLoadView.setImage(paths.get(i));
                        gridPics.addView(upLoadView);
                    }
                    if (gridPics.getChildCount() < PIC_LIMIT)
                        addViewToGrid();//小于最大数,还可以继续添加
                }
                break;
        }
    }
}
