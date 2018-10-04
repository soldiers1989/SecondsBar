package com.fx.secondbar.ui.search;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.util.SpaceDecorationUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fx.secondbar.R;
import com.fx.secondbar.ui.mall.AdMall;
import com.fx.secondbar.ui.home.adapter.AdQuote;

import java.util.ArrayList;
import java.util.List;

/**
 * function:搜索页
 * author: frj
 * modify date: 2018/9/10
 */
public class AcSearch extends ActivitySupport
{

    /**
     * 搜索商品
     */
    public static final int TYPE_COMMODITY = 1;
    /**
     * 搜索行情
     */
    public static final int TYPE_QUOTES = 2;

    private Toolbar toolbar;
    private EditText ed_search;
    private ImageView img_toolbar_left;
    private ImageView img_toolbar_right;
    private LinearLayout ll_title;
    private RecyclerView recyclerView;

    private BaseQuickAdapter adapter;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_search;
    }

    @Override
    protected void initView()
    {
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
                        adapter.setNewData(getDatas());
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
        int type = getIntent().getIntExtra(KEY, TYPE_COMMODITY);
        if (TYPE_COMMODITY == type)
        {
            ll_title.setVisibility(View.GONE);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            recyclerView.addItemDecoration(SpaceDecorationUtil.getDecoration(getResources().getDimensionPixelSize(R.dimen.mall_item_space), false, false, false));
            adapter = new AdMall();
            adapter.bindToRecyclerView(recyclerView);
        } else if (TYPE_QUOTES == type)
        {
            ll_title.setVisibility(View.VISIBLE);
            recyclerView.setPadding(getResources().getDimensionPixelOffset(R.dimen.search_content_plr), 0, getResources().getDimensionPixelOffset(R.dimen.search_content_plr), 0);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.addItemDecoration(SpaceDecorationUtil.getDecoration(getResources().getDimensionPixelSize(R.dimen.mall_item_space), false, false, false));
            adapter = new AdQuote();
            adapter.bindToRecyclerView(recyclerView);
        }
    }

    private List<String> getDatas()
    {
        List<String> list = new ArrayList<>();
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1536514197209&di=fd7806ccccb9f675e081158a168d217c&imgtype=0&src=http%3A%2F%2Fphotocdn.sohu.com%2F20140408%2FImg397875444.jpg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1536514197209&di=4a1d55696d17cc5ac675b325682949f3&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimage%2Fc0%253Dshijue1%252C0%252C0%252C294%252C40%2Fsign%3D5022a7eab8119313d34ef7f30d5166a2%2Fb17eca8065380cd79242cbc5ab44ad34598281bd.jpg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1536514197208&di=da96e93ec963b3ebaa1a5366b6374ad5&imgtype=0&src=http%3A%2F%2Fimg6.blog.eastmoney.com%2Fte%2Ftealemon%2F201404%2F20140409174203840.jpg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1536514197208&di=abc69a266d646a83c8a39e2738650be9&imgtype=0&src=http%3A%2F%2Fs1.sinaimg.cn%2Fmw690%2F006wmg2Hzy73dot1Fug80");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1536514197207&di=b9d08210a7d4e15e75132cfedadfedd9&imgtype=0&src=http%3A%2F%2Fimg.mp.sohu.com%2Fupload%2F20170815%2Fc31de52066b745e49c1e789a92148798_th.png");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1536514197207&di=b906894d2fbf9e2d40d6b5cc487efb7d&imgtype=0&src=http%3A%2F%2Fs4.sinaimg.cn%2Fmw690%2F006wmg2Hzy73WumZF2X13%26690");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1536514197207&di=d75ed4eb065a4fee99e6bd7c5f43eac3&imgtype=0&src=http%3A%2F%2Fimg.mp.itc.cn%2Fupload%2F20160921%2F0b12ece25105493db41a01b3aa33e5d4_th.jpg");
        return list;
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
                    adapter.setNewData(getDatas());
                    hideSoftInput(ed_search.getWindowToken());
                }
                break;
            case R.id.img_toolbar_left:
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
