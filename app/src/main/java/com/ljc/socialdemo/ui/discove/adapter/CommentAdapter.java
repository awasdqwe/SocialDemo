package com.ljc.socialdemo.ui.discove.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ljc.socialdemo.R;
import com.ljc.socialdemo.db.dao.Commen;
import com.ljc.socialdemo.db.dao.Discove;
import com.ljc.socialdemo.utils.TimeUtil;

/**
 * Created by 18075 on 2018/11/12.
 */

public class CommentAdapter extends BaseQuickAdapter<Commen, BaseViewHolder> {

    public CommentAdapter() {
        super(R.layout.item_comment);
    }

    @Override
    protected void convert(BaseViewHolder helper, Commen item) {
        helper.setText(R.id.tv_name,item.getName());
        helper.setText(R.id.tv_content,item.getContent());
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
