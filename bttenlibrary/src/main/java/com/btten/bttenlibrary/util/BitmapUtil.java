package com.btten.bttenlibrary.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;

/**
 * 功能：Bitmap操作工具类
 */
public class BitmapUtil
{
    /**
     * bitmap压缩率
     */
    private static final int COMPRESSIBILITY = 30;
    /**
     * 将Bitmap保存在文件时，文件的压缩大小的临界点，即小于这个值不再压缩 单位为Kb
     */
    public static final int COMPRESSIBILITY_FOR_FILE = 100;

    /**
     * 圆角的角度
     */
    private static final int ROUNT_DEGREE = 10;

    /**
     * 将图片压缩至指定的宽度大小
     */
    public static final int DEFAULT_WIDTH = 720;
    /**
     * 将图片压缩至指定的高度大小
     */
    public static final int DEFAULT_HEIGHT = 1280;

    /**
     * 根据View(主要是ImageView)的宽和高来获取图片的缩略图
     *
     * @param path
     * @param viewWidth
     * @param viewHeight
     * @return
     */
    public static Bitmap decodeThumbBitmapForFile(String path, int viewWidth, int viewHeight)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 设置为true,表示解析Bitmap对象，该对象不占内存
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        // 设置缩放比例
        options.inSampleSize = computeScale(options, viewWidth, viewHeight);

        // 设置为false,解析Bitmap对象加入到内存中
        options.inJustDecodeBounds = false;
        Bitmap bm = BitmapFactory.decodeFile(path, options);
        if (bm == null)
        {
            return null;
        }
        int degree = readPictureDegree(path);
        bm = rotateBitmap(bm, degree);
        ByteArrayOutputStream baos = null;
        try
        {
            baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, COMPRESSIBILITY, baos);
        } finally
        {
            try
            {
                if (baos != null)
                    baos.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return bm;
    }

    /**
     * @param context
     * @param resId      图片资源id
     * @param viewWidth  要缩放的宽度
     * @param viewHeight 要缩放的高度
     * @return
     * @author：Frj 功能:加载资源文件中的图像，并对其进行缩放和压缩 使用方法：
     */
    public static Bitmap decodeThumbBitmapForRes(Context context, int resId, int viewWidth, int viewHeight)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 设置为true,表示解析Bitmap对象，该对象不占内存
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), resId, options);
        // 设置缩放比例
        options.inSampleSize = computeScale(options, viewWidth, viewHeight);

        // 设置为false,解析Bitmap对象加入到内存中
        options.inJustDecodeBounds = false;
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), resId, options);
        if (bm == null)
        {
            return null;
        }
        ByteArrayOutputStream baos = null;
        try
        {
            baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, COMPRESSIBILITY, baos);
        } finally
        {
            try
            {
                if (baos != null)
                    baos.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return bm;
    }

    /**
     * 根据View(主要是ImageView)的宽和高来获取图片的缩略图的字节数组
     *
     * @param path
     * @param viewWidth
     * @param viewHeight
     * @param isOnlyReduce 表示是否仅缩小，即如果不对图片进行放大
     * @return
     */
    public static byte[] decodeThumbBitmapByteForFile(String path, int viewWidth, int viewHeight, boolean isOnlyReduce)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 设置为true,表示解析Bitmap对象，该对象不占内存
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        // 设置缩放比例
        options.inSampleSize = computeScale(options, viewWidth, viewHeight, isOnlyReduce);

        // 设置为false,解析Bitmap对象加入到内存中
        options.inJustDecodeBounds = false;
        Bitmap bm = BitmapFactory.decodeFile(path, options);
        if (bm == null)
        {
            return null;
        }
        int degree = readPictureDegree(path);
        bm = rotateBitmap(bm, degree);
        ByteArrayOutputStream baos = null;
        byte[] b = null;
        try
        {
            baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, COMPRESSIBILITY, baos);
            b = baos.toByteArray();
        } finally
        {
            try
            {
                if (baos != null)
                {
                    baos.close();
                }
                bm.recycle();
            } catch (IOException e)
            {
                e.printStackTrace();
            } catch (Error error)
            {
                error.printStackTrace();
            }
        }
        return b;
    }

    /**
     * @param bitmap
     * @param path
     * @author：frj 功能:压缩Bitmap，并保存到文件中
     */
    public static void compressBitmapSaveToFile(Bitmap bitmap, String path)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 如果签名是png的话，则不管quality是多少，都不会进行质量的压缩
        int quality = 100;
        while (baos.toByteArray().length / 1024f > COMPRESSIBILITY_FOR_FILE)
        {
            quality = quality - 5;// 每次都减少5
            if (quality <= 0)
            {
                break;
            }
            baos.reset();// 重置baos即清空baos
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        }
        try
        {
            File file = new File(path);
            if (!file.exists()) // 如果文件不存在，那么创建文件
            {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e)
        {
            e.printStackTrace();

        }
    }

    /**
     * 根据View(主要是ImageView)的宽和高来计算Bitmap缩放比例。默认不缩放
     *
     * @param options
     * @param viewWidth
     * @param viewHeight
     */
    public static int computeScale(BitmapFactory.Options options, int viewWidth, int viewHeight)
    {
        int inSampleSize = 1;
        if (viewWidth == 0 || viewWidth == 0)
        {
            return inSampleSize;
        }
        int bitmapWidth = options.outWidth;
        int bitmapHeight = options.outHeight;

        return computeScale(bitmapWidth, bitmapHeight, viewWidth, viewHeight);
    }

    /**
     * 根据View(主要是ImageView)的宽和高来计算Bitmap缩小比例。默认不缩放
     *
     * @param options
     * @param viewWidth
     * @param viewHeight
     * @param isOnlyReduce 表示是否仅缩小
     * @return
     */
    public static int computeScale(BitmapFactory.Options options, int viewWidth, int viewHeight, boolean isOnlyReduce)
    {
        int inSampleSize = 1;
        if (viewWidth == 0 || viewWidth == 0)
        {
            return inSampleSize;
        }
        int bitmapWidth = options.outWidth;
        int bitmapHeight = options.outHeight;
        if (isOnlyReduce)
        {
            if (bitmapWidth < viewWidth && bitmapHeight < viewHeight)
            {
                return inSampleSize;
            }
        }
        return computeScale(bitmapWidth, bitmapHeight, viewWidth, viewHeight);
    }


    /**
     * @param bitmapWidth  图片宽度
     * @param bitmapHeight 图片高度
     * @param viewWidth    控件宽度
     * @param viewHeight   控件高度
     * @return
     * @author：frj 功能:根据View(主要是ImageView)的宽和高来计算Bitmap缩放比例。默认不缩放 使用方法：
     */
    public static int computeScale(int bitmapWidth, int bitmapHeight, int viewWidth, int viewHeight)
    {
        int inSampleSize = 1;
        if (viewWidth == 0 || viewWidth == 0)
        {
            return inSampleSize;
        }
        // 假如Bitmap的宽度或高度大于我们设定图片的View的宽高，则计算缩放比例
        if (bitmapWidth > viewWidth || bitmapHeight > viewWidth)
        {
            int widthScale = Math.round((float) bitmapWidth / (float) viewWidth);
            int heightScale = Math.round((float) bitmapHeight / (float) viewWidth);

            // 为了保证图片不缩放变形，我们取宽高比例最小的那个
            inSampleSize = widthScale < heightScale ? widthScale : heightScale;
        }
        return inSampleSize;
    }

    /**
     * @param path
     * @return
     * @author：Frj 功能:计算图片旋转的角度 使用方法：
     */
    public static int readPictureDegree(String path)
    {
        int degree = 0;
        try
        {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation)
            {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * @param bitmap
     * @param rotate 图片要旋转的角度
     * @return
     * @author：Frj 功能:旋转图片 使用方法：
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int rotate)
    {
        if (bitmap == null)
            return null;
        if (rotate == 0)
        {
            return bitmap;
        }
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        // Setting post rotate to 90
        Matrix mtx = new Matrix();
        mtx.postRotate(rotate);
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
    }

    /**
     * 获取圆角位图的方法
     *
     * @param bitmap 需要转化成圆角的位图
     * @return 处理后的圆角位图
     */
    public static Bitmap toRoundCorner(Bitmap bitmap)
    {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = ROUNT_DEGREE;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /**
     * @param bitmap
     * @return
     * @author：Frj 功能:将Bitmap转换成Drawable对象 使用方法：
     */
    public static Drawable BitmapToDrawable(Bitmap bitmap)
    {
        return new BitmapDrawable(bitmap);
    }

    /**
     * @param drawable 要转换的Drawable对象
     * @return
     * @author：frj 功能:将Drawable对象转换成Bitmap对象 使用方法：
     */
    public static Bitmap drawableToBitmap(Drawable drawable)
    {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * @param b
     * @return
     * @author：frj 功能:将byte[]转换成Bitmap 使用方法：
     */
    public static Bitmap Bytes2Bimap(byte[] b)
    {
        try
        {
            if (b.length != 0)
            {
                return BitmapFactory.decodeByteArray(b, 0, b.length);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        } catch (OutOfMemoryError e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param bm
     * @return
     * @author：frj 功能:将Bitmap转换成byte[] 使用方法：
     */
    public static byte[] Bitmap2Bytes(Bitmap bm)
    {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] byteArray = baos.toByteArray();
        try
        {
            baos.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return byteArray;
    }

    /**
     * @param image
     * @param size  最大大小 单位Kb 如果值为0 则默认压缩至100Kb以下
     * @return
     * @author：Frj 功能:压缩图片至指定大小以下 使用方法：
     */
    public static Bitmap compressImage(Bitmap image, int size)
    {
        size = size == 0 ? COMPRESSIBILITY_FOR_FILE : size;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        float length = baos.toByteArray().length / 1024;
        while (length > size)
        { // 循环判断如果压缩后图片是否大于size，大于则继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
            if (options < 0)
            {
                break;
            }
            length = baos.toByteArray().length / 1024;
        }
        Bitmap bitmap = Bytes2Bimap(baos.toByteArray());
        if (baos != null)
        {
            try
            {
                baos.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    /**
     * @param bitmap
     * @author：frj 功能:压缩Bitmap，并保存到文件中 使用方法：
     */
    public static byte[] getCompressBitmapBytes(Bitmap bitmap)
    {
        if (bitmap == null)
        {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 如果签名是png的话，则不管quality是多少，都不会进行质量的压缩
        int quality = 100;
        while (baos.toByteArray().length / 1024f > COMPRESSIBILITY_FOR_FILE)
        {
            quality = quality - 5;// 每次都减少5
            if (quality <= 0)
            {
                break;
            }
            baos.reset();// 重置baos即清空baos
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        }
        byte[] byteArray = baos.toByteArray();
        try
        {
            baos.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return byteArray;
    }
}
