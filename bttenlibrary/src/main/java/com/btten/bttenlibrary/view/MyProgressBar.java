package com.btten.bttenlibrary.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**
 * 自定义ProgressBar 在进度条中间显示进度
 * 
 * @author frj
 * 
 */
public class MyProgressBar extends ProgressBar {

	private String text_progress = "0%";
	private Paint mPaint;// 画笔
	private float textSize = 12; // 文字大小
	private int mTextColor = Color.WHITE;

	public MyProgressBar(Context context) {
		super(context);
		initPaint();
	}

	public MyProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initPaint();
	}

	public MyProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initPaint();
	}

	/**
	 * 初始化画笔
	 */
	private void initPaint() {
		this.mPaint = new Paint();
		this.mPaint.setAntiAlias(true);
		this.mPaint.setColor(mTextColor);
		mPaint.setTextSize(textSize);
	}

	/**
	 * 设置文字大小
	 * 
	 * @param textSize
	 */
	public void setTextSize(float textSize) {
		this.textSize = textSize;
		initPaint();
		invalidate();
	}

	/**
	 * 设置显示百分比
	 * 
	 * @param progress
	 */
	public void setTextProgress(int progress) {
		int i = (int) ((progress * 1.0f / this.getMax()) * 100);
		this.text_progress = String.valueOf(i) + "%";
		invalidate();
	}

	/**
	 * 设置字体颜色
	 * 
	 * @param color
	 */
	public void setTextColor(int color) {
		this.mTextColor = color;
		initPaint();
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Rect rect = new Rect();
		this.mPaint.getTextBounds(this.text_progress, 0, this.text_progress.length(), rect);
		int x = (getWidth() / 2) - rect.centerX();
		int y = (getHeight() / 2) - rect.centerY();
		canvas.drawText(this.text_progress, x, y, this.mPaint);
	}

}
