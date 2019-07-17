package com.ljc.socialdemo.ui.home.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ljc.socialdemo.R;
import com.ljc.socialdemo.moudle.HomeContent;
import com.ljc.socialdemo.utils.TimeUtil;

import java.util.List;

/**
 * Created by 18075 on 2018/11/12.
 */

public class HomeAdapter extends BaseQuickAdapter<HomeContent.DataBean, BaseViewHolder> {

    public HomeAdapter() {
        super(R.layout.item_home);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeContent.DataBean item) {
        helper.setText(R.id.tv_title, item.getList_title());
        String publish_time = item.getPublish_time();
        helper.setText(R.id.tv_time, TimeUtil.getFormatTime(Long.valueOf(publish_time))); //需要转换
        helper.setText(R.id.tv_type, item.getChannel());

        List<String> list_img = item.getList_img();
        ImageView iv_icon = helper.getView(R.id.iv_icon);
        if (list_img != null && list_img.size() > 0)
            Glide.with(getRecyclerView().getContext())
                    .load(list_img.get(0))
                    .placeholder(com.jaiky.imagespickers.R.drawable.global_img_default)
                    .centerCrop()
                    .into(iv_icon);
    }
}
