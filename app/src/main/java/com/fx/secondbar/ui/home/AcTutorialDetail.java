package com.fx.secondbar.ui.home;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.glide.GlideApp;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.TurialBean;
import com.fx.secondbar.util.Constants;

/**
 * function:教程详情
 * author: frj
 * modify date: 2018/9/27
 */
public class AcTutorialDetail extends ActivitySupport
{

    private TextView tv_title;
    private TextView tv_content;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_tutorial_detail;
    }

    @Override
    protected void initView()
    {
        tv_title = findView(R.id.tv_title);
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
        TurialBean bean = getIntent().getParcelableExtra(KEY);
        if (bean != null)
        {
            VerificationUtil.setViewValue(tv_title, bean.getTitle(), "秒吧教程");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            {
//                tv_content.setText(Html.fromHtml(bean.getContent(), Html.FROM_HTML_SEPARATOR_LINE_BREAK_PARAGRAPH | Html.FROM_HTML_SEPARATOR_LINE_BREAK_HEADING | Html.FROM_HTML_SEPARATOR_LINE_BREAK_LIST_ITEM | Html.FROM_HTML_SEPARATOR_LINE_BREAK_LIST | Html.FROM_HTML_SEPARATOR_LINE_BREAK_DIV | Html.FROM_HTML_SEPARATOR_LINE_BREAK_BLOCKQUOTE | Html.FROM_HTML_OPTION_USE_CSS_COLORS, new CustomImageGetter(tv_content), null));
                tv_content.setText(Html.fromHtml(bean.getContent(), Html.FROM_HTML_OPTION_USE_CSS_COLORS, new CustomImageGetter(tv_content), null));
            } else
            {
                tv_content.setText(Html.fromHtml(bean.getContent(), new CustomImageGetter(tv_content), null));
            }
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

    public static class CustomImageGetter implements Html.ImageGetter
    {

        private TextView textView;

        public CustomImageGetter(TextView textView)
        {
            this.textView = textView;
        }

        @Override
        public Drawable getDrawable(String source)
        {
            if (textView != null)
            {
                String url = "";
                if (!TextUtils.isEmpty(source))
                {
                    if (source.startsWith("http"))
                    {
                        url = source;
                    } else
                    {
                        url = Constants.ROOT_URL + source;
                    }
                }
                //级别列表Drawable
                final LevelListDrawable drawable = new LevelListDrawable();
                GlideApp.with(textView.getContext()).asBitmap().load(url).into(new SimpleTarget<Bitmap>()
                {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition)
                    {
                        try
                        {
                            if (resource != null)
                            {
                                BitmapDrawable bitmapDrawable = new BitmapDrawable(resource);
                                drawable.addLevel(1, 1, bitmapDrawable);
                                drawable.setBounds(0, 0, resource.getWidth(), resource.getHeight());
                                drawable.setLevel(1);
                                CharSequence text = textView.getText();
                                textView.setText(text);
                                textView.refreshDrawableState();
                            }
                        } catch (Exception e)
                        {
                            e.printStackTrace();
                        } catch (Error error)
                        {
                            error.printStackTrace();
                        }
                    }
                });

                return drawable;
            }
            return null;
        }
    }
}
