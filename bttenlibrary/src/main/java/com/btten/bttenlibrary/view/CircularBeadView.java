package com.btten.bttenlibrary.view;

import com.btten.bttenlibrary.application.BtApplication;
import com.btten.bttenlibrary.util.BitmapUtil;
import com.btten.bttenlibrary.util.ResourceHelper;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 圆角矩形的ImageView控件
 * 
 * 注意：
 * 
 * 1. 使用该控件时只支持两种ScaleType，CENTER_CROP和FIT_XY。当ScaleType非FIT_XY时，
 * 其余的都默认按照CENTER_CROP的模式缩放图片。
 * 
 * 2. 使用时控件宽高请不要使用wrap_content。
 * 
 * @author frj
 * 
 */
public class CircularBeadView extends ImageView {

	private int mRadius = 1; // 圆角大小

	// 控件默认长、宽
	private int mWidth = 0;
	private int mHeight = 0;

	public CircularBeadView(Context context) {
		super(context);
	}

	public CircularBeadView(Context context, AttributeSet attrs) {
		super(context, attrs);
		readAttributeSet(attrs);
	}

	public CircularBeadView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		readAttributeSet(attrs);
	}

	/**
	 * 读取属性文件设置
	 */
	private void readAttributeSet(AttributeSet attrs) {
		TypedArray array = getContext().obtainStyledAttributes(attrs, ResourceHelper
				.getInstance(BtApplication.getApplication().getApplicationContext()).getStyleableIds("circularview"));
		mRadius = array.getDimensionPixelSize(
				ResourceHelper.getInstance(BtApplication.getApplication().getApplicationContext())
						.getStyleableId("circularview_radius"),
				1);
		array.recycle();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		try {
			Drawable drawable = getDrawable();
			if (drawable == null) {
				return;
			}
			if (getWidth() == 0 || getHeight() == 0) {
				return;
			}
			if (mWidth == 0) {
				mWidth = getWidth();
			}
			if (mHeight == 0) {
				mHeight = getHeight();
			}
			Bitmap b = BitmapUtil.drawableToBitmap(drawable);

			Bitmap scaledBitmap = null;
			if (mHeight != b.getHeight() || mWidth != b.getWidth()) {
				scaledBitmap = createScaledBitmap(b, mWidth, mHeight, true);
			} else {
				scaledBitmap = b;
			}
			Bitmap roundBitmap = createRoundConerImage(scaledBitmap);
			if (!scaledBitmap.isRecycled()) {
				scaledBitmap.recycle();
				scaledBitmap = null;
			}
			if (!b.isRecycled()) {
				b.recycle();
				b = null;
			}
			canvas.drawBitmap(roundBitmap, 0, 0, null);
		} catch (Exception e) {
			e.printStackTrace();
			super.onDraw(canvas);
		} catch (OutOfMemoryError error) {
			error.printStackTrace();
			super.onDraw(canvas);
		}
	}

	/**
	 * 根据原图添加圆角
	 * 
	 * @param source
	 * @return
	 */
	private Bitmap createRoundConerImage(Bitmap source) {

		final Paint paint = new Paint();
		paint.setAntiAlias(true);
		Bitmap target = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(target);
		RectF rect = new RectF(0, 0, source.getWidth(), source.getHeight());
		canvas.drawRoundRect(rect, mRadius, mRadius, paint);
		/*
		 * SRC_IN这种模式，两个绘制的效果叠加后取交集展现后图。 怎么说呢，咱们第一个绘制的是个圆角矩形，第二个绘制的是个Bitmap，
		 * 于是交集为圆角矩形，展现的是BItmap，就实现了圆角图片效果。
		 */
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(source, 0, 0, paint);
		if (!source.isRecycled()) {
			source.recycle();
			source = null;
		}
		return target;
	}

	/**
	 * 缩放图片，该方法只实现FIT_XY 与CENTER_CROP的效果。当ScaleType不为FIT_XY时，都返回CENTER_CROP效果
	 * 
	 * @param src
	 *            图片
	 * @param dstWidth
	 *            宽度
	 * @param dstHeight
	 *            高度
	 * @param filter
	 *            表示源图是否过滤
	 * @return
	 */
	private Bitmap createScaledBitmap(Bitmap src, int dstWidth, int dstHeight, boolean filter) {
		if (ScaleType.FIT_XY == getScaleType()) {
			return Bitmap.createScaledBitmap(src, dstWidth, dstHeight, filter);
		} else {
			Matrix m = new Matrix();
			float sx = (float) dstWidth / (float) src.getWidth();
			float sy = (float) dstHeight / (float) src.getHeight();
			float scale = 0; // 缩放倍数
			int x = 0; // 宽截取起点
			int y = 0; // 高截取起点
			if (sx <= sy) {
				scale = sy;
			} else {
				scale = sx;
			}
			m.setScale(scale, scale);
			// 创建缩放后的图片
			Bitmap temp = Bitmap.createBitmap(src, x, y, src.getWidth(), src.getHeight(), m, filter);
			int width = temp.getWidth();
			int height = temp.getHeight();
			if (dstWidth > dstHeight) {
				// 截取开始位置
				y = (dstWidth - dstHeight) / 2;
				height = dstHeight;
				x = 0;
			} else {
				// 截取开始位置
				x = (dstHeight - dstWidth) / 2;
				width = dstWidth;
				y = 0;
			}
			// 根据缩放后的图片以及控件宽高获得截取后的图片
			return Bitmap.createBitmap(temp, x, y, width, height, new Matrix(), filter);
		}
	}
}
