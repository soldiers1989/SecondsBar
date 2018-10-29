package com.fx.secondbar.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.nio.ByteBuffer;
import java.security.MessageDigest;

/**
 * function:缩放Bitmap
 * author: frj
 * modify date: 2018/10/29
 */
public class ScaleTransform extends BitmapTransformation
{

    private static final String ID = "com.fx.secondbar.util.ScaleTransform";
    private static final byte[] ID_BYTES = ID.getBytes(CHARSET);

    private int targetWidth;

    public ScaleTransform(int targetWidth)
    {
        this.targetWidth = targetWidth;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight)
    {
        if (toTransform != null)
        {
            int width = toTransform.getWidth();
            int height = toTransform.getHeight();
            // 计算缩放比例
            float scaleWidth = ((float) targetWidth) / width;
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleWidth);
            Bitmap newbm = Bitmap.createBitmap(toTransform, 0, 0, width, height, matrix,
                    true);
            return newbm;
        }
        return toTransform;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest)
    {
        messageDigest.update(ID_BYTES);

        byte[] radiusData = ByteBuffer.allocate(4).putInt(targetWidth).array();
        messageDigest.update(radiusData);
    }
}
