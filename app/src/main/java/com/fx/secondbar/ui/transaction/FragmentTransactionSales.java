package com.fx.secondbar.ui.transaction;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fx.secondbar.R;
import com.fx.secondbar.bean.Handicap;
import com.fx.secondbar.ui.home.adapter.AdTransactionHandicap;
import com.fx.secondbar.ui.home.item.FragmentViewPagerBase;
import com.fx.secondbar.view.CopyListView;

import java.util.ArrayList;
import java.util.List;

/**
 * function:交易卖出
 * author: frj
 * modify date: 2018/9/20
 */
public class FragmentTransactionSales extends FragmentViewPagerBase
{

    private CopyListView list_top;
    private CopyListView list_bottom;
    private TextView tv_price_down;
    private TextView tv_price_up;

    public static FragmentTransactionSales newInstance()
    {
        return new FragmentTransactionSales();
    }

    @Override
    public void onStarShow()
    {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.f_transaction_sale, container, false);
    }

    @Override
    protected void initView()
    {
        list_top = findView(R.id.list_top);
        list_bottom = findView(R.id.list_bottom);
        tv_price_down = findView(R.id.tv_price_down);
        tv_price_up = findView(R.id.tv_price_up);
    }

    @Override
    protected void initListener()
    {

    }

    @Override
    protected void initData()
    {
        AdTransactionHandicap adTransactionHandicap = new AdTransactionHandicap(getContext(), true);
        adTransactionHandicap.addList(getDatas(), false);
        list_top.setAdapter(adTransactionHandicap);

        AdTransactionHandicap adBottom = new AdTransactionHandicap(getContext(), false);
        adBottom.addList(getBottomDatas(), false);
        list_bottom.setAdapter(adBottom);

        String down = String.format(getString(R.string.transaction_head_price_down), "2.58");
        String downTips = getString(R.string.transaction_head_price_down_tips);
        String up = String.format(getString(R.string.transaction_head_price_up), "3.16");
        String upTips = getString(R.string.transaction_head_price_up_tips);
        SpannableString spDown = new SpannableString(down);
        spDown.setSpan(new ForegroundColorSpan(Color.parseColor("#03c086")), downTips.length(), down.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_price_down.setText(spDown);

        SpannableString spUp = new SpannableString(up);
        spUp.setSpan(new ForegroundColorSpan(Color.parseColor("#e94961")), upTips.length(), up.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_price_up.setText(spUp);
    }

    private List<Handicap> getDatas()
    {
        List<Handicap> list = new ArrayList<>();
        list.add(new Handicap("卖5", "0.00", "0.00"));
        list.add(new Handicap("卖4", "0.00", "0.00"));
        list.add(new Handicap("卖3", "0.00", "0.00"));
        list.add(new Handicap("卖2", "0.00", "0.00"));
        list.add(new Handicap("卖1", "0.00", "0.00"));
        return list;
    }

    private List<Handicap> getBottomDatas()
    {
        List<Handicap> list = new ArrayList<>();
        list.add(new Handicap("买5", "0.00", "0.00"));
        list.add(new Handicap("买4", "0.00", "0.00"));
        list.add(new Handicap("买3", "0.00", "0.00"));
        list.add(new Handicap("买2", "0.00", "0.00"));
        list.add(new Handicap("买1", "0.00", "0.00"));
        return list;
    }
}
