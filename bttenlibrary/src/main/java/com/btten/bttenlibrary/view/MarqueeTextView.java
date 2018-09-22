package com.btten.bttenlibrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 功能：让TextView一直获取焦点，从而实现在ListView等控件中的跑马灯效果
 */
public class MarqueeTextView extends TextView {

	/**
	 * @param context
	 */
	public MarqueeTextView(Context context) {
		super(context);
	}

	public MarqueeTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean isFocused() {
		return true;
	}
}
