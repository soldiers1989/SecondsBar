package com.fx.secondbar.ui.person;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.btten.bttenlibrary.util.VerificationUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.RangeBean;
import com.fx.secondbar.util.GlideLoad;
import com.joooonho.SelectableRoundedImageView;

/**
 * function:排行榜列表适配器
 * author: frj
 * modify date: 2018/10/3
 */
public class AdLeaderBoard extends BaseQuickAdapter<RangeBean, BaseViewHolder>
{
    public AdLeaderBoard()
    {
        super(R.layout.ad_leader_board_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, RangeBean item)
    {
        ConstraintLayout contentView = helper.getView(R.id.contentView);
        ImageView img_rank = helper.getView(R.id.img_rank);
        TextView tv_rank = helper.getView(R.id.tv_rank);
        SelectableRoundedImageView img_avatar = helper.getView(R.id.img_avatar);
        TextView tv_income = helper.getView(R.id.tv_income);
        TextView tv_nickname = helper.getView(R.id.tv_nickname);

        GlideLoad.load(img_avatar, item.getImg(), true, R.mipmap.default_avatar, R.mipmap.default_avatar);
        VerificationUtil.setViewValue(tv_income, item.getAmt() + "Q");
        VerificationUtil.setViewValue(tv_nickname, item.getNickname());

        if (item.getLevel() <= 3)
        {
            contentView.setBackgroundResource(R.drawable.leader_board_item_higher);
            img_rank.setVisibility(View.VISIBLE);
            tv_rank.setVisibility(View.INVISIBLE);
            if (item.getLevel() == 1)
            {
                img_rank.setImageResource(R.mipmap.ic_rank_first);
            } else if (item.getLevel() == 2)
            {
                img_rank.setImageResource(R.mipmap.ic_rank_second);
            } else if (item.getLevel() == 3)
            {
                img_rank.setImageResource(R.mipmap.ic_rank_third);
            } else
            {
                img_rank.setImageResource(R.mipmap.ic_rank_third);
            }
        } else
        {
            contentView.setBackgroundResource(R.drawable.leader_board_item_bg);
            img_rank.setVisibility(View.INVISIBLE);
            tv_rank.setVisibility(View.VISIBLE);
            VerificationUtil.setViewValue(tv_rank, String.valueOf(item.getLevel()));
        }
    }
}
