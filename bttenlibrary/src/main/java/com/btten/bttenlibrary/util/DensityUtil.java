package com.btten.bttenlibrary.util;

import android.content.Context;

/**
 * dp、sp、px互相转换
 */
public class DensityUtil {

	/**
	 * 
	 * @author：Frj 功能:dip转px 使用方法：
	 * @param context
	 * @param dpValue
	 *            dip值
	 * @return px值
	 */
	public static int dip2px(Context context, float dpValue) {

		final float scale = context.getResources().getDisplayMetrics().density;

		return (int) (dpValue * scale + 0.5f);

	}

	/**
	 * 
	 * @author：Frj 功能:px转dip 使用方法：
	 * @param context
	 * @param pxValue
	 *            px值
	 * @return dip值
	 */
	public static int px2dip(Context context, float pxValue) {

		final float scale = context.getResources().getDisplayMetrics().density;

		return (int) (pxValue / scale + 0.5f);

	}

	/**
	 * 
	 * @author：Frj 功能:px转sp 使用方法：
	 * @param context
	 * @param pxValue
	 *            px值
	 * @return sp值
	 */
	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 
	 * @author：Frj 功能:sp转px 使用方法：
	 * @param context
	 * @param spValue
	 *            sp值
	 * @return px值
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

}
