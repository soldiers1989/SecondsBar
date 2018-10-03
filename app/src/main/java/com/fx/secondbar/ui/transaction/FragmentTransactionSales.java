package com.fx.secondbar.ui.transaction;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.btten.bttenlibrary.base.bean.ResponseBean;
import com.btten.bttenlibrary.util.Arithmetic;
import com.btten.bttenlibrary.util.ShowToast;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.CommissionBean;
import com.fx.secondbar.bean.TransactionBean;
import com.fx.secondbar.http.HttpManager;
import com.fx.secondbar.ui.home.adapter.AdTransactionHandicap;
import com.fx.secondbar.util.CashierInputFilter;
import com.fx.secondbar.util.ProgressDialogUtil;
import com.fx.secondbar.view.CopyListView;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * function:交易卖出
 * author: frj
 * modify date: 2018/9/20
 */
public class FragmentTransactionSales extends FragmentTransactionItem
{

    private CopyListView list_top;
    private CopyListView list_bottom;
    private TextView tv_price_down;     //跌停价格
    private TextView tv_price_up;    //涨停价格
    private TextView tv_new_price;  //最新价格
    private TextView tv_can_num;     //可购买的秒数
    private TextView tv_buy_submit;
    private EditText ed_input;      //输入的价格
    private EditText ed_seconds;    //购买的秒数
    private TextView tv_price;      //总价
    private TextView tv_price_token;    //可用的代币数量
    private TextView tv_code;       //代币名称
    private TextView tv_price_ste;  //可用STE
    private TextView tv_time;       //可用时间

    /**
     * 最低价
     */
    private double priceDown;
    /**
     * 最高价
     */
    private double priceUp;
    /**
     * 可卖出的秒数
     */
    private int canSaleSeconds = 0;

    private ProgressDialog dialog;

    private AdTransactionHandicap adSale;
    private AdTransactionHandicap adBuy;

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
        tv_new_price = findView(R.id.tv_new_price);
        tv_buy_submit = findView(R.id.tv_buy_submit);
        ed_input = findView(R.id.ed_input);
        ed_seconds = findView(R.id.ed_seconds);
        tv_price = findView(R.id.tv_price);
        tv_can_num = findView(R.id.tv_can_num);
        tv_price_token = findView(R.id.tv_price_token);
        tv_code = findView(R.id.tv_code);
        tv_price_ste = findView(R.id.tv_price_ste);
        tv_time = findView(R.id.tv_time);
    }

    @Override
    protected void initListener()
    {
        tv_buy_submit.setOnClickListener(this);
        addFilter(ed_input, new CashierInputFilter());
        ed_input.addTextChangedListener(watcher);
        ed_seconds.addTextChangedListener(watcher);
    }

    @Override
    protected void initData()
    {
        adSale = new AdTransactionHandicap(getContext(), true);
        list_top.setAdapter(adSale);

        adBuy = new AdTransactionHandicap(getContext(), false);
        list_bottom.setAdapter(adBuy);

        dialog = ProgressDialogUtil.getProgressDialog(getActivity(), getString(R.string.progress_tips), true);

        //是否将要刷新数据
        if (isPrepareRefresh)
        {
            refreshData(peopleId);
            isPrepareRefresh = false;
        } else
        {
            bindData(new TransactionBean());
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        //是否将要刷新数据
        if (isPrepareRefresh)
        {
            refreshData(peopleId);
            isPrepareRefresh = false;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden)
    {
        super.onHiddenChanged(hidden);
        if (!isHidden())
        {
            //是否将要刷新数据
            if (isPrepareRefresh)
            {
                refreshData(peopleId);
                isPrepareRefresh = false;
            }
        }
    }

    private TextWatcher watcher = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {

        }

        @Override
        public void afterTextChanged(Editable s)
        {
            try
            {
                //计算总价
                String priceStr = getTextView(ed_input);
                String secondStr = getTextView(ed_seconds);
                float inputPrice = Float.parseFloat(priceStr);
                int inputSecond = Integer.parseInt(secondStr);
                VerificationUtil.setViewValue(tv_price, Arithmetic.mul(inputPrice, inputSecond) + "STE");
            } catch (NumberFormatException e)
            {
                e.printStackTrace();
            }
        }
    };

    /**
     * 添加输入过滤器
     *
     * @param ed
     * @param inputFilter
     */
    private void addFilter(EditText ed, InputFilter inputFilter)
    {
        InputFilter inputFilters[] = ed.getFilters();
        InputFilter afterFilters[] = null;
        if (inputFilters != null)
        {
            afterFilters = new InputFilter[inputFilters.length + 1];
            for (int i = 0; i < inputFilters.length; i++)
            {
                afterFilters[i] = inputFilters[i];
            }
            afterFilters[inputFilters.length] = inputFilter;
        } else
        {
            afterFilters = new InputFilter[1];
            afterFilters[0] = inputFilter;
        }
        ed.setFilters(afterFilters);
    }


    /**
     * 绑定数据
     *
     * @param bean
     */
    private void bindData(TransactionBean bean)
    {
        if (bean != null)
        {
            String down = String.format(getString(R.string.transaction_head_price_down), String.valueOf(bean.getPrice_DT()));
            String downTips = getString(R.string.transaction_head_price_down_tips);
            String up = String.format(getString(R.string.transaction_head_price_up), String.valueOf(bean.getPrice_ZT()));
            String upTips = getString(R.string.transaction_head_price_up_tips);
            SpannableString spDown = new SpannableString(down);
            spDown.setSpan(new ForegroundColorSpan(Color.parseColor("#03c086")), downTips.length(), down.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv_price_down.setText(spDown);

            SpannableString spUp = new SpannableString(up);
            spUp.setSpan(new ForegroundColorSpan(Color.parseColor("#e94961")), upTips.length(), up.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv_price_up.setText(spUp);

            VerificationUtil.setViewValue(tv_new_price, String.format(getString(R.string.transaction_head_new_price), String.valueOf(bean.getPrice())));
            VerificationUtil.setViewValue(tv_code, "可用" + bean.getZjm());
            VerificationUtil.setViewValue(tv_can_num, Arithmetic.doubleToStr(bean.getHaveseconds()));
            VerificationUtil.setViewValue(tv_price_ste, String.valueOf(bean.getBalanceamt()));
            VerificationUtil.setViewValue(tv_time, String.valueOf(bean.getHaveseconds()));

            priceDown = bean.getPrice_DT();
            priceUp = bean.getPrice_ZT();
//            canSaleSeconds = bean.getCanbuyseconds().intValue();
            canSaleSeconds = bean.getHaveseconds().intValue();

            addAdapterData(bean.getList_buy(), adBuy, list_bottom);
            addAdapterData(bean.getList_sell(), adSale, list_top);
        }
    }

    /**
     * 添加适配器数据
     *
     * @param sourceList   数据源
     * @param adapter      适配器对象
     * @param copyListView 列表对象
     */
    private void addAdapterData(List<CommissionBean> sourceList, AdTransactionHandicap adapter, CopyListView copyListView)
    {
        List<CommissionBean> list = new ArrayList<>();
        int size = VerificationUtil.getSize(sourceList);
        if (size > 5)
        {
            for (int i = 0; i < 5; i++)
            {
                list.add(i, sourceList.get(i));
            }
        } else if (size < 5)
        {
            for (int i = 0; i < size; i++)
            {
                list.add(sourceList.get(i));
            }
            for (int i = 0; i < (5 - size); i++)
            {
                list.add(new CommissionBean());
            }
        }
        adapter.addList(list, false);
        copyListView.setAdapter(adapter);
    }

    /**
     * 刷新数据
     */
    private void refreshData(String peopleId)
    {
        if (dialog != null)
        {
            dialog.show();
        }
        HttpManager.getTransactionCenter(peopleId, new Subscriber<TransactionBean>()
        {
            @Override
            public void onCompleted()
            {

            }

            @Override
            public void onError(Throwable e)
            {
                if (isNetworkCanReturn())
                {
                    return;
                }
                e.printStackTrace();
                if (dialog != null)
                {
                    dialog.dismiss();
                }
                ShowToast.showToast(HttpManager.checkLoadError(e));
            }

            @Override
            public void onNext(TransactionBean transactionBean)
            {
                if (isNetworkCanReturn())
                {
                    return;
                }
                if (dialog != null)
                {
                    dialog.dismiss();
                }
                bindData(transactionBean);
            }
        });
    }

    /**
     * 清除输入值
     */
    private void clearInputValue()
    {
        ed_input.setText("");
        ed_seconds.setText("");
        tv_price.setText("");
    }

    /**
     * 卖出
     */
    private void sale(String price, String seconds, final String peopleId)
    {
        if (dialog != null)
        {
            dialog.show();
        }
        HttpManager.saleTransaction(price, seconds, peopleId, new Subscriber<ResponseBean>()
        {
            @Override
            public void onCompleted()
            {

            }

            @Override
            public void onError(Throwable e)
            {
                if (isNetworkCanReturn())
                {
                    return;
                }
                if (dialog != null)
                {
                    dialog.dismiss();
                }
                e.printStackTrace();
                ShowToast.showToast(HttpManager.checkLoadError(e));
            }

            @Override
            public void onNext(ResponseBean responseBean)
            {
                if (isNetworkCanReturn())
                {
                    return;
                }
                if (dialog != null)
                {
                    dialog.dismiss();
                }
                ShowToast.showToast("卖出成功");
                refreshData(peopleId);
                clearInputValue();
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.tv_buy_submit:
                try
                {
                    if (VerificationUtil.requiredFieldValidator(getActivity(), new View[]{ed_input, ed_seconds}, new String[]{"请输入购买价格", "请输入购买的秒数"}))
                    {
                        int seconds = Integer.parseInt(getTextView(ed_seconds));
                        if (seconds > canSaleSeconds)
                        {
                            ShowToast.showToast("您卖出的秒数不能大于您可卖出的秒数");
                            return;
                        }
                        double price = Double.parseDouble(getTextView(ed_input));
                        if (price > priceUp)
                        {
                            ShowToast.showToast("您卖出的价格不能大于涨停价格");
                            return;
                        }
                        if (price < priceDown)
                        {
                            ShowToast.showToast("您卖出的价格不能小于跌停价格");
                            return;
                        }
                        sale(String.valueOf(price), String.valueOf(seconds), peopleId);
                    }
                } catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                break;
        }
    }
}
