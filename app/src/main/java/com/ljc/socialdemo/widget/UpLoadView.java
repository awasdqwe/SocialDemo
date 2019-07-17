package com.ljc.socialdemo.widget;

/**
 * Created by chejiangwei on 2018/3/9.
 * Describe:
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ljc.socialdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 上传view
 */
public class UpLoadView extends FrameLayout {
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.iv_delete)
    ImageView ivDelete;
    private Listener mListener;
    private onDeleteListener onDeleteListener;

    public UpLoadView(@NonNull Context context) {
        super(context);
        inflate(context, R.layout.view_upload, this);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_pic, R.id.iv_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_pic:
                mListener.clickPic((String) getTag());
                break;
            case R.id.iv_delete:
                removeSelfFromParent(this);
                onDeleteListener.clickDelete((String) getTag());
                break;
        }
    }

    /**
     * 把自身从父View中移除
     */
    public static void removeSelfFromParent(View view) {
        if (view != null) {
            ViewParent parent = view.getParent();
            if (parent != null && parent instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) parent;
                group.removeView(view);
            }
        }
    }

    public void setImage(String fileName) {
        if (fileName != null) {
            Glide.with(getContext()).load(fileName)
                    .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                    .centerCrop()
                    .placeholder(com.jaiky.imagespickers.R.drawable.global_img_default).error(com.jaiky.imagespickers.R.drawable.global_img_default)
                    .into(ivPic);
            setTag(fileName);
        }
    }

    /**
     * 加载视频图片
     */
    public void setBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            ivPic.setImageBitmap(bitmap);
//            ivDelete.setVisibility(View.VISIBLE);
            setTag("video");
        }
    }


    public void setListerner(Listener listerner) {
        this.mListener = listerner;
    }

    public void setOnDeleteListener(onDeleteListener listerner) {
        this.onDeleteListener = listerner;
    }


    public interface Listener {
        void clickPic(String tag);
    }

    public interface onDeleteListener {
        void clickDelete(String tag);
    }
}
