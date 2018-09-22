package com.fx.secondbar.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.btten.bttenlibrary.base.adapter.BtAdapter;
import com.fx.secondbar.R;

/**
 * function:仿ListView的LinearLayout控件
 * author: frj
 * modify date: 2016/9/23
 */
public class CopyListView extends LinearLayout {
    private Context mContext;
    private BtAdapter mAdapter;
    //项点击事件
    private AdapterView.OnItemClickListener onItemClickListener;
    //分割线高度
    private int dividerHeight = 1;
    //分割线
    private Drawable divider;
    //默认透明的分割线
    private int DEFAULT_DIVIDER_COLOR = Color.TRANSPARENT;
    //默认分割线高度
    private int DEFAULT_DIVIDER_HEIGHT = 1;

    public CopyListView(Context context) {
        super(context);
        mContext = context;
    }

    public CopyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setCustomAttributes(attrs);
    }

    public CopyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        setCustomAttributes(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CopyListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        setCustomAttributes(attrs);
    }

    /**
     * 绑定自定义样式
     *
     * @param attrs
     */
    private void setCustomAttributes(AttributeSet attrs) {
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.copylistview);
        dividerHeight = a.getDimensionPixelSize(R.styleable.copylistview_cdividerHeight,
                DEFAULT_DIVIDER_HEIGHT);
        divider = a.getDrawable(R.styleable.copylistview_cdivider);
        a.recycle();
        setOrientation(VERTICAL);
    }

    /**
     * 设置数据源
     *
     * @param adapter
     */
    public void setAdapter(BtAdapter adapter) {
        if (adapter != null) {
            this.mAdapter = adapter;
            bindData();
        }
    }

    /**
     * 设置项点击事件
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 绑定数据
     */
    private void bindData() {
        removeAllViews();
        for (int i = 0; i < mAdapter.getCount(); i++) {
            View view = mAdapter.getView(i, null, this);
            view.setTag(i);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        int pos = (int) v.getTag();
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(null, v, pos, mAdapter.getItemId(pos));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            addView(view);
            if (i < mAdapter.getCount() - 1) {
                View dividerView = new View(mContext);
                dividerView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, dividerHeight));
                if (divider == null) {
                    dividerView.setBackgroundColor(DEFAULT_DIVIDER_COLOR);
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        dividerView.setBackground(divider);
                    } else {
                        dividerView.setBackgroundDrawable(divider);
                    }
                }
                addView(dividerView);
            }
        }
    }

    /**
     * 更新数据
     */
    public void notifyChangeDataSet() {
        if (mAdapter == null) {
            return;
        }
        bindData();
    }
}
