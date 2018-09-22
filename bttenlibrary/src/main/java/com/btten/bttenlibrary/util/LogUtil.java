package com.btten.bttenlibrary.util;

import com.btten.bttenlibrary.application.BtApplication;

import android.text.TextUtils;
import android.util.Log;

/**
 * 日志工具类 只当在测试环境下才会输出日志信息
 * 
 * @author frj
 * 
 */
public class LogUtil {

	/**
	 * 默认Tag
	 */
	private static final String TAG = "LogUtil";

	/**
	 * 输出I级日志信息
	 * 
	 * @param tag
	 *            日志标签
	 * @param content
	 *            日志信息
	 * @param tr
	 *            异常信息
	 */
	public static void i(String tag, String content, Throwable tr) {
		if (BtApplication.getApplication().isDebug()) {
			if (TextUtils.isEmpty(content)) {
				content = "content is null";
			}
			if (tr == null) {
				Log.i(TextUtils.isEmpty(tag) ? TAG : tag, content);
			} else {
				Log.i(TextUtils.isEmpty(tag) ? TAG : tag, content, tr);
			}
		}
	}

	/**
	 * 输出I级日志信息
	 * 
	 * @param tag
	 * @param content
	 */
	public static void i(String tag, String content) {
		i(tag, content, null);
	}

	/**
	 * 输出I级日志信息
	 * 
	 * @param tag
	 * @param value
	 */
	public static void i(String tag, int value) {
		i(tag, String.valueOf(value));
	}

	/**
	 * 输出I级日志信息
	 * 
	 * @param tag
	 * @param value
	 */
	public static void i(String tag, float value) {
		i(tag, String.valueOf(value));
	}

	/**
	 * 输出I级日志信息
	 * 
	 * @param tag
	 * @param value
	 */
	public static void i(String tag, double value) {
		i(tag, String.valueOf(value));
	}

	/**
	 * 输出I级日志信息
	 * 
	 * @param tag
	 * @param value
	 */
	public static void i(String tag, boolean value) {
		i(tag, String.valueOf(value));
	}

	/**
	 * 输出I级日志信息
	 * 
	 * @param content
	 */
	public static void i(String content) {
		i(TAG, content);
	}

	/**
	 * 输出I级日志信息
	 * 
	 * @param value
	 */
	public static void i(int value) {
		i(TAG, value);
	}

	/**
	 * 输出I级日志信息
	 * 
	 * @param value
	 */
	public static void i(float value) {
		i(TAG, value);
	}

	/**
	 * 输出I级日志信息
	 * 
	 * @param value
	 */
	public static void i(double value) {
		i(TAG, value);
	}

	/**
	 * 输出I级日志信息
	 * 
	 * @param value
	 */
	public static void i(boolean value) {
		i(TAG, value);
	}

	/**
	 * 输出D级日志信息
	 * 
	 * @param tag
	 *            标签信息
	 * @param content
	 *            日志信息
	 * @param tr
	 *            异常信息
	 */
	public static void d(String tag, String content, Throwable tr) {
		if (BtApplication.getApplication().isDebug()) {
			if (TextUtils.isEmpty(content)) {
				content = "content is null";
			}
			if (tr == null) {
				Log.d(TextUtils.isEmpty(tag) ? TAG : tag, content);
			} else {
				Log.d(TextUtils.isEmpty(tag) ? TAG : tag, content, null);
			}
		}
	}

	/**
	 * 输出D级日志信息
	 * 
	 * @param tag
	 * @param content
	 */
	public static void d(String tag, String content) {
		d(tag, content,null);
	}

	/**
	 * 输出D级日志信息
	 * 
	 * @param tag
	 * @param value
	 */
	public static void d(String tag, int value) {
		d(tag, String.valueOf(value));
	}

	/**
	 * 输出D级日志信息
	 * 
	 * @param tag
	 * @param value
	 */
	public static void d(String tag, float value) {
		d(tag, String.valueOf(value));
	}

	/**
	 * 输出D级日志信息
	 * 
	 * @param tag
	 * @param value
	 */
	public static void d(String tag, double value) {
		d(tag, String.valueOf(value));
	}

	/**
	 * 输出D级日志信息
	 * 
	 * @param tag
	 * @param value
	 */
	public static void d(String tag, boolean value) {
		d(tag, String.valueOf(value));
	}

	/**
	 * 输出D级日志信息
	 * 
	 * @param content
	 */
	public static void d(String content) {
		d(TAG, content);
	}

	/**
	 * 输出I级日志信息
	 * 
	 * @param value
	 */
	public static void d(int value) {
		d(TAG, value);
	}

	/**
	 * 输出I级日志信息
	 * 
	 * @param value
	 */
	public static void d(float value) {
		d(TAG, value);
	}

	/**
	 * 输出I级日志信息
	 * 
	 * @param value
	 */
	public static void d(double value) {
		d(TAG, value);
	}

	/**
	 * 输出I级日志信息
	 * 
	 * @param value
	 */
	public static void d(boolean value) {
		d(TAG, value);
	}

	/**
	 * 输出E级日志信息
	 * 
	 * @param tag
	 *            标签
	 * @param content
	 *            内容
	 * @param tr
	 *            异常信息
	 */
	public static void e(String tag, String content, Throwable tr) {
		if (BtApplication.getApplication().isDebug()) {
			if (TextUtils.isEmpty(content)) {
				content = "content is null";
			}
			if (tr == null) {
				Log.e(TextUtils.isEmpty(tag) ? TAG : tag, content);
			} else {
				Log.e(TextUtils.isEmpty(tag) ? TAG : tag, content, tr);
			}
		}
	}

	/**
	 * 输出E级日志信息
	 * 
	 * @param tag
	 * @param content
	 */
	public static void e(String tag, String content) {
		e(tag, content,null);
	}

	/**
	 * 输出E级日志信息
	 * 
	 * @param tag
	 * @param value
	 */
	public static void e(String tag, int value) {
		e(tag, String.valueOf(value));
	}

	/**
	 * 输出E级日志信息
	 * 
	 * @param tag
	 * @param value
	 */
	public static void e(String tag, float value) {
		e(tag, String.valueOf(value));
	}

	/**
	 * 输出E级日志信息
	 * 
	 * @param tag
	 * @param value
	 */
	public static void e(String tag, double value) {
		e(tag, String.valueOf(value));
	}

	/**
	 * 输出E级日志信息
	 * 
	 * @param tag
	 * @param value
	 */
	public static void e(String tag, boolean value) {
		e(tag, String.valueOf(value));
	}

	/**
	 * 输出E级日志信息
	 * 
	 * @param content
	 */
	public static void e(String content) {
		e(TAG, content);
	}

	/**
	 * 输出I级日志信息
	 * 
	 * @param value
	 */
	public static void e(int value) {
		e(TAG, value);
	}

	/**
	 * 输出I级日志信息
	 * 
	 * @param value
	 */
	public static void e(float value) {
		e(TAG, value);
	}

	/**
	 * 输出I级日志信息
	 * 
	 * @param value
	 */
	public static void e(double value) {
		e(TAG, value);
	}

	/**
	 * 输出I级日志信息
	 * 
	 * @param value
	 */
	public static void e(boolean value) {
		e(TAG, value);
	}

	/**
	 * 输出W级日志信息
	 * 
	 * @param tag
	 *            日志标签
	 * @param content
	 *            日志信息
	 * @param tr
	 *            异常信息
	 */
	public static void w(String tag, String content, Throwable tr) {
		if (BtApplication.getApplication().isDebug()) {
			if (TextUtils.isEmpty(content)) {
				content = "content is null";
			}
			if (tr == null) {
				Log.w(TextUtils.isEmpty(tag) ? TAG : tag, content);
			} else {
				Log.w(TextUtils.isEmpty(tag) ? TAG : tag, content, tr);
			}
		}
	}

	/**
	 * 输出W级日志信息
	 * 
	 * @param tag
	 * @param content
	 */
	public static void w(String tag, String content) {
		w(tag, content, null);
	}

	/**
	 * 输出W级日志信息
	 * 
	 * @param tag
	 * @param value
	 */
	public static void w(String tag, int value) {
		w(tag, String.valueOf(value));
	}

	/**
	 * 输出W级日志信息
	 * 
	 * @param tag
	 * @param value
	 */
	public static void w(String tag, float value) {
		w(tag, String.valueOf(value));
	}

	/**
	 * 输出W级日志信息
	 * 
	 * @param tag
	 * @param value
	 */
	public static void w(String tag, double value) {
		w(tag, String.valueOf(value));
	}

	/**
	 * 输出W级日志信息
	 * 
	 * @param tag
	 * @param value
	 */
	public static void w(String tag, boolean value) {
		w(tag, String.valueOf(value));
	}

	/**
	 * 输出W级日志信息
	 * 
	 * @param content
	 */
	public static void w(String content) {
		w(TAG, content);
	}

	/**
	 * 输出I级日志信息
	 * 
	 * @param value
	 */
	public static void w(int value) {
		w(TAG, value);
	}

	/**
	 * 输出I级日志信息
	 * 
	 * @param value
	 */
	public static void w(float value) {
		w(TAG, value);
	}

	/**
	 * 输出I级日志信息
	 * 
	 * @param value
	 */
	public static void w(double value) {
		w(TAG, value);
	}

	/**
	 * 输出I级日志信息
	 * 
	 * @param value
	 */
	public static void w(boolean value) {
		w(TAG, value);
	}

	/**
	 * 输出V级日志信息
	 * 
	 * @param tag
	 *            日志标签
	 * @param content
	 *            日志信息
	 * @param tr
	 *            异常信息
	 */
	public static void v(String tag, String content, Throwable tr) {
		if (BtApplication.getApplication().isDebug()) {
			if (TextUtils.isEmpty(content)) {
				content = "content is null";
			}
			if (tr == null) {
				Log.v(TextUtils.isEmpty(tag) ? TAG : tag, content);
			} else {
				Log.v(TextUtils.isEmpty(tag) ? TAG : tag, content, tr);
			}
		}
	}

	/**
	 * 输出V级日志信息
	 * 
	 * @param tag
	 * @param content
	 */
	public static void v(String tag, String content) {
		v(tag, content, null);
	}

	/**
	 * 输出V级日志信息
	 * 
	 * @param tag
	 * @param value
	 */
	public static void v(String tag, int value) {
		v(tag, String.valueOf(value));
	}

	/**
	 * 输出V级日志信息
	 * 
	 * @param tag
	 * @param value
	 */
	public static void v(String tag, float value) {
		v(tag, String.valueOf(value));
	}

	/**
	 * 输出V级日志信息
	 * 
	 * @param tag
	 * @param value
	 */
	public static void v(String tag, double value) {
		v(tag, String.valueOf(value));
	}

	/**
	 * 输出V级日志信息
	 * 
	 * @param tag
	 * @param value
	 */
	public static void v(String tag, boolean value) {
		v(tag, String.valueOf(value));
	}

	/**
	 * 输出V级日志信息
	 * 
	 * @param content
	 */
	public static void v(String content) {
		v(TAG, content);
	}

	/**
	 * 输出I级日志信息
	 * 
	 * @param value
	 */
	public static void v(int value) {
		v(TAG, value);
	}

	/**
	 * 输出I级日志信息
	 * 
	 * @param value
	 */
	public static void v(float value) {
		v(TAG, value);
	}

	/**
	 * 输出I级日志信息
	 * 
	 * @param value
	 */
	public static void v(double value) {
		v(TAG, value);
	}

	/**
	 * 输出I级日志信息
	 * 
	 * @param value
	 */
	public static void v(boolean value) {
		v(TAG, value);
	}
}
