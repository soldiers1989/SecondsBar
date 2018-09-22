package com.btten.bttenlibrary.view;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 功能：带有倒影的ImageView
 */
public class InvertedImageView extends ImageView
{
	public InvertedImageView(Context context)
	{
		super(context);
	}

	public InvertedImageView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	@Override
	public void setImageBitmap(Bitmap bm)
	{
		super.setImageBitmap(createReflectedImages(bm));
	}

	/**
	 * 
	 * @author：Frj 功能:对Bitmap进行处理，让其带有倒影 使用方法：
	 * @param bm
	 * @return
	 */
	private Bitmap createReflectedImages(Bitmap bm)
	{
		final int reflectionGap = 0;

		int width = bm.getWidth();
		int height = bm.getHeight();

		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);

		Bitmap reflectionImage = Bitmap.createBitmap(bm, 0, height / 2, width,
				height / 2, matrix, false);

		Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
				(height + height / 2), Config.ARGB_8888);

		Canvas canvas = new Canvas(bitmapWithReflection);

		canvas.drawBitmap(bm, 0, 0, null);

		Paint deafaultPaint = new Paint();
		canvas.drawRect(0, height, width, height + reflectionGap, deafaultPaint);

		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0, bm.getHeight(), 0,
				bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
				0x00ffffff, TileMode.CLAMP);

		paint.setShader(shader);

		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));

		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
				+ reflectionGap, paint);
		ByteArrayOutputStream baos = null;
		try
		{
			baos = new ByteArrayOutputStream();
			bitmapWithReflection.compress(Bitmap.CompressFormat.JPEG, 30, baos);
		}
		finally
		{
			try
			{
				if (baos != null)
					baos.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return bitmapWithReflection;
	}
}
