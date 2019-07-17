package com.ljc.socialdemo.ui.message.adapter;


import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ljc.socialdemo.R;
import com.ljc.socialdemo.db.dao.Message;


/**
 * Created by 18075 on 2018/11/9.
 */

public class AdviceAdapter extends BaseQuickAdapter<Message, BaseViewHolder> {
    public AdviceAdapter() {
        super(R.layout.item_advice);
    }

    @Override
    protected void convert(BaseViewHolder helper, Message item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_content, item.getContent());
        helper.setText(R.id.tv_time, item.getTime());
        ImageView iv_icon = helper.getView(R.id.iv_icon);
        Glide.with(getRecyclerView().getContext())
                .load(item.getHead())
                .error(R.drawable.icon_user)
                .placeholder(R.drawable.icon_user)
                .into(iv_icon);
    }

}
