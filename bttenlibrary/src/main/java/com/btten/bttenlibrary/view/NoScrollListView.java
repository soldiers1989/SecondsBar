package com.btten.bttenlibrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 功能：计算绝对高度的ListView。
 * 
 * 使用：与普通GridView使用方式一致，使用于ScrollView等可滑动控件中
 * 注意：Adapter的缓存模式失效。
 * 
 * @author frj
 * 
 */
public class NoScrollListView extends ListView {

	/*
	 * 实现三个构造方法 此处只用于初始化
	 */

	public NoScrollListView(Context context) {
		super(context);
	}

	public NoScrollListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NoScrollListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * 重写测量的方法，此处将包括所有项的高度全部计算出来，用作ListView的高度O
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, mExpandSpec);
	}
}
