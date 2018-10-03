package com.fx.secondbar.ui.person;

import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.btten.bttenlibrary.application.BtApplication;
import com.btten.bttenlibrary.util.DisplayUtil;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.LevelBean;
import com.fx.secondbar.util.LevelUtils;

/**
 * function:等级项
 * author: frj
 * modify date: 2018/10/2
 */
public class AdLevelItem extends BaseQuickAdapter<LevelBean, BaseViewHolder>
{
    public AdLevelItem()
    {
        super(R.layout.ad_level_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, LevelBean item)
    {
        TextView tv_level = helper.getView(R.id.tv_level);
        FrameLayout fl_name = helper.getView(R.id.fl_name);
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_q = helper.getView(R.id.tv_q);
        TextView tv_reward = helper.getView(R.id.tv_reward);
        calSize(tv_level, fl_name, tv_q, tv_reward);

        tv_level.setText("LV." + item.getLevel());
        VerificationUtil.setViewValue(tv_name, "LV" + item.getLevel() + " " + item.getName());
        VerificationUtil.setViewValue(tv_q, item.getEndnum());
        VerificationUtil.setViewValue(tv_reward, VerificationUtil.verifyDefault(item.getPrizeqcoin(), "0"));
        try
        {
            tv_name.setBackgroundResource(LevelUtils.getLevelIcons(Integer.parseInt(item.getLevel())));
        } catch (NumberFormatException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 计算尺寸
     *
     * @param tvLevel
     * @param flName
     * @param tvQ
     * @param tvReward
     */
    private void calSize(TextView tvLevel, FrameLayout flName, TextView tvQ, TextView tvReward)
    {
        int width = DisplayUtil.getScreenSize(BtApplication.getApplication()).widthPixels;
        width -= BtApplication.getApplication().getResources().getDimensionPixelSize(R.dimen.quote_item_title_plr) * 2;
        //比例为：1:1.2:1:1
        int oneWith = (int) (width / 4.2);
        LinearLayout.LayoutParams paramsLL = (LinearLayout.LayoutParams) tvLevel.getLayoutParams();
        if (paramsLL.width != oneWith)
        {
            paramsLL.width = oneWith;
            tvLevel.setLayoutParams(paramsLL);
        }

        LinearLayout.LayoutParams paramsPrice = (LinearLayout.LayoutParams) flName.getLayoutParams();
        if (paramsPrice.width != (width - 3 * oneWith))
        {
            paramsPrice.width = (width - 3 * oneWith);
            flName.setLayoutParams(paramsPrice);
        }

        LinearLayout.LayoutParams paramsUp = (LinearLayout.LayoutParams) tvQ.getLayoutParams();
        if (paramsUp.width != oneWith)
        {
            paramsUp.width = oneWith;
            tvQ.setLayoutParams(paramsUp);
        }

        LinearLayout.LayoutParams paramsPercent = (LinearLayout.LayoutParams) tvReward.getLayoutParams();
        if (paramsPercent.width != oneWith)
        {
            paramsPercent.width = oneWith;
            tvReward.setLayoutParams(paramsPercent);
        }
    }
}
