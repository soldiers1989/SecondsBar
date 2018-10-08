package com.fx.secondbar.ui.search;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.util.ShowToast;
import com.btten.bttenlibrary.util.SpaceDecorationUtil;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.CommodityBean;
import com.fx.secondbar.bean.InfomationBean;
import com.fx.secondbar.bean.QuoteBean;
import com.fx.secondbar.http.HttpManager;
import com.fx.secondbar.ui.home.AcInformationDetail;
import com.fx.secondbar.ui.home.adapter.AdQuote;
import com.fx.secondbar.ui.home.item.adapter.AdInfomation;
import com.fx.secondbar.ui.mall.AcMallDetail;
import com.fx.secondbar.ui.mall.AdMall;
import com.fx.secondbar.ui.quote.AcQuoteDetail;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * function:搜索页
 * author: frj
 * modify date: 2018/9/10
 */
public class AcSearch extends ActivitySupport
{

    private static final int REQUEST_CODE_DETAIL = 1;

    /**
     * 搜索商品
     */
    public static final int TYPE_COMMODITY = 1;
    /**
     * 搜索行情
     */
    public static final int TYPE_QUOTES = 2;
    /**
     * 搜索资讯
     */
    public static final int TYPE_INFORMATION = 3;

    private ConstraintLayout content;
    private Toolbar toolbar;
    private EditText ed_search;
    private ImageView img_toolbar_left;
    private ImageView img_toolbar_right;
    private LinearLayout ll_title;
    private RecyclerView recyclerView;

    private BaseQuickAdapter adapter;
    //当前搜索页面的类型
    private int type;
    /**
     * 当前页码
     */
    private int currPage = PAGE_START;
    //当前搜索的关键字
    private String currSearch;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_search;
    }

    @Override
    protected void initView()
    {
        content = findView(R.id.content);
        toolbar = findView(R.id.toolbar);
        ed_search = findView(R.id.ed_search);
        img_toolbar_left = findView(R.id.img_toolbar_left);
        img_toolbar_right = findView(R.id.img_toolbar_right);
        recyclerView = findView(R.id.recyclerView);
        ll_title = findView(R.id.ll_title);
    }

    @Override
    protected void initListener()
    {
        ed_search.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                if (EditorInfo.IME_ACTION_SEARCH == actionId)
                {
                    if (adapter != null)
                    {
                        search(getTextView(ed_search), PAGE_START);
                        hideSoftInput(ed_search.getWindowToken());
                    }
                }
                return true;
            }
        });
        img_toolbar_right.setOnClickListener(this);
        img_toolbar_left.setOnClickListener(this);
    }

    @Override
    protected void initData()
    {
        setSupportActionBar(toolbar);
        type = getIntent().getIntExtra(KEY, TYPE_COMMODITY);
        if (TYPE_COMMODITY == type)
        {
            ll_title.setVisibility(View.GONE);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            recyclerView.addItemDecoration(SpaceDecorationUtil.getDecoration(getResources().getDimensionPixelSize(R.dimen.mall_item_space), false, false, false));
            adapter = new AdMall();
            adapter.bindToRecyclerView(recyclerView);
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener()
            {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position)
                {
                    if (isFastDoubleClick(view))
                    {
                        return;
                    }
                    AdMall adMall = (AdMall) adapter;
                    if (adMall != null)
                    {
                        jump(AcMallDetail.class, adMall.getData().get(position).getMerchandise_ID());
                    }
                }
            });
            adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener()
            {
                @Override
                public void onLoadMoreRequested()
                {
                    search(currSearch, currPage + 1);
                }
            }, recyclerView);
            content.setBackgroundColor(Color.parseColor("#f2f2f2"));
        } else if (TYPE_QUOTES == type)
        {
            ll_title.setVisibility(View.VISIBLE);
            recyclerView.setPadding(getResources().getDimensionPixelOffset(R.dimen.search_content_plr), 0, getResources().getDimensionPixelOffset(R.dimen.search_content_plr), 0);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.addItemDecoration(SpaceDecorationUtil.getDecoration(getResources().getDimensionPixelSize(R.dimen.mall_item_space), false, false, false));
            adapter = new AdQuote();
            adapter.bindToRecyclerView(recyclerView);
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener()
            {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position)
                {
                    if (isFastDoubleClick(view))
                    {
                        return;
                    }
                    AdQuote adQuote = (AdQuote) adapter;
                    if (adQuote == null)
                    {
                        return;
                    }
                    Bundle bundle = new Bundle();
                    String title = adQuote.getItem(position).getName();
                    if (!TextUtils.isEmpty(adQuote.getItem(position).getZjm()))
                    {
                        title = title + "(" + adQuote.getItem(position).getZjm() + ")";
                    }
                    bundle.putString(KEY_STR, title);
                    bundle.putInt(KEY, adQuote.getItem(position).getIscollection());
                    bundle.putString("id", adQuote.getItem(position).getPeopleid());
                    jump(AcQuoteDetail.class, bundle, REQUEST_CODE_DETAIL);
                }
            });
            adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener()
            {
                @Override
                public void onLoadMoreRequested()
                {
                    search(currSearch, currPage + 1);
                }
            }, recyclerView);
            content.setBackgroundColor(Color.parseColor("#292d32"));
        } else if (TYPE_INFORMATION == type)
        {
            ll_title.setVisibility(View.GONE);
            recyclerView.setPadding(0, getResources().getDimensionPixelOffset(R.dimen.search_content_plr), 0, 0);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.addItemDecoration(SpaceDecorationUtil.getDecoration(1, false, false, false));
            adapter = new AdInfomation();
            adapter.bindToRecyclerView(recyclerView);
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener()
            {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position)
                {
                    if (isFastDoubleClick(view))
                    {
                        return;
                    }
                    AdInfomation adInfomation = (AdInfomation) adapter;
                    if (adInfomation == null)
                    {
                        return;
                    }
                    AdInfomation.InfomationEntity entity = adInfomation.getItem(position);
                    if (AdInfomation.InfomationEntity.TYPE_MULTI_IMG == entity.getItemType() || AdInfomation.InfomationEntity.TYPE_SINGLE_IMG == entity.getItemType())
                    {
                        jump(AcInformationDetail.class, entity.getInfomationBean().getNews_ID(), false);
                    } else if (AdInfomation.InfomationEntity.TYPE_COMMODITY == entity.getItemType())
                    {
                        CommodityBean commodityBean = entity.getCommodityBean();
                        if (commodityBean != null)
                        {
                            jump(AcMallDetail.class, commodityBean.getMerchandise_ID());
                        }
                    }
                }
            });
            adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener()
            {
                @Override
                public void onLoadMoreRequested()
                {
                    search(currSearch, currPage + 1);
                }
            }, recyclerView);
            content.setBackgroundColor(Color.parseColor("#f2f2f2"));
        }
        if (adapter != null)
        {
            adapter.setEmptyView(createEmpty());
        }
    }

    /**
     * 创建空页面
     *
     * @return
     */
    private View createEmpty()
    {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_empty, recyclerView, false);
        TextView tv_empty_data = view.findViewById(R.id.tv_empty_data);
        tv_empty_data.setText("未搜索到数据");
        return view;
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
            case R.id.img_toolbar_right:
                if (adapter != null)
                {
                    search(getTextView(ed_search), PAGE_START);
                    hideSoftInput(ed_search.getWindowToken());
                }
                break;
            case R.id.img_toolbar_left:
                finish();
                break;
        }
    }

    /**
     * 搜索
     *
     * @param name 关键字
     * @param page 页码
     */
    private void search(String name, int page)
    {
        if (TYPE_COMMODITY == type)
        {
            searchMall(name, page);
        } else if (TYPE_QUOTES == type)
        {
            searchQuote(name, page);
        } else if (TYPE_INFORMATION == type)
        {
            searchInformation(name, page);
        }
    }

    /**
     * 搜索行情
     *
     * @param name
     * @param page
     */
    private void searchQuote(final String name, final int page)
    {
        HttpManager.searchQuote(name, page, PAGE_NUM, new Subscriber<List<QuoteBean>>()
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
            public void onNext(List<QuoteBean> quoteBeans)
            {
                if (isDestroy())
                {
                    return;
                }
                currPage = page;
                currSearch = name;
                if (page == PAGE_START)
                {
                    adapter.setNewData(quoteBeans);
                    if (VerificationUtil.getSize(quoteBeans) == 0)
                    {
                        ShowToast.showToast("暂无相关结果");
                    }
                } else
                {
                    adapter.addData(quoteBeans);
                }
                if (VerificationUtil.getSize(quoteBeans) >= PAGE_NUM)
                {
                    adapter.loadMoreComplete();
                } else
                {
                    adapter.loadMoreEnd();
                }
            }
        });
    }

    /**
     * 搜索商品
     *
     * @param name 关键字
     * @param page 页码
     */
    private void searchMall(final String name, final int page)
    {
        HttpManager.searchMall(name, page, PAGE_NUM, new Subscriber<List<CommodityBean>>()
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
            public void onNext(List<CommodityBean> commodityBeans)
            {
                if (isDestroy())
                {
                    return;
                }
                currPage = page;
                currSearch = name;
                if (page == PAGE_START)
                {
                    adapter.setNewData(commodityBeans);
                    if (VerificationUtil.getSize(commodityBeans) == 0)
                    {
                        ShowToast.showToast("暂无相关结果");
                    }
                } else
                {
                    adapter.addData(commodityBeans);
                }
                if (VerificationUtil.getSize(commodityBeans) >= PAGE_NUM)
                {
                    adapter.loadMoreComplete();
                } else
                {
                    adapter.loadMoreEnd();
                }
            }
        });
    }

    /**
     * 搜索资讯
     *
     * @param name 关键字
     * @param page 页码
     */
    private void searchInformation(final String name, final int page)
    {
        HttpManager.searchInformation(name, page, PAGE_NUM, new Subscriber<List<InfomationBean>>()
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
            public void onNext(List<InfomationBean> infomationBeans)
            {
                if (isDestroy())
                {
                    return;
                }
                currPage = page;
                currSearch = name;
                if (page == PAGE_START)
                {
                    adapter.setNewData(handlerInformationData(infomationBeans));
                    if (VerificationUtil.getSize(infomationBeans) == 0)
                    {
                        ShowToast.showToast("暂无相关结果");
                    }
                } else
                {
                    adapter.addData(handlerInformationData(infomationBeans));
                }
                if (VerificationUtil.getSize(infomationBeans) >= PAGE_NUM)
                {
                    adapter.loadMoreComplete();
                } else
                {
                    adapter.loadMoreEnd();
                }
            }
        });
    }

    /**
     * 数据处理
     *
     * @param infomationBeans
     * @return
     */
    private List<AdInfomation.InfomationEntity> handlerInformationData(List<InfomationBean> infomationBeans)
    {
        List<AdInfomation.InfomationEntity> list = new ArrayList<>();
        if (infomationBeans != null)
        {
            for (int i = 0; i < infomationBeans.size(); i++)
            {

                String pictures = infomationBeans.get(i).getPictures();
                String[] picture = null;
                if (!TextUtils.isEmpty(pictures))
                {
                    picture = pictures.split(",");
                }
                AdInfomation.InfomationEntity infomationEntity = new AdInfomation.InfomationEntity((picture != null && picture.length != 1) ? AdInfomation.InfomationEntity.TYPE_MULTI_IMG : AdInfomation.InfomationEntity.TYPE_SINGLE_IMG, infomationBeans.get(i));
                list.add(infomationEntity);
            }
        }
        return list;
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_CODE_DETAIL == requestCode && (RESULT_OK == resultCode || AcQuoteDetail.RESULT_CODE_TRANSACTION == resultCode))
        {

            setResult(resultCode, data);
            finish();
        }
    }
}
