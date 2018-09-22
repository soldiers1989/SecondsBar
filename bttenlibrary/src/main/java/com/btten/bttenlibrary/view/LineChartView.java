package com.btten.bttenlibrary.view;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Path;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;

/**
 * 功能：折线图显示。
 * 
 * @author frj
 *
 */
public class LineChartView extends View implements OnGestureListener,
		OnDoubleTapListener {

	/**
	 * 使用： LineChartView line_view =
	 * (LineChartView)findViewById(R.id.line_view); ArrayList<LineItem> mXItem =
	 * new ArrayList<LineChartView.LineItem>(); LineItem item1 = new LineItem();
	 * item1.value = 1; mXItem.add(item1); LineItem item2 = new LineItem();
	 * item2.value = 2; mXItem.add(item2); LineItem item3 = new LineItem();
	 * item3.value = 3; mXItem.add(item3); LineItem item4 = new LineItem();
	 * item4.value = 4; mXItem.add(item4); line_view.setmXItem(mXItem);
	 * line_view.setmYItem(mXItem); ArrayList<Area> mArea = new
	 * ArrayList<LineChartView.Area>(); Area area1 = new Area(); area1.xValue =
	 * 1.2f; area1.yValue = 1.4f; mArea.add(area1); Area area2 = new Area();
	 * area2.xValue = 2.2f; area2.yValue = 2.0f; mArea.add(area2); Area area3 =
	 * new Area(); area3.xValue = 2.8f; area3.yValue = 3f; area3.isHighlight =
	 * true; mArea.add(area3); Area area4 = new Area(); area4.xValue = 3.5f;
	 * area4.yValue = 3f; mArea.add(area4);
	 * 
	 * ArrayList<Area> mArea1 = new ArrayList<LineChartView.Area>(); Area
	 * area1_1 = new Area(); area1_1.xValue = 1.4f; area1_1.yValue = 1.2f;
	 * mArea1.add(area1_1); Area area2_1 = new Area(); area2_1.xValue = 2.0f;
	 * area2_1.yValue = 2.2f; mArea1.add(area2_1); Area area3_1 = new Area();
	 * area3_1.xValue = 3f; area3_1.yValue = 2.8f; area3_1.isHighlight = true;
	 * mArea1.add(area3_1); Area area4_1 = new Area(); area4_1.xValue = 3f;
	 * area4_1.yValue = 3.5f; mArea1.add(area4_1);
	 * 
	 * line_view.setmIsShowLegalArea(true); line_view.setShowTipsArea(true);
	 * int[] mPositions = { 2, 3 }; line_view.setmPositions(mPositions);
	 * 
	 * ArrayList<AllLineBean> list = new ArrayList<LineChartView.AllLineBean>();
	 * AllLineBean bean = new AllLineBean(); bean.items = mArea; bean.lineColor
	 * = Color.parseColor("#349D0C"); bean.lineName = "干啥子"; list.add(bean);
	 * AllLineBean bean1 = new AllLineBean(); bean1.items = mArea1;
	 * bean1.lineColor = Color.parseColor("#09ADE6"); bean1.lineName = "哟哟切克闹e";
	 * list.add(bean1); line_view.setmList(list);
	 * line_view.setmTipsGravity(TipsGravity.LEFT); line_view.setmListener(new
	 * OnKneeClickListener() {
	 * 
	 * @Override public void onKneeClick(int groupPosition, int childPosition) {
	 *           Log.e("position", "groupPosition=" + groupPosition +
	 *           ",childPosition=" + childPosition); } });
	 */

	/**
	 * 合法区域（是否在折线图上展示一个合法的区域范围）
	 */
	private boolean mIsShowLegalArea = false;

	/**
	 * 合法区域显示颜色
	 */
	// private int[] mLegalAreaColor = { Color.parseColor("#E2EAFF") };
	private int[] mLegalAreaColor = { Color.parseColor("#B198EC") };

	/**
	 * X 轴线颜色
	 */
	private int mXLineColor = Color.parseColor("#BDBDBD");

	/**
	 * Y轴线颜色
	 */
	private int mYLineColor = Color.parseColor("#BDBDBD");

	/**
	 * 折线颜色
	 */
	private int mLineColor = Color.parseColor("#09ADE6");

	/**
	 * 高亮的点的颜色
	 */
	private int mHighLightColor = Color.parseColor("#FD5248");

	/**
	 * 提示区域的高度
	 */
	private int mTipsAreaHeight = 40;

	/**
	 * 表示是否显示提示区域
	 */
	private boolean isShowTipsArea = false;

	/**
	 * 提示区域每个提示项的间隔大小
	 */
	private int mTipsIntervalWidth = 10;
	/**
	 * 提示区域提示文字的文字大小
	 */
	private int mTipsTextSize = 16;

	/**
	 * 提示显示方案 默认从左往右显示
	 */
	private TipsGravity mTipsGravity = TipsGravity.LEFT;

	/**
	 * 提示部分的显示模式，从什么地方开始显示
	 * 
	 * @author frj
	 * 
	 */
	public static enum TipsGravity {
		CENTER, LEFT, RIGHT
	}

	/**
	 * 文字大小 单位：px
	 */
	private int mTextSize = 20;

	/**
	 * 文字颜色
	 */
	private int mTextColor = Color.parseColor("#000000");

	/**
	 * 折点区域的半径 单位：px
	 */
	private int mAreaRide = 4;

	/**
	 * 线宽
	 */
	private int mLineWidth = 2;

	/**
	 * 宽高
	 */
	private int mWidth = 0;
	private int mHeight = 0;

	private ArrayList<LineItem> mYItem; // Y轴显示的项
	private ArrayList<LineItem> mXItem;// X轴显示的项

	private int mViewOutsideWidth = 0; // 其它控件占据的屏幕宽度和

	/**
	 * 折线集合（可画多条折线）
	 */
	private ArrayList<AllLineBean> mList;

	/**
	 * 合法区域范围 默认为0和1
	 */
	private float[][] mPositions = { { 0, 1 } };

	private GestureDetector mGestureDetector; // 手势监听
	private OnKneeClickListener mListener; // 折点点击监听

	public LineChartView(Context context) {
		super(context);
		mGestureDetector = new GestureDetector(getContext(), this);
		mGestureDetector.setOnDoubleTapListener(this);
	}

	public LineChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mGestureDetector = new GestureDetector(getContext(), this);
		mGestureDetector.setOnDoubleTapListener(this);
	}

	public LineChartView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mGestureDetector = new GestureDetector(getContext(), this);
		mGestureDetector.setOnDoubleTapListener(this);
	}

	/**
	 * 获取当前是否显示合法区域
	 * 
	 * @return true表示显示 false表示不显示 默认值为false
	 */
	public boolean ismIsShowLegalArea() {
		return mIsShowLegalArea;
	}

	/**
	 * 设置是否显示合法区域
	 * 
	 * @param mIsShowLegalArea
	 *            true表示显示 false表示不显示 默认值为false
	 */
	public void setShowLegalArea(boolean mIsShowLegalArea) {
		this.mIsShowLegalArea = mIsShowLegalArea;
	}

	/**
	 * 获取Y轴显示项
	 * 
	 * @return
	 */
	public ArrayList<LineItem> getYItem() {
		return mYItem;
	}

	/**
	 * 设置Y轴显示项
	 * 
	 * @param mYItem
	 */
	public void setYItem(ArrayList<LineItem> mYItem) {
		this.mYItem = mYItem;
		invalidate();
	}

	/**
	 * 获取X轴显示项
	 * 
	 * @return
	 */
	public ArrayList<LineItem> getXItem() {
		return mXItem;
	}

	/**
	 * 设置X轴显示项
	 * 
	 * @param mXItem
	 */
	public void setXItem(ArrayList<LineItem> mXItem) {
		this.mXItem = mXItem;
		invalidate();
	}

	/**
	 * 获取合法区域范围
	 * 
	 * @return
	 */
	public float[][] getPositions() {
		return mPositions;
	}

	/**
	 * 设置合法区域范围
	 * 
	 * @param mPositions
	 *            第一个值为下方点的值 第二个值为上方点的值 ；此值与Y轴的项相关
	 */
	public void setPositions(float[][] mPositions) {
		this.mPositions = mPositions;
	}

	/**
	 * 设置合法区域颜色
	 * 
	 * @param color
	 */
	public void setLegalAreaColor(int[] color) {
		mLegalAreaColor = color;
	}

	/**
	 * 获取合法区域颜色
	 * 
	 */
	public int[] getLegalAreaColor() {
		return mLegalAreaColor;
	}

	public int getTextSize() {
		return mTextSize;
	}

	/**
	 * 设置文字大小（主要是X、Y轴的项的文字）
	 * 
	 * @param mTextSize
	 */
	public void setTextSize(int mTextSize) {
		this.mTextSize = mTextSize;
	}

	/**
	 * 设置文字颜色
	 * 
	 * @param color
	 */
	public void setTextColor(int color) {
		this.mTextColor = color;
	}

	public int getAreaRide() {
		return mAreaRide;
	}

	/**
	 * 设置折点的半径（实际半径为折点的半径+折线的宽度）
	 * 
	 * @param mAreaRide
	 */
	public void setAreaRide(int mAreaRide) {
		this.mAreaRide = mAreaRide;
	}

	public int getLineWidth() {
		return mLineWidth;
	}

	/**
	 * 设置折线的宽度
	 * 
	 * @param mLineWidth
	 */
	public void setLineWidth(int mLineWidth) {
		this.mLineWidth = mLineWidth;
	}

	/**
	 * 设置折点的点击监听事件
	 * 
	 * @param mListener
	 */
	public void setOnKneeClickListener(OnKneeClickListener mListener) {
		this.mListener = mListener;
	}

	/**
	 * 获取提示区域高度
	 * 
	 * @return
	 */
	public int getTipsAreaHeight() {
		return mTipsAreaHeight;
	}

	/**
	 * 设置提示区域高度
	 * 
	 * @param mTipsAreaHeight
	 */
	public void setTipsAreaHeight(int mTipsAreaHeight) {
		this.mTipsAreaHeight = mTipsAreaHeight;
	}

	/**
	 * 表示是否显示提示区域
	 * 
	 * @return
	 */
	public boolean isShowTipsArea() {
		return isShowTipsArea;
	}

	/**
	 * 设置是否显示提示区域
	 * 
	 * @param isShowTipsArea
	 */
	public void setShowTipsArea(boolean isShowTipsArea) {
		this.isShowTipsArea = isShowTipsArea;
	}

	/**
	 * 获取折线数据
	 * 
	 * @return
	 */
	public ArrayList<AllLineBean> getmList() {
		return mList;
	}

	/**
	 * 设置要展示的折线
	 * 
	 * @param mList
	 */
	public void setmList(ArrayList<AllLineBean> mList) {
		this.mList = mList;
	}

	/**
	 * 设置提示区域 每个提示之间的间隔宽度
	 * 
	 * @param mTipsIntervalWidth
	 */
	public void setTipsIntervalWidth(int mTipsIntervalWidth) {
		this.mTipsIntervalWidth = mTipsIntervalWidth;
	}

	/**
	 * 提示区域每个提示项的间隔大小
	 * 
	 * @return
	 */
	public int getTipsIntervalWidth() {
		return this.mTipsIntervalWidth;
	}

	/**
	 * 设置提示文字的字体大小（提示区域）
	 * 
	 * @param mTipsTextSize
	 */
	public void setTipsTextSize(int mTipsTextSize) {
		this.mTipsTextSize = mTipsTextSize;
	}

	/**
	 * 提示区域提示文字的文字大小
	 * 
	 * @return
	 */
	public int getTipsTextSize() {
		return this.mTipsTextSize;
	}

	/**
	 * 设置提示部分显示方案 默认从左往右显示
	 * 
	 * @param mGravity
	 */
	public void setTipsGravity(TipsGravity mGravity) {
		if (mGravity == null) {
			this.mTipsGravity = TipsGravity.LEFT;
		} else {
			this.mTipsGravity = mGravity;
		}
	}

	/**
	 * 获取控件外其它控件占据的屏幕宽度总和
	 * 
	 * @return
	 */
	public int getViewOutsideWidth() {
		return mViewOutsideWidth;
	}

	/**
	 * 设置控件外其它控件占据的屏幕宽度总和
	 * 
	 * @param ViewOutsideWidth
	 */
	public void setViewOutsideWidth(int ViewOutsideWidth) {
		this.mViewOutsideWidth = ViewOutsideWidth;
	}

	/**
	 * 设置完各种参数后 需要调用此方法才有效
	 */
	public void draw() {
		requestLayout();
		invalidate();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		/**
		 * 设置宽度
		 */
		int width_specMode = MeasureSpec.getMode(widthMeasureSpec);
		int width_specSize = MeasureSpec.getSize(widthMeasureSpec);
		if (width_specMode == MeasureSpec.EXACTLY)// match_parent , accurate
		{
			mWidth = width_specSize;
			mWidth = mWidth - getPaddingLeft() - getPaddingRight();
			Log.e("linechartView", "EXACTLY");
		} else {
			// DisplayMetrics displayMetrics = getContext().getResources()
			// .getDisplayMetrics();
			// // 根据X轴项数计算宽度
			// if (mXItem != null && mXItem.size() > 0) {
			// if ((mXItem.size() - 1) * 4 * mTextSize <
			// displayMetrics.widthPixels
			// - getPaddingLeft() - getPaddingRight()) {
			// mWidth = displayMetrics.widthPixels - getPaddingLeft()
			// - getPaddingRight();
			// Log.e("linechartView", "DisplayMetrics");
			// } else {
			// mWidth = (mXItem.size() - 1) * 4 * mTextSize
			// + getPaddingLeft() + getPaddingRight();
			// Log.e("linechartView", "width is normal");
			// }
			// } else {
			// mWidth = displayMetrics.widthPixels - getPaddingLeft()
			// - getPaddingRight();
			// Log.e("linechartView", "mXItem is null");
			// }
			DisplayMetrics displayMetrics = getContext().getResources()
					.getDisplayMetrics();
			mWidth = displayMetrics.widthPixels - getPaddingLeft()
					- getPaddingRight() - mViewOutsideWidth;
		}
		// 根据X轴项数计算宽度
		if (mXItem != null && mXItem.size() > 0) {
			if ((mXItem.size() - 1) * 4 * mTextSize >= mWidth) {
				mWidth = (mXItem.size() - 1) * 4 * mTextSize + getPaddingLeft()
						+ getPaddingRight();
			}
		}
		Log.e("linechartView", "width=" + mWidth);
		/**
		 * 设置高度
		 */
		int height_specMode = MeasureSpec.getMode(heightMeasureSpec);
		int height_specSize = MeasureSpec.getSize(heightMeasureSpec);
		if (height_specMode == MeasureSpec.EXACTLY)// match_parent , accurate
		{
			mHeight = height_specSize;
		} else {
			DisplayMetrics displayMetrics = getContext().getResources()
					.getDisplayMetrics();
			mHeight = displayMetrics.heightPixels - getPaddingTop()
					- getPaddingBottom();
		}
		setMeasuredDimension(mWidth, mHeight);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		Log.e("measure", "getMeasureWidth=" + getMeasuredWidth());
		super.onDraw(canvas);
		/*
		 * 首先判断项是否为空
		 */
		if (mYItem == null || mXItem == null) {
			return;
		}
		if (mXItem.size() == 0 || mYItem.size() == 0) {
			return;
		}
		/*
		 * 控件的矩形范围 单行最多显示4个字符，其中一个字符为间隔宽 最多显示4行，其中一个字符为间隔宽
		 */
		// Rect rect = new Rect(getPaddingLeft() + mTextSize * 3,
		// isShowTipsArea ? (getPaddingTop() + mTipsAreaHeight)
		// : getPaddingTop(), mWidth - getPaddingRight()
		// - mTextSize * 3, mHeight - getPaddingBottom()
		// - mTextSize * 2);

		/*
		 * 根据X轴最后一项的文字长度，设置总体宽度（少空白），当最后一项文字长度小于预定义宽度时，使用文字长度作为最后一项宽度。
		 */
		Paint temp = new Paint();
		temp.setTextSize(mTextSize);
		float width = mWidth - getPaddingRight() - mTextSize * 3;
		if (mXItem.size() > 0) {
			if (temp.measureText(mXItem.get(mXItem.size() - 1).itemName) < mTextSize * 3) {
				width = mWidth
						- getPaddingRight()
						- temp.measureText(mXItem.get(mXItem.size() - 1).itemName);
			}
		}

		Rect rect = new Rect(getPaddingLeft() + mTextSize * 3,
				isShowTipsArea ? (getPaddingTop() + mTipsAreaHeight)
						: getPaddingTop(), (int) width, mHeight
						- getPaddingBottom() - mTextSize * 2);
		drawTips(canvas);
		drawLine(canvas, rect);
		try {
			drawLegalArea(canvas, rect);
		} catch (Exception e) {
			e.printStackTrace();
		}
		drawPathAndPoint(canvas, rect);
	}

	/**
	 * 画X轴、Y轴所有的线
	 * 
	 * @param canvas
	 */
	private void drawLine(Canvas canvas, Rect rect) {

		/*
		 * 画X轴Y轴
		 */
		Paint x_paint = new Paint();
		x_paint.setColor(mXLineColor);
		x_paint.setAntiAlias(true); // 抗锯齿
		x_paint.setStyle(Paint.Style.STROKE);
		x_paint.setDither(true); // 使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
		x_paint.setStrokeWidth(mLineWidth);

		Paint y_paint = new Paint();
		y_paint.setColor(mYLineColor);
		y_paint.setAntiAlias(true); // 抗锯齿
		y_paint.setStyle(Paint.Style.STROKE);
		y_paint.setDither(true); // 使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
		y_paint.setStrokeWidth(mLineWidth);
		y_paint.setTextSize(mTextSize);

		Paint x_text_paint = new Paint();
		x_text_paint.setColor(mTextColor);
		x_text_paint.setAntiAlias(true); // 抗锯齿
		x_text_paint.setDither(true); // 使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
		x_text_paint.setTextSize(mTextSize);
		x_text_paint.setTextAlign(Align.RIGHT);

		Paint y_text_paint = new Paint();
		y_text_paint.setColor(mTextColor);
		y_text_paint.setAntiAlias(true); // 抗锯齿
		y_text_paint.setDither(true); // 使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
		y_text_paint.setTextSize(mTextSize);

		/*
		 * 画X轴与Y轴的项区域
		 */
		int yItemHeight = 0;
		if (mYItem.size() == 1) {
			yItemHeight = rect.height();
		} else if (mYItem.size() > 1) {
			yItemHeight = rect.height() / (mYItem.size() - 1);
		} else {
			return;
		}
		int xItemHeigth = 0;
		if (mXItem.size() == 1) {
			xItemHeigth = rect.width();
		} else if (mXItem.size() > 1) {
			xItemHeigth = rect.width() / (mXItem.size() - 1);
		} else {
			return;
		}
		// 画Y轴的所有线
		for (int i = 0; i < mXItem.size(); i++) {
			canvas.drawLine(rect.left + xItemHeigth * i, rect.bottom, rect.left
					+ xItemHeigth * i, rect.top, y_paint);
			if (TextUtils.isEmpty(mXItem.get(i).itemName)) {
				mXItem.get(i).itemName = String.valueOf(mXItem.get(i).value);
			}
			// canvas.drawText(mXItem.get(i).itemName, rect.left + xItemHeigth *
			// i
			// - mTextSize / 2, rect.bottom + mTextSize + mTextSize / 2,
			// y_text_paint);
			/*
			 * 判断文字长度是否超过了左侧提示区域的宽度，如果超出，则缩小字体显示。
			 */
			if (mTextSize * 3 >= y_text_paint
					.measureText(mXItem.get(i).itemName)) {
				canvas.drawText(mXItem.get(i).itemName, rect.left + xItemHeigth
						* i - mTextSize / 2, rect.bottom + mTextSize
						+ mTextSize / 2, y_text_paint);
			} else {
				/*
				 * 循环的减少textSize的大小，直到左侧宽度能够完全承载文字长度
				 */
				Paint temp = y_text_paint;
				int textSize = mTextSize;
				do {
					textSize = textSize - 2;
					temp.setTextSize(textSize);
				} while (mTextSize * 3 < temp
						.measureText(mXItem.get(i).itemName));
				canvas.drawText(mXItem.get(i).itemName, rect.left + xItemHeigth
						* i - mTextSize / 2, rect.bottom + mTextSize
						+ mTextSize / 2, temp);
			}
		}
		// 画X轴的所有线
		for (int i = 0; i < mYItem.size(); i++) {
			canvas.drawLine(rect.left, rect.bottom - yItemHeight * i,
					rect.right, rect.bottom - yItemHeight * i, x_paint);
			if (TextUtils.isEmpty(mYItem.get(i).itemName)) {
				mYItem.get(i).itemName = String.valueOf(mYItem.get(i).value);
			}
			// canvas.drawText(mYItem.get(i).itemName, rect.left
			// - mYItem.get(i).itemName.getBytes().length * mTextSize / 2,
			// rect.bottom - yItemHeight * i + mTextSize / 2, x_paint);
			/*
			 * 判断文字长度是否超过了左侧提示区域的宽度，如果超出，则缩小字体显示。
			 */
			if ((mTextSize * 3 - mTextSize / 2) >= x_text_paint
					.measureText(mYItem.get(i).itemName)) {
				canvas.drawText(mYItem.get(i).itemName, rect.left - mTextSize
						/ 2, rect.bottom - yItemHeight * i + mTextSize / 2,
						x_text_paint);
			} else {
				/*
				 * 循环的减少textSize的大小，直到左侧宽度能够完全承载文字长度
				 */

				int textSize = mTextSize;
				int measureTextWidth = 0;
				do {
					textSize = textSize - 2;
					TextPaint FontPaint = new TextPaint();
					FontPaint.setTextSize(textSize);
					measureTextWidth = (int) FontPaint.measureText(mYItem
							.get(i).itemName);
				} while ((mTextSize * 3 - mTextSize / 2) < measureTextWidth);
				Paint temp = x_text_paint;
				temp.setTextSize(textSize);
				canvas.drawText(mYItem.get(i).itemName, rect.left - mTextSize
						/ 2, rect.bottom - yItemHeight * i + mTextSize / 2,
						temp);
			}

		}
	}

	/**
	 * 画合法区域
	 */
	private void drawLegalArea(Canvas canvas, Rect rect) throws Exception {
		/*
		 * 画合法区域
		 */
		if (mIsShowLegalArea) {
			/*
			 * 判断合法区域范围是否合法(如果两个值中的一个或者两个都超出了Y轴的范围，那么视为不合法)
			 */
			if (mPositions != null) {
				Paint legalArea_paint = new Paint();
				legalArea_paint.setAntiAlias(true); // 抗锯齿
				legalArea_paint.setStyle(Paint.Style.FILL);
				legalArea_paint.setDither(true); // 使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
				for (int i = 0; i < mPositions.length; i++) {
					if (mLegalAreaColor.length == mPositions.length) {
						legalArea_paint.setColor(mLegalAreaColor[i]);
					} else {
						if (i < mLegalAreaColor.length) {
							legalArea_paint.setColor(mLegalAreaColor[i]);
						} else {
							legalArea_paint.setColor(mLegalAreaColor[0]);
						}
					}
					legalArea_paint.setAlpha(100);
					float top = countSectionByY(mPositions[i][0], mYItem, rect);
					float bottom = countSectionByY(mPositions[i][1], mYItem,
							rect);
					canvas.drawRect(rect.left + mLineWidth,
							(top > bottom) ? (top - mLineWidth)
									: (top + mLineWidth), rect.right
									- mLineWidth,
							(top > bottom) ? (bottom + mLineWidth)
									: (bottom - mLineWidth), legalArea_paint);
				}
			}
		}
	}

	/**
	 * 画折线与折点
	 * 
	 * @param canvas
	 * @param rect
	 */
	private void drawPathAndPoint(Canvas canvas, Rect rect) {
		if (mList != null) {
			/*
			 * 如果有合法区域，那么最后一项为不合法的表示
			 */
			int length = mList.size();
			// if (mIsShowLegalArea) {
			// length = mList.size() - 1;
			// }
			// if (length < 0) {
			// return;
			// }
			for (int i = 0; i < length; i++) {
				if (mList.get(i).lineColor == 0) {
					mList.get(i).lineColor = mLineColor;
				}
				if (mList.get(i).items != null) {
					Paint area_paint = new Paint();
					area_paint.setAntiAlias(true); // 抗锯齿
					area_paint.setStyle(Paint.Style.FILL);
					area_paint.setDither(true); // 使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
					area_paint.setColor(Color.WHITE);

					Paint area_cover_paint = new Paint();
					area_cover_paint.setAntiAlias(true); // 抗锯齿
					area_cover_paint.setStyle(Paint.Style.STROKE);
					area_cover_paint.setDither(true); // 使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
					area_cover_paint.setStrokeWidth(mLineWidth);

					Paint path_paint = new Paint();
					path_paint.setAntiAlias(true); // 抗锯齿
					path_paint.setStyle(Paint.Style.STROKE);
					path_paint.setDither(true); // 使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
					path_paint.setStrokeWidth(mLineWidth);
					path_paint.setColor(mList.get(i).lineColor);
					for (int j = 0; j < mList.get(i).items.size(); j++) {
						float center_x = countSectionByX(
								mList.get(i).items.get(j).xValue, mXItem, rect);
						float center_y = countSectionByY(
								mList.get(i).items.get(j).yValue, mYItem, rect);
						Path path = new Path();
						if (j < mList.get(i).items.size() - 1) {
							path.moveTo(center_x, center_y);
							float next_x = countSectionByX(
									mList.get(i).items.get(j + 1).xValue,
									mXItem, rect);
							float next_y = countSectionByY(
									mList.get(i).items.get(j + 1).yValue,
									mYItem, rect);
							path.lineTo(next_x, next_y);
							canvas.drawPath(path, path_paint);
						}
						/**
						 * 由于点击区域过小，现在增大一个线宽。从2个线宽变成4个线宽
						 */
						// Rect ride = new Rect((int) Math.floor(center_x
						// - mAreaRide - mLineWidth),
						// (int) Math.floor(center_y - mAreaRide
						// - mLineWidth), (int) Math.ceil(center_x
						// + mAreaRide + mLineWidth),
						// (int) Math.ceil(center_y + mAreaRide
						// + mLineWidth));
						Rect ride = new Rect((int) Math.floor(center_x
								- mAreaRide - 2 * mLineWidth),
								(int) Math.floor(center_y - mAreaRide - 2
										* mLineWidth), (int) Math.ceil(center_x
										+ mAreaRide + 2 * mLineWidth),
								(int) Math.ceil(center_y + mAreaRide + 2
										* mLineWidth));
						mList.get(i).items.get(j).rect = ride;
						if (mList.get(i).items.get(j).isHighlight) {
							area_cover_paint.setColor(mHighLightColor);
						} else {
							area_cover_paint.setColor(mList.get(i).lineColor);
						}
						canvas.drawCircle(center_x, center_y, mAreaRide
								+ mLineWidth, area_paint);
						canvas.drawCircle(center_x, center_y, mAreaRide
								+ mLineWidth, area_cover_paint);
					}
				}
			}
		}
	}

	/**
	 * 画提示信息（指示折线表示的意义）
	 * 
	 * @param canvas
	 */
	private void drawTips(Canvas canvas) {
		if (isShowTipsArea) {
			if (mList != null) {
				if (mTipsGravity == TipsGravity.LEFT) {
					int startPosition_X = getPaddingLeft() + mTipsIntervalWidth
							+ mTextSize * 2; // x轴起始位置
					int startPosition_Y = getPaddingTop(); // Y轴起始位置
					for (int i = 0; i < mList.size(); i++) {
						if (mList.get(i).lineColor == 0) {
							mList.get(i).lineColor = mLineColor;
						}
						if (TextUtils.isEmpty(mList.get(i).lineName)) {
							mList.get(i).lineName = "折线" + (i + 1);
						}
						Rect rect = new Rect(startPosition_X
								+ mTipsIntervalWidth, startPosition_Y
								+ mTipsAreaHeight / 2 - mTipsIntervalWidth / 2,
								startPosition_X + mTipsIntervalWidth
										+ mTipsIntervalWidth, startPosition_Y
										+ mTipsAreaHeight / 2
										+ mTipsIntervalWidth / 2);
						Paint paint = new Paint();
						paint.setAntiAlias(true); // 抗锯齿
						paint.setStyle(Paint.Style.FILL);
						paint.setDither(true); // 使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
						paint.setColor(mList.get(i).lineColor);
						paint.setTextSize(mTipsTextSize);
						/*
						 * 如果显示合法区域，那么最后一项则为不合法提示，对不合法提示画圆表示
						 */
						if (i == mList.size() - 1) {
							if (mIsShowLegalArea) {
								canvas.drawCircle((rect.right - rect.left) / 2
										+ rect.left, (rect.bottom - rect.top)
										/ 2 + rect.top, rect.width() / 2, paint);
							} else {
								canvas.drawRect(rect, paint);
							}
						} else {
							canvas.drawRect(rect, paint);
						}

						canvas.drawText(mList.get(i).lineName, rect.right
								+ mTipsIntervalWidth, rect.bottom, paint);
						// startPosition_X = startPosition_X + rect.width()
						// + mList.get(i).lineName.getBytes().length
						// * mTipsTextSize / 2 + mTipsIntervalWidth;

						/*
						 * 获取字符串在画布上的长度
						 * paint.measureText(mList.get(i).lineName);
						 */
						float textWidth = paint
								.measureText(mList.get(i).lineName);
						// 向上取整
						startPosition_X = (int) Math
								.ceil((startPosition_X + rect.width()
										+ textWidth + mTipsIntervalWidth + mTipsIntervalWidth));
					}
				} else if (mTipsGravity == TipsGravity.RIGHT) {
					int startPosition_X = 0;
					startPosition_X = mWidth - getPaddingRight();
					int startPosition_Y = getPaddingTop(); // Y轴起始位置
					for (int i = 0; i < mList.size(); i++) {
						if (mList.get(i).lineColor == 0) {
							mList.get(i).lineColor = mLineColor;
						}
						if (TextUtils.isEmpty(mList.get(i).lineName)) {
							mList.get(i).lineName = "折线" + (i + 1);
						}
						Paint paint = new Paint();
						paint.setAntiAlias(true); // 抗锯齿
						paint.setStyle(Paint.Style.FILL);
						paint.setDither(true); // 使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
						paint.setColor(mList.get(i).lineColor);
						paint.setTextSize(mTipsTextSize);
						paint.setTextAlign(Align.RIGHT);
						canvas.drawText(mList.get(i).lineName, startPosition_X
								- mTipsIntervalWidth, startPosition_Y
								+ mTipsAreaHeight / 2 + mTipsIntervalWidth / 2,
								paint);
						int textWidth = (int) Math.ceil(paint.measureText(mList
								.get(i).lineName));
						Log.e("measureTextWidth", mList.get(i).lineName + "="
								+ textWidth);
						Rect rect = new Rect(startPosition_X
								- mTipsIntervalWidth - textWidth
								- mTipsIntervalWidth - mTipsIntervalWidth,
								startPosition_Y + mTipsAreaHeight / 2
										- mTipsIntervalWidth / 2,
								startPosition_X - mTipsIntervalWidth
										- textWidth - mTipsIntervalWidth,
								startPosition_Y + mTipsAreaHeight / 2
										+ mTipsIntervalWidth / 2);

						/*
						 * 如果显示合法区域，那么最后一项则为不合法提示，对不合法提示画圆表示
						 */
						if (i == mList.size() - 1) {
							if (mIsShowLegalArea) {
								canvas.drawCircle((rect.right - rect.left) / 2
										+ rect.left, (rect.bottom - rect.top)
										/ 2 + rect.top, rect.width() / 2, paint);
							} else {
								canvas.drawRect(rect, paint);
							}
						} else {
							canvas.drawRect(rect, paint);
						}
						startPosition_X = startPosition_X - mTipsIntervalWidth
								- textWidth - mTipsIntervalWidth - rect.width();
					}
				} else if (mTipsGravity == TipsGravity.CENTER) {
					int startPosition_X = 0;
					int startPosition_Y = getPaddingTop(); // Y轴起始位置
					int tipsContentWidth = 0;
					// 每项的间隔宽度
					tipsContentWidth = 0;
					for (int i = 0; i < mList.size(); i++) {
						Paint paint_temp = new Paint();
						paint_temp.setTextSize(mTipsTextSize);
						int textWidth = (int) Math.ceil(paint_temp
								.measureText(mList.get(i).lineName));
						Log.e("measureTextWidth", mList.get(i).lineName + "="
								+ textWidth);
						/*
						 * 提示文字宽度+提示图案宽度+间隔宽度
						 */
						// tipsContentWidth = tipsContentWidth
						// + mList.get(i).lineName.getBytes().length
						// * mTipsTextSize / 2 + mTipsIntervalWidth
						// + mTipsIntervalWidth;
						/*
						 * 内容宽度=上一项内容宽度+图标与左侧间隔宽度+图标宽度+图标与文字间隔宽度+文字宽度
						 */
						tipsContentWidth = tipsContentWidth
								+ mTipsIntervalWidth + mTipsIntervalWidth
								+ mTipsIntervalWidth + textWidth;
					}
					startPosition_X = (mWidth - tipsContentWidth) / 2;
					for (int i = 0; i < mList.size(); i++) {
						if (mList.get(i).lineColor == 0) {
							mList.get(i).lineColor = mLineColor;
						}
						if (TextUtils.isEmpty(mList.get(i).lineName)) {
							mList.get(i).lineName = "折线" + (i + 1);
						}
						Rect rect = new Rect(startPosition_X
								+ mTipsIntervalWidth, startPosition_Y
								+ mTipsAreaHeight / 2 - mTipsIntervalWidth / 2,
								startPosition_X + mTipsIntervalWidth
										+ mTipsIntervalWidth, startPosition_Y
										+ mTipsAreaHeight / 2
										+ mTipsIntervalWidth / 2);
						Paint paint = new Paint();
						paint.setAntiAlias(true); // 抗锯齿
						paint.setStyle(Paint.Style.FILL);
						paint.setDither(true); // 使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
						paint.setColor(mList.get(i).lineColor);
						paint.setTextSize(mTipsTextSize);
						/*
						 * 如果显示合法区域，那么最后一项则为不合法提示，对不合法提示画圆表示
						 */
						if (i == mList.size() - 1) {
							if (mIsShowLegalArea) {
								canvas.drawCircle((rect.right - rect.left) / 2
										+ rect.left, (rect.bottom - rect.top)
										/ 2 + rect.top, rect.width() / 2, paint);
							} else {
								canvas.drawRect(rect, paint);
							}
						} else {
							canvas.drawRect(rect, paint);
						}
						canvas.drawText(mList.get(i).lineName, rect.right
								+ mTipsIntervalWidth, rect.bottom, paint);

						int textWidth = (int) Math.ceil(paint.measureText(mList
								.get(i).lineName));
						startPosition_X = startPosition_X + rect.width()
								+ textWidth + mTipsIntervalWidth
								+ mTipsIntervalWidth;
					}
				}
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mGestureDetector.onTouchEvent(event);
		// 拦截点击事件
		return true;
	}

	/**
	 * 计算折点的值在Y轴上的区间范围
	 * 
	 * @param value
	 *            折点值
	 * @return 折点在Y轴显示位置
	 */
	private float countSectionByY(float value, ArrayList<LineItem> items,
			Rect rect) {
		if (value < items.get(0).value) {
			return rect.bottom;
		}
		if (value > items.get(items.size() - 1).value) {
			return rect.top;
		}
		float yHeightItem = 0;
		if (items.size() == 0) {
			return rect.bottom;
		} else if (items.size() > 1) {
			yHeightItem = rect.height() / (items.size() - 1);
		} else {
			yHeightItem = rect.height();
		}
		for (int i = 0; i < items.size(); i++) {
			if (i < items.size() - 1) {
				if (value == items.get(i).value) {
					return rect.bottom - yHeightItem * i;
				} else if (value > items.get(i).value
						&& value < items.get(i + 1).value) {
					return rect.bottom
							- yHeightItem
							* i
							- (yHeightItem * (value - items.get(i).value) / (items
									.get(i + 1).value - (items.get(i).value)));
				} else {
					continue;
				}
			} else {
				/*
				 * 不管大于小于都只显示到这里
				 */
				return rect.bottom - yHeightItem * i;
			}
		}
		return rect.top;
	}

	/**
	 * 计算折点的值在X轴上的区间范围
	 * 
	 * @param value
	 *            折点值
	 * @return 折点在X轴显示位置
	 */
	private float countSectionByX(float value, ArrayList<LineItem> items,
			Rect rect) {
		if (value < items.get(0).value) {
			return rect.left;
		}
		if (value > items.get(items.size() - 1).value) {
			return rect.right;
		}
		float xWidthItem = 0;
		if (items.size() == 0) {
			return rect.left;
		} else if (items.size() > 1) {
			xWidthItem = rect.width() / (items.size() - 1);
		} else {
			xWidthItem = rect.width();
		}
		for (int i = 0; i < items.size(); i++) {
			if (i < items.size() - 1) {
				if (value == items.get(i).value) {
					return rect.left + xWidthItem * i;
				} else if (value > items.get(i).value
						&& value < items.get(i + 1).value) {
					return rect.left
							+ xWidthItem
							* i
							+ (xWidthItem * (value - items.get(i).value) / (items
									.get(i + 1).value - items.get(i).value));
				} else {
					continue;
				}
			} else {
				/*
				 * 不管大于小于都只显示到这里
				 */
				return rect.left + xWidthItem * i;
			}
		}
		return rect.left;
	}

	/**
	 * 折线实体
	 * 
	 * @author frj
	 * 
	 */
	public static class AllLineBean {
		public int lineColor; // 折线颜色
		public String lineName; // 折线名称
		public ArrayList<Area> items; // 折线
	}

	/**
	 * Y轴项
	 * 
	 * @author frj
	 * 
	 */
	public static class LineItem {
		public float value; // 项值
		public String itemName; // 项名 当项名未设置时，使用项值
		public String unit; // 单位
	}

	/**
	 * 点的范围
	 */
	public static class Area {
		public float xValue; // X轴值
		public float yValue; // Y轴值
		public Rect rect; // 范围
		public boolean isHighlight = false; // 是否高亮显示 默认否
	}

	/**
	 * 合法区域
	 * 
	 * @author frj
	 * 
	 */
	public static class LegalAreaItem {
		int mPositions[];
		boolean isShowLegalArea = false;
	}

	/**
	 * 折点的点击事件
	 * 
	 * @author frj
	 * 
	 */
	public interface OnKneeClickListener {
		/**
		 * 点击的项
		 * 
		 * @param childPosition
		 *            点击的项在集合中的位置
		 */
		void onKneeClick(int groupPosition, int childPosition);

		/**
		 * 双击控件
		 */
		void onDoubleClick();
	}

	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		if (mList != null) {
			int groupPositon = -1;
			int childPosition = -1;
			for (int i = 0; i < mList.size(); i++) {
				if (mList.get(i).items != null) {
					for (int j = 0; j < mList.get(i).items.size(); j++) {
						if (mList.get(i).items.get(j).rect != null) {
							if (mList.get(i).items.get(j).rect.contains(
									(int) e.getX(), (int) e.getY()) == true) {
								// 根据点击的位置，判断点击位置在合法区域中哪个更接近中心区域
								if (childPosition == -1) {
									groupPositon = i;
									childPosition = j;
								} else {
									Rect before = mList.get(groupPositon).items
											.get(childPosition).rect;
									// 之前的中心点坐标
									int before_x = (before.right - before.left) / 2;
									int before_y = (before.bottom - before.top) / 2;

									Rect curr = mList.get(i).items.get(j).rect;
									// 当前的中心点坐标
									int curr_x = (curr.right - curr.left) / 2;
									int curr_y = (curr.bottom - curr.top) / 2;

									// 之前的点与点击点的差值和（横坐标的差值+纵坐标的差值）
									int before_diff = Math.abs(before_x
											- (int) e.getX())
											+ Math.abs(before_y
													- (int) e.getY());
									// 当前点与点击点的差值和（横坐标的差值+纵坐标的差值）
									int curr_diff = Math.abs(curr_x
											- (int) e.getX())
											+ Math.abs(curr_y - (int) e.getY());
									// 选择差值更小的
									if (curr_diff < before_diff) {
										groupPositon = i;
										childPosition = j;
									}
								}
							}
						}
					}
				}
			}
			if (mListener != null) {
				if (groupPositon != -1 && childPosition != -1) {
					mListener.onKneeClick(groupPositon, childPosition);
				}
			}
		}
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		return false;
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
		return false;
	}

	/**
	 * 双击
	 */
	@Override
	public boolean onDoubleTap(MotionEvent e) {
		if (mListener != null) {
			mListener.onDoubleClick();
		}
		return false;
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent e) {
		return false;
	}

}
