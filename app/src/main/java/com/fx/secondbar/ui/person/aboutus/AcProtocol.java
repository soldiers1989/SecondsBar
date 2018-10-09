package com.fx.secondbar.ui.person.aboutus;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.fx.secondbar.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * function:用户协议
 * author: frj
 * modify date: 2018/9/29
 */
public class AcProtocol extends ActivitySupport
{

    private TextView tv_content;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_protocol;
    }

    @Override
    protected void initView()
    {
        tv_content = findView(R.id.tv_content);
        findView(R.id.ib_back).setOnClickListener(this);
        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initListener()
    {

    }

    @Override
    protected void initData()
    {
        tv_content.setText(readAssetResource());
    }

    /**
     * 读取Assets文件中资源
     *
     * @return
     */
    private String readAssetResource()
    {
        try
        {
            InputStreamReader inputReader = new InputStreamReader(getResources().getAssets().open("user_agreement.txt"));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            StringBuilder sb = new StringBuilder();
            while ((line = bufReader.readLine()) != null)
            {

                sb.append(stringChang(line));
            }
            return sb.toString();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }


    public String stringChang(String s)
    {
        StringBuffer sb = new StringBuffer();
        char[] c = s.toCharArray();
        for (int i = 0; i < c.length; i++)
        {
            if (c[i] != '\\')
            {
                if (c[i] != 'n')
                {
                    sb.append(c[i]);
                }
            } else
            {
                sb.append("\n");
            }
        }
        return sb.toString();
    }


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
