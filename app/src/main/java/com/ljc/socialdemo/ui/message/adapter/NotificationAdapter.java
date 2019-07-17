package com.ljc.socialdemo.ui.message.adapter;


import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ljc.socialdemo.R;
import com.ljc.socialdemo.db.dao.Message;
import com.ljc.socialdemo.db.dao.Notification;


/**
 * Created by 18075 on 2018/11/9.
 */

public class NotificationAdapter extends BaseQuickAdapter<Notification, BaseViewHolder> {
    public NotificationAdapter() {
        super(R.layout.item_notifica);
    }

    @Override
    protected void convert(BaseViewHolder helper, Notification item) {
        helper.setText(R.id.tv_content, item.getContent());
    }

}
