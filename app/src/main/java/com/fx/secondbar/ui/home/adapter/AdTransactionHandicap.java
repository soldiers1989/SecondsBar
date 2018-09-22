package com.fx.secondbar.ui.home.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.btten.bttenlibrary.base.adapter.BtAdapter;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.btten.bttenlibrary.util.ViewHolder;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.Handicap;

/**
 * function:交易-盘口列表适配器
 * author: frj
 * modify date: 2018/9/12
 */
public class AdTransactionHandicap extends BtAdapter<Handicap>
{

    //用于标识是上盘口还是下盘口，上盘口和下盘口  盘口颜色不一致
    private boolean isTop;

    public AdTransactionHandicap(Context context, boolean isTop)
    {
        super(context);
        this.isTop = isTop;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.ad_transaction_handicap, parent, false);
        }
        TextView tv_index = ViewHolder.get(convertView, R.id.tv_index);
        TextView tv_num = ViewHolder.get(convertView, R.id.tv_num);
        TextView tv_price = ViewHolder.get(convertView, R.id.tv_price);
        if (isTop)
        {
            tv_index.setTextColor(Color.parseColor("#03c086"));
        } else
        {
            tv_index.setTextColor(Color.parseColor("#e94961"));
        }
        VerificationUtil.setViewValue(tv_index, getItem(position).getIndex(), "");
        VerificationUtil.setViewValue(tv_num, getItem(position).getNum(), "");
        VerificationUtil.setViewValue(tv_price, getItem(position).getPrice(), "");
        return convertView;
    }
}
