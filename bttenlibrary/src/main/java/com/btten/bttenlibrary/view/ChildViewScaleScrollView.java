package com.btten.bttenlibrary.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PointF;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ScrollView;

/**
 * 子控件进行缩放的ScrollView
 * 
 * 使用：与ScrollView使用一致，如果需要对子控件进行缩放，请调用setScaleView(View view)方法。
 * 
 * @author Frj
 * 
 */
public class ChildViewScaleScrollView extends ScrollView {

	// 要进行缩放的View
	private View scaleView;

	private float originalHeight = 0; // 控件原始的高度

	private GestureDetector mGestureDetector; // 手势监听

	/**
	 * 释放操作
	 */
	private static final int BACK_SCALE = 0;
	private float scaleY = 0;
	private boolean isBacking = false;// 是否处在回弹状态

	/** 记录是拖拉模式还是放大缩小模式 0:拖拉模式，1：放大 */
	private int mode = 0;// 初始状态
	/** 拖拉照片模式 */
	private final int MODE_DRAG = 1;
	/** 用于记录开始时候的坐标位置 */
	private PointF startPoint = new PointF();

	public ChildViewScaleScrollView(Context context) {
		super(context);
		init();
	}

	public ChildViewScaleScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ChildViewScaleScrollView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	/**
	 * 初始化 手势监听
	 */
	private void init() {
		mGestureDetector = new GestureDetector(getContext(),
				new YScrollDetector());
		setFadingEdgeLength(0);
	}

	/**
	 * 设置缩放的View（此处建议为ImageView或纯背景的View，当为ImageView时，ScaleType建议设置为center_crop）
	 * 
	 * @param scaleView
	 * @throws
	 */
	public void setScaleView(View scaleView) {
		if (scaleView == null) {
			throw new NullPointerException("scaleView is can't null");
		}
		this.scaleView = scaleView;
		scaleView.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {

					@SuppressLint("NewApi")
					@Override
					public void onGlobalLayout() {
						originalHeight = ChildViewScaleScrollView.this.scaleView
								.getHeight();
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
							ChildViewScaleScrollView.this.scaleView
									.getViewTreeObserver()
									.removeOnGlobalLayoutListener(this);
						} else {
							ChildViewScaleScrollView.this.scaleView
									.getViewTreeObserver()
									.removeGlobalOnLayoutListener(this);
						}
					}
				});
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (scaleView == null) {// 无头部图片
			return super.onTouchEvent(ev);
		}
		switch (ev.getAction() & MotionEvent.ACTION_MASK) {
		// 手指压下屏幕
		case MotionEvent.ACTION_DOWN:
			if (isBacking) {
				return super.onTouchEvent(ev);
			}
			int[] location = new int[2];
			scaleView.getLocationInWindow(location);
			if (location[1] >= 0) {
				mode = MODE_DRAG;
				// 记录ImageView当前的移动位置
				startPoint.set(ev.getX(), ev.getY());
			}
			break;
		// 手指在屏幕上移动，改事件会被不断触发
		case MotionEvent.ACTION_MOVE:
			// 拖拉图片
			if (mode == MODE_DRAG) {
				// float dx = event.getX() - startPoint.x; // 得到x轴的移动距离
				float dy = ev.getY() - startPoint.y; // 得到y轴的移动距离
				// 在没有移动之前的位置上进行移动
				if (dy / 2 + originalHeight <= 1.5 * originalHeight) {
					float scale = (dy / 2 + originalHeight) / (originalHeight);// 得到缩放倍数
					if (dy > 0) {
						scaleY = dy;
						ViewGroup.LayoutParams params = scaleView
								.getLayoutParams();
						params.height = (int) (scale * originalHeight);
						scaleView.setLayoutParams(params);
					}
				}
			}
			break;
		// 手指离开屏幕
		case MotionEvent.ACTION_UP:
			// 当触点离开屏幕，图片还原
			mHandler.sendEmptyMessage(BACK_SCALE);
		case MotionEvent.ACTION_POINTER_UP:
			mode = 0;
			break;
		}
		return super.dispatchTouchEvent(ev);
	}

	/**
	 * 向下滑动让图片变大
	 * 
	 * @param event
	 * @return
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case BACK_SCALE:
				float scale = (scaleY / 2 + originalHeight) / (originalHeight);// 得到缩放倍数
				if (scaleY > 0) {
					isBacking = true;
					ViewGroup.LayoutParams params = scaleView.getLayoutParams();
					params.height = (int) (scale * originalHeight);
					scaleView.setLayoutParams(params);
					scaleY = scaleY / 2 - 1;
					mHandler.sendEmptyMessageDelayed(BACK_SCALE, 20);
				} else {
					scaleY = 0;
					ViewGroup.LayoutParams params = scaleView.getLayoutParams();
					params.height = (int) originalHeight;
					scaleView.setLayoutParams(params);
					isBacking = false;
				}
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return super.onInterceptTouchEvent(ev);
		// return mGestureDetector.onTouchEvent(ev)
		// && super.onInterceptTouchEvent(ev);
		// return mGestureDetector.onTouchEvent(ev);
	}

	/**
	 * 判断当前是左右滑动还是上下滑动（根据左右和上下滑动距离判断，哪个距离更大，就表示哪个是滑动）
	 * 
	 * @author frj
	 * 
	 */
	private class YScrollDetector extends SimpleOnGestureListener {
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			// 如果是滑动就返回true 自己消费事件
			return true;
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			// 如果是单点就返回false 子控件消费事件
			return false;
		}
	}
}
