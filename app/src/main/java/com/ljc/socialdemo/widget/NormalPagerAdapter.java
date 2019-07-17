package com.ljc.socialdemo.widget;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ljc.socialdemo.Constant;
import com.ljc.socialdemo.ui.WebActivity;

import java.util.List;

/**
 * Created by 18075 on 2018/11/12.
 * viewpager的普通适配器
 */

public class NormalPagerAdapter extends PagerAdapter {
    private List listImg;
    private List<String> listUrl;
    private Context      context;

    public NormalPagerAdapter(List listImg,List<String> listUrl, Context context) {
        this.listImg = listImg;
        this.listUrl = listUrl;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listImg.size();
    }

    //判断是否是否为同一张图片，这里返回方法中的两个参数做比较就可以
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
    //设置viewpage内部东西的方法，如果viewpage内没有子空间滑动产生不了动画效果
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Object urls = listImg.get(position);
        if (urls instanceof String) {
            Glide.with(context)
                    .load(urls)
                    .placeholder(com.jaiky.imagespickers.R.drawable.global_img_default)
                    .centerCrop()
                    .into(imageView);
        }else if (urls instanceof Integer){
            imageView.setImageResource((Integer) urls);
        }
        container.addView(imageView);
        imageView.setOnClickListener(v -> {
            if (listUrl == null || listUrl.size() == 0) return;
            Intent intent = new Intent(context, WebActivity.class);
            intent.putExtra(Constant.WEB_URL,listUrl.get(position));
            context.startActivity(intent);
        });
        //最后要返回的是控件本身
        return imageView;
    }
    //因为它默认是看三张图片，第四张图片的时候就会报错，还有就是不要返回父类的作用
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        //         super.destroyItem(container, position, object);
    }
//    //目的是展示title上的文字，
//    @Override
//    public CharSequence getPageTitle(int position) {
//        return mainTitlesArray[position];
//    }
}
