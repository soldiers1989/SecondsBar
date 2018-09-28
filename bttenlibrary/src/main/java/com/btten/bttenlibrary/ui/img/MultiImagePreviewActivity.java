package com.btten.bttenlibrary.ui.img;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.btten.bttenlibrary.R;
import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.util.DisplayUtil;
import com.btten.bttenlibrary.util.ResourceHelper;
import com.btten.bttenlibrary.util.glide.GlideUtils;
import com.btten.bttenlibrary.view.photoview.PhotoView;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

/**
 * 选择图片预览
 *
 * @author frj
 */
public class MultiImagePreviewActivity extends ActivitySupport implements OnPageChangeListener
{

    private Toolbar toolbar;
    private ImageView img_toolbar_left;
    private TextView tv_mark;
    private ViewPager viewPager;
    private ArrayList<String> imgPaths;

    @Override
    protected void onCreate(Bundle arg0)
    {
        super.onCreate(arg0);
    }

    @Override
    protected int getLayoutResId()
    {
        return R.layout.activity_preview;
    }

    @Override
    protected void initView()
    {
        img_toolbar_left = findView(R.id.img_toolbar_left);
        toolbar = findView(R.id.toolbar);
        tv_mark = findView(R.id.tv_mark);
        viewPager = findView(R.id.viewPager);
        setSupportActionBar(toolbar);
//        initToolBar();
    }

    @Override
    protected void initListener()
    {
        img_toolbar_left.setOnClickListener(this);
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    protected void initData()
    {
        imgPaths = getIntent().getStringArrayListExtra(ConstantValue.EXTRA_DEFAULT_SELECTED_LIST);
        viewPager.setAdapter(mPagerAdapter);
        if (imgPaths != null && imgPaths.size() > 0)
        {
            viewPager.setCurrentItem(0);
            tv_mark.setText((0 + 1) + "/" + imgPaths.size());
        }
    }

    /**
     * 初始化Toolbar高度
     */
    private void initToolBar()
    {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) toolbar.getLayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            int statusBarHeight = DisplayUtil.getStatusBarHeight(this);
            params.height += statusBarHeight;
            toolbar.setPadding(0, statusBarHeight, 0, 0);
        }
    }

    @Override
    protected String[] getPermissionArrays()
    {
        return new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
    }

    @Override
    protected int[] getPermissionInfoTips()
    {
        return new int[]{ResourceHelper.getInstance(getApplicationContext()).getStringId("permission_write_external_storage_tips")};
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        finish();
    }

    private PagerAdapter mPagerAdapter = new PagerAdapter()
    {

        @Override
        public int getCount()
        {
            if (imgPaths == null)
            {
                return 0;
            } else
            {
                return imgPaths.size();
            }
        }

        @Override
        public View instantiateItem(final ViewGroup container, final int position)
        {
            final PhotoView photoPreview = new PhotoView(MultiImagePreviewActivity.this);
            photoPreview.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            String path = imgPaths.get(position);
            photoPreview.enable();
            if (path.indexOf("http://") != 0)
            {
                path = "file://" + path;
            }
            RequestOptions options = GlideUtils.getDefaultOptions();
            options.diskCacheStrategy(DiskCacheStrategy.ALL);
            options.skipMemoryCache(true);
            options.fitCenter();
            GlideUtils.load(MultiImagePreviewActivity.this, path, photoPreview, options);
            ((ViewPager) container).addView(photoPreview);
            return photoPreview;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object)
        {
            return view == object;
        }

    };

    @Override
    public void onPageScrollStateChanged(int arg0)
    {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2)
    {

    }

    @Override
    public void onPageSelected(int arg0)
    {
        if (imgPaths != null && imgPaths.size() > 0)
        {
            tv_mark.setText((arg0 + 1) + "/" + imgPaths.size());
        }
    }
}
