/**
 * <Title:NoScrollGridView.java>
 * <Description:不能滑动的GridView 用于嵌套在ScrollView中>
 * <Company:>
 * <Author:Frj>
 * <Mender:2014-6-7>
 * <Version:1.0>
 */
package com.btten.bttenlibrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 功能：计算绝对高度的GridView。
 * 
 * 使用：与普通GridView使用方式一致，使用于ScrollView等可滑动控件中
 * 注意：Adapter的缓存模式失效。
 * 
 * @author frj
 * 
 */
public class NoScrollGridView extends GridView {

	public NoScrollGridView(Context context) {
		super(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public NoScrollGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NoScrollGridView(Context context, AttributeSet attrs, int delf) {
		super(context, attrs, delf);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, mExpandSpec);
	}

}
