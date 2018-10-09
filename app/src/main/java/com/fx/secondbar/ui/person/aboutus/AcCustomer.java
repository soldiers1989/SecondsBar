package com.fx.secondbar.ui.person.aboutus;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.util.DensityUtil;
import com.btten.bttenlibrary.util.ShowToast;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.CustomerBean;
import com.fx.secondbar.http.HttpManager;

import java.util.List;

import rx.Subscriber;

/**
 * function:联系客服
 * author: frj
 * modify date: 2018/10/4
 */
public class AcCustomer extends ActivitySupport
{

    private static final String URI_PHONE_FORMAT = "tel:%1$s";

    private TextView tv_online;
    private TextView tv_tips;
    private LinearLayout ll_phone;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_customer;
    }

    @Override
    protected void initView()
    {
        tv_online = findView(R.id.tv_online);
        tv_tips = findView(R.id.tv_tips);
        ll_phone = findView(R.id.ll_phone);
        findView(R.id.ib_back).setOnClickListener(this);
        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initListener()
    {
        tv_online.setOnClickListener(this);
    }

    @Override
    protected void initData()
    {
        getData();
    }

    /**
     * 获取客服数据
     */
    private void getData()
    {
        HttpManager.getCustomerInfo(new Subscriber<List<CustomerBean>>()
        {
            @Override
            public void onCompleted()
            {
            }

            @Override
            public void onError(Throwable e)
            {
                if (isDestroy())
                {
                    return;
                }
                e.printStackTrace();
                ShowToast.showToast(HttpManager.checkLoadError(e));
            }

            @Override
            public void onNext(List<CustomerBean> customerBeans)
            {
                if (isDestroy())
                {
                    return;
                }
                if (VerificationUtil.getSize(customerBeans) > 0)
                {

                    addViews(customerBeans, ll_phone);
                }
            }
        });
    }

    /**
     * 根据项添加子控件
     *
     * @param list
     * @param viewGroups
     */
    private void addViews(List<CustomerBean> list, LinearLayout viewGroups)
    {
        if (viewGroups != null && VerificationUtil.noEmpty(list))
        {
            int dataSize = list.size();
            //每行两项
            int temp = dataSize % 2;
            //计算子项大小
            int size = temp == 0 ? temp + dataSize / 2 : temp + dataSize / 2;
            for (int i = 0; i < size; i++)
            {
                String left = list.get(i * 2).getPhone();
                if (TextUtils.isEmpty(left))
                {
                    left = list.get(i * 2).getMobile();
                }
                String right = "";
                if (i * 2 + 1 < dataSize)
                {
                    right = list.get(i * 2 + 1).getPhone();
                    if (TextUtils.isEmpty(right))
                    {
                        right = list.get(i * 2 + 1).getMobile();
                    }
                }
                viewGroups.addView(createRows(left, right));
            }
        }
    }

    /**
     * 创建一行
     *
     * @param left  左边的号码（或微信号）
     * @param right 右边的号码（或微信号）
     * @return
     */
    private LinearLayout createRows(String left, String right)
    {
        LinearLayout rows = new LinearLayout(this);
        rows.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(this, 24));
        rows.setLayoutParams(params);
        rows.setGravity(Gravity.CENTER_VERTICAL);
        rows.addView(createText(left, true));
        rows.addView(createText(right, false));
        return rows;
    }

    /**
     * 创建TextView
     *
     * @param text   显示文本
     * @param isLeft 是否是左侧项
     * @return
     */
    private TextView createText(String text, boolean isLeft)
    {
        TextView textView = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        textView.setLayoutParams(params);
        textView.setText(text);
        textView.setMaxLines(1);
        textView.setGravity(isLeft ? Gravity.LEFT : Gravity.RIGHT);
        textView.setTextColor(Color.parseColor("#666666"));
        textView.setTextSize(15);
        textView.setOnClickListener(phoneClick);
        return textView;
    }

    /**
     * 电话项点击事件
     */
    private View.OnClickListener phoneClick = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            if (isFastDoubleClick(v))
            {
                return;
            }
            TextView tv = (TextView) v;
            String phone = getTextView(tv);
            if (!TextUtils.isEmpty(phone))
            {
                //跳转至拨号界面
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(String.format(URI_PHONE_FORMAT, phone)));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (intent.resolveActivity(getPackageManager()) != null)
                {
                    startActivity(intent);
                }
            }
        }
    };

    @Override
    public void onClick(View v)
    {
        if (isFastDoubleClick(v))
        {
            return;
        }
        switch (v.getId())
        {
            case R.id.ib_back:
                finish();
                break;
            case R.id.tv_online:
                try
                {
                    //可以跳转到添加好友，如果qq号是好友了，直接聊天
                    String url = "mqqwpa://im/chat?chat_type=wpa&uin=173681320";//uin是发送过去的qq号码
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (Exception e)
                {
                    e.printStackTrace();
                    ShowToast.showToast("请检查是否安装QQ");
                }
                break;
        }
    }

    @Override
    protected String[] getPermissionArrays()
    {
        return new String[0];
    }

    @Override
    protected int[] getPermissionInfoTips()
    {
        return new int[0];
    }
}
