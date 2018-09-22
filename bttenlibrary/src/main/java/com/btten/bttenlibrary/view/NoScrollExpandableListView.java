package com.btten.bttenlibrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * 功能：计算绝对高度的ExpandableListView。
 * 
 * 使用：与普通ExpandableListView使用方式一致，使用于ScrollView等可滑动控件中 注意：Adapter的缓存模式失效。
 * 
 * 建议： 不建议使用这种方式实现ExpandableListView的效果。由于嵌套结构复杂，导致计算高度时间开销较大。
 * 建议使用LinearLayout动态添加子控件的方式实现。
 * 
 * @author frj
 * 
 */
public class NoScrollExpandableListView extends ExpandableListView {

	public NoScrollExpandableListView(Context context) {
		super(context);
	}

	public NoScrollExpandableListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NoScrollExpandableListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * 重写测量的方法，此处将包括所有项的高度全部计算出来，用作ListView的高度O
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, mExpandSpec);
	}
}
