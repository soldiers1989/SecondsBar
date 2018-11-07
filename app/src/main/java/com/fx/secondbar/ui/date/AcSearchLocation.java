package com.fx.secondbar.ui.date;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.util.ShowToast;
import com.fx.secondbar.R;
import com.fx.secondbar.application.FxApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * function:搜索地理位置
 * author: frj
 * modify date: 2018/11/6
 */
public class AcSearchLocation extends ActivitySupport implements OnGetSuggestionResultListener, OnGetGeoCoderResultListener
{

    private Toolbar toolbar;
    private AutoCompleteTextView ed_search;
    private ImageView img_toolbar_left;
    private TextView img_toolbar_right;

    private SuggestionSearch mSuggestionSearch = null;
    private GeoCoder geoCoder;

    private ArrayAdapter<String> sugAdapter = null;

    private List<SuggestionResult.SuggestionInfo> list;

    private ProgressDialog dialog;

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
        geoCoder = GeoCoder.newInstance();
        geoCoder.setOnGetGeoCodeResultListener(this);
    }

    @Override
    protected void initListener()
    {
        img_toolbar_right.setOnClickListener(this);
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
        initDialog();
        sugAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line);
        ed_search.setAdapter(sugAdapter);
        ed_search.setThreshold(1);
    }

    /**
     * 初始化Dialog
     */
    private void initDialog()
    {
        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.progress_tips));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (v.getId() == R.id.img_toolbar_right)
        {
            if (TextUtils.isEmpty(getTextView(ed_search)))
            {
                ShowToast.showToast("请输入地点名称");
                return;
            }
            String city = "";
            if (FxApplication.getInstance().getLocationBean() != null)
            {
                city = FxApplication.getInstance().getLocationBean().getCity();
            }
            if (TextUtils.isEmpty(city))
            {
                ShowToast.showToast("暂未获取到您的位置");
                return;
            }
            if (dialog == null)
            {
                initDialog();
            }
            dialog.show();
            geoCoder.geocode(new GeoCodeOption()
                    .city(city)
                    .address(getTextView(ed_search)));
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

    @Override
    protected void onDestroy()
    {
        if (mSuggestionSearch != null)
        {
            mSuggestionSearch.destroy();
        }
        if (geoCoder != null)
        {
            geoCoder.destroy();
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

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult)
    {
        if (geoCodeResult == null || geoCodeResult.error != SearchResult.ERRORNO.NO_ERROR)
        {
            //没有检索到结果
        }
        if (isDestroy())
        {
            return;
        }
        Intent intent = new Intent();
        String address = "";
        String lat = "";
        String lng = "";
        if (dialog != null && dialog.isShowing())
        {
            dialog.dismiss();
        }
        if (ed_search != null)
        {
            address = getTextView(ed_search);
            intent.putExtra("address", address);
        }
        if (geoCodeResult.getLocation() != null)
        {
            lat = String.valueOf(geoCodeResult.getLocation().latitude);
            lng = String.valueOf(geoCodeResult.getLocation().longitude);
            intent.putExtra("lat", lat);
            intent.putExtra("lng", lng);
        }
        if (TextUtils.isEmpty(address) || TextUtils.isEmpty(lat) || TextUtils.isEmpty(lng))
        {
            ShowToast.showToast("未找到对应地址，请重新输入");
            if (ed_search != null)
            {
                ed_search.setText("");
            }
            return;
        }
        hideSoftInput(ed_search.getWindowToken());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult)
    {

    }
}
