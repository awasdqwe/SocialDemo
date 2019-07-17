package com.ljc.socialdemo.ui.discove.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ljc.socialdemo.Constant;
import com.ljc.socialdemo.R;
import com.ljc.socialdemo.db.dao.Discove;
import com.ljc.socialdemo.moudle.HomeContent;
import com.ljc.socialdemo.utils.PreferencesUtils;
import com.ljc.socialdemo.utils.TimeUtil;
import com.ljc.socialdemo.widget.GlideRoundTransUtils;

import java.util.List;

/**
 * Created by 18075 on 2018/11/12.
 */

public class DiscoveAdapter extends BaseQuickAdapter<Discove, BaseViewHolder> {

    public DiscoveAdapter() {
        super(R.layout.item_discove);
    }

    @Override
    protected void convert(BaseViewHolder helper, Discove item) {
        helper.setText(R.id.tv_content, item.getContent());
        helper.setText(R.id.tv_time, TimeUtil.getDescriptionTimeFromTimestamp(Long.valueOf(item.getCreateTime())));
        int commentNum = item.getCommentNum();
        if (commentNum > 0) {
            helper.setText(R.id.tv_comment, "评论(" + commentNum + ")");
        } else {
            helper.setText(R.id.tv_comment, "评论");
        }
        int zanNum = item.getZanNum();
        if (zanNum > 0) {
            helper.setText(R.id.tv_zan, "点赞(" + zanNum + ")");
        } else {
            helper.setText(R.id.tv_zan, "点赞");
        }

        TextView tv_zan = helper.getView(R.id.tv_zan);
        long userId = PreferencesUtils.getLong(getRecyclerView().getContext(), Constant.USERID_KEY);
        if (userId == item.getUserId() && item.getIsZan())
            tv_zan.setSelected(true);
        else tv_zan.setSelected(false);

        helper.addOnClickListener(R.id.tv_comment);
        helper.addOnClickListener(R.id.tv_zan);

        ImageView iv_icon = helper.getView(R.id.iv_icon);
        String imgs = item.getImgs();
        if (!TextUtils.isEmpty(imgs)) {
            String[] split = imgs.split(",");
            Glide.with(getRecyclerView().getContext())
                    .load(split[0])
                    .placeholder(com.jaiky.imagespickers.R.drawable.global_img_default)
                    .error(com.jaiky.imagespickers.R.drawable.global_img_default)
                    .centerCrop()
                    .transform(new GlideRoundTransUtils(getRecyclerView().getContext(), 5))
                    .into(iv_icon);
        } else {
            iv_icon.setVisibility(View.GONE);
        }

        ImageView iv_head = helper.getView(R.id.iv_head);
        Glide.with(getRecyclerView().getContext())
                .load(item.getHeader())
                .placeholder(R.drawable.icon_user)
                .error(R.drawable.icon_user)
                .centerCrop()
                .into(iv_head);
    }
}
