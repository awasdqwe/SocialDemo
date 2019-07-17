package com.ljc.socialdemo.ui.discove.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ljc.socialdemo.R;
import com.ljc.socialdemo.db.dao.Discove;
import com.ljc.socialdemo.db.dao.Zan;
import com.ljc.socialdemo.utils.TimeUtil;

/**
 * Created by 18075 on 2018/11/12.
 */

public class ZanAdapter extends BaseQuickAdapter<Zan, BaseViewHolder> {

    public ZanAdapter() {
        super(R.layout.item_zan);
    }

    @Override
    protected void convert(BaseViewHolder helper, Zan item) {
        helper.setText(R.id.tv_name,item.getName());
        helper.setText(R.id.tv_time, TimeUtil.getDescriptionTimeFromTimestamp(Long.valueOf(item.getCreateTime())));

        ImageView iv_head = helper.getView(R.id.iv_head);
        Glide.with(getRecyclerView().getContext())
                .load(item.getHeader())
                .placeholder(R.drawable.icon_user)
                .error(R.drawable.icon_user)
                .centerCrop()
                .into(iv_head);

    }
}
