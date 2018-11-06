package com.fx.secondbar.ui.date;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.btten.bttenlibrary.base.ActivitySupport;
import com.fx.secondbar.R;
import com.fx.secondbar.application.FxApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * function:搜索地理位置
 * author: frj
 * modify date: 2018/11/6
 */
public class AcSearchLocation extends ActivitySupport implements OnGetSuggestionResultListener
{

    private Toolbar toolbar;
    private AutoCompleteTextView ed_search;
    private ImageView img_toolbar_left;
    private ImageView img_toolbar_right;

    private SuggestionSearch mSuggestionSearch = null;

    private ArrayAdapter<String> sugAdapter = null;

    private List<SuggestionResult.SuggestionInfo> list;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_search_location;
    }

    @Override
    protected void initView()
    {
        toolbar = findView(R.id.toolbar);
        ed_search = findView(R.id.ed_search);
        img_toolbar_left = findView(R.id.img_toolbar_left);
        img_toolbar_right = findView(R.id.img_toolbar_right);

        // 初始化建议搜索模块，注册建议搜索事件监听
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(this);
    }

    @Override
    protected void initListener()
    {
        /* 当输入关键字变化时，动态更新建议列表 */
        ed_search.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void afterTextChanged(Editable arg0)
            {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3)
            {

            }

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3)
            {
                if (cs.length() <= 0)
                {
                    return;
                }
                String city = "";
                if (FxApplication.getInstance().getLocationBean() != null)
                {
                    city = FxApplication.getInstance().getLocationBean().getCity();
                }
                /* 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新 */
                mSuggestionSearch.requestSuggestion((new SuggestionSearchOption())
                        .keyword(cs.toString())
                        .city(city));
            }
        });
        img_toolbar_right.setOnClickListener(this);
        img_toolbar_left.setOnClickListener(this);

        ed_search.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if (list != null && list.size() > position)
                {
                    String address = list.get(position).key;
                    String lat = String.valueOf(list.get(position).pt.latitude);
                    String lng = String.valueOf(list.get(position).pt.longitude);
                    Intent intent = new Intent();
                    intent.putExtra("address", address);
                    intent.putExtra("lat", lat);
                    intent.putExtra("lng", lng);
                    hideSoftInput(ed_search.getWindowToken());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    @Override
    protected void initData()
    {
        setSupportActionBar(toolbar);
        sugAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line);
        ed_search.setAdapter(sugAdapter);
        ed_search.setThreshold(1);
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

    @Override
    protected void onDestroy()
    {
        if (mSuggestionSearch != null)
        {
            mSuggestionSearch.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onGetSuggestionResult(SuggestionResult res)
    {
        if (res == null || res.getAllSuggestions() == null)
        {
            return;
        }
        if (list == null)
        {
            list = new ArrayList<>();
        } else
        {
            list.clear();
        }
        list.addAll(res.getAllSuggestions());
        List<String> suggest = new ArrayList<>();
        for (SuggestionResult.SuggestionInfo info : res.getAllSuggestions())
        {
            if (info.key != null)
            {
                suggest.add(info.key);
            }
        }

        sugAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,
                suggest);
        ed_search.setAdapter(sugAdapter);
    }
}
