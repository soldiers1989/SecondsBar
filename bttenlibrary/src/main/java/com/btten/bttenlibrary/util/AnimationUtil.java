package com.btten.bttenlibrary.util;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;

/**
 * 动画工具类
 *
 */
public class AnimationUtil implements AnimationListener {

	private Animation animation;
	private OnAnimationEndListener animationEndListener;
	private OnAnimationStartListener animationStartListener;
	private OnAnimationRepeatListener animationRepeatListener;

	/**
	 * 根据动画文件创建一个动画
	 * 
	 * @param context
	 * @param resId
	 *            动画文件资源id
	 */
	public AnimationUtil(Context context, int resId) {
		this.animation = AnimationUtils.loadAnimation(context, resId);
		this.animation.setAnimationListener(this);
	}

	/**
	 * 默认创建一个平移动画
	 * 
	 * @param fromXDelta
	 *            X轴开始位置
	 * @param toXDelta
	 *            X轴结束位置
	 * @param fromYDelta
	 *            Y轴开始位置
	 * @param toYDelta
	 *            Y轴结束位置
	 */
	public AnimationUtil(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta) {
		animation = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
	}

	/**
	 * 设置动画开始时间的偏移量
	 * 
	 * @param startOffset
	 *            单位毫秒，表示动画将在多长时间后开始
	 * @return
	 */
	public AnimationUtil setStartOffSet(long startOffset) {
		animation.setStartOffset(startOffset);
		return this;
	}

	/**
	 * 设置插入器
	 * 
	 * @param i
	 * @return
	 */
	public AnimationUtil setInterpolator(Interpolator i) {
		animation.setInterpolator(i);
		return this;
	}

	/**
	 * 设置速率恒定的插入器
	 * 
	 * @return
	 */
	public AnimationUtil setLinearInterpolator() {
		animation.setInterpolator(new LinearInterpolator());
		return this;
	}

	/**
	 * 开始动画
	 * 
	 * @param view
	 */
	public void startAnimation(View view) {
		view.startAnimation(animation);
	}

	/**
	 * 开始View的背景的动画
	 * 
	 * @param resId
	 *            背景资源ID
	 * @param view
	 */
	public static void startAnimation(int resId, View view) {
		view.setBackgroundResource(resId);
		((AnimationDrawable) view.getBackground()).start();
	}

	/**
	 * 设置持续时间 单位：毫秒
	 * 
	 * @param durationMillis
	 * @return
	 */
	public AnimationUtil setDuration(long durationMillis) {
		animation.setDuration(durationMillis);
		return this;
	}

	/**
	 * 设置动画结束后是否保持当前状态 ；默认为false 不保持；
	 * 
	 * @param fillAfter
	 * @return
	 */
	public AnimationUtil setFillAfter(boolean fillAfter) {
		animation.setFillAfter(fillAfter);
		return this;
	}

	/**
	 * 动画结束监听器
	 */
	public interface OnAnimationEndListener {
		void onAnimationEnd(Animation animation);
	}

	/**
	 * 动画开始监听器
	 * 
	 */
	public interface OnAnimationStartListener {
		void onAnimationStart(Animation animation);
	}

	/**
	 * 动画开始重复监听器
	 */
	public interface OnAnimationRepeatListener {
		void onAnimationRepeat(Animation animation);
	}

	/**
	 * 设置动画结束监听
	 * 
	 * @param listener
	 * @return
	 */
	public AnimationUtil setOnAnimationEndLinstener(OnAnimationEndListener listener) {
		this.animationEndListener = listener;
		return this;
	}

	/**
	 * 设置动画开始监听
	 * 
	 * @param listener
	 * @return
	 */
	public AnimationUtil setOnAnimationStartLinstener(OnAnimationStartListener listener) {
		this.animationStartListener = listener;
		return this;
	}

	/**
	 * 设置动画重复监听
	 * 
	 * @param listener
	 * @return
	 */
	public AnimationUtil setOnAnimationRepeatLinstener(OnAnimationRepeatListener listener) {
		this.animationRepeatListener = listener;
		return this;
	}

	/**
	 * 设置一个动画监听 通知显示动画相关的事件,如结束或动画的重复。
	 * 
	 * @param animationListener
	 */
	public void setAnimationListener(AnimationListener animationListener) {
		animation.setAnimationListener(animationListener);
	}

	@Override
	public void onAnimationStart(Animation animation) {
		if (this.animationStartListener != null) {
			this.animationStartListener.onAnimationStart(animation);
		}
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		if (this.animationEndListener != null) {
			this.animationEndListener.onAnimationEnd(animation);
		}
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		if (this.animationRepeatListener != null) {
			this.animationRepeatListener.onAnimationRepeat(animation);
		}
	}

}
