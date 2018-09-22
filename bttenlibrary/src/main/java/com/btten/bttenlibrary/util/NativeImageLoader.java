package com.btten.bttenlibrary.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.media.ExifInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.text.TextUtils;

/**
 * 功能：本地图片加载器,采用的是异步解析本地图片，单例模式利用getInstance()获取NativeImageLoader实例
 * 调用loadNativeImage()方法加载本地图片，此类可作为一个加载本地图片的工具类
 */
public class NativeImageLoader
{

    private LruCache<String, Bitmap> mMemoryCache;
    private static NativeImageLoader mInstance = new NativeImageLoader();
    private ExecutorService mImageThreadPool = Executors.newFixedThreadPool(1);

    private NativeImageLoader()
    {
        // 获取应用程序的最大内存
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // 用最大内存的1/8来存储图片
        final int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize)
        {

            // 获取每张图片的大小
            @Override
            protected int sizeOf(String key, Bitmap bitmap)
            {
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }
        };
    }

    /**
     * 通过此方法来获取NativeImageLoader的实例
     *
     * @return
     */
    public static NativeImageLoader getInstance()
    {
        return mInstance;
    }

    /**
     * 加载本地图片，对图片不进行裁剪
     *
     * @param path
     * @param mCallBack
     * @return
     */
    public Bitmap loadNativeImage(final String path, final NativeImageCallBack mCallBack)
    {
        return this.loadNativeImage(path, null, mCallBack);
    }

    /**
     * 此方法来加载本地图片，这里的mPoint是用来封装ImageView的宽和高，我们会根据ImageView控件的大小来裁剪Bitmap
     * 如果你不想裁剪图片，调用loadNativeImage(final String path, final NativeImageCallBack
     * mCallBack)来加载
     *
     * @param path
     * @param mPoint
     * @param mCallBack
     * @return
     */
    public Bitmap loadNativeImage(final String path, final Point mPoint, final NativeImageCallBack mCallBack)
    {
        return loadNativeImage(path, mPoint, false, mCallBack);
    }

    /**
     * 此方法来加载本地图片，这里的mPoint是用来封装ImageView的宽和高，我们会根据ImageView控件的大小来裁剪Bitmap
     * 如果你不想裁剪图片，调用loadNativeImage(final String path, final NativeImageCallBack
     * mCallBack)来加载
     *
     * @param path      图片路径
     * @param mPoint    指定大小
     * @param isSave    表示是否对当前操作的图片文件保存压缩后的图片
     * @param mCallBack 回调接口
     * @return
     */
    public Bitmap loadNativeImage(final String path, final Point mPoint, final boolean isSave,
                                  final NativeImageCallBack mCallBack)
    {
        // 先获取内存中的Bitmap
        Bitmap bitmap = getBitmapFromMemCache(path);

        final Handler mHander = new Handler()
        {

            @Override
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);
                mCallBack.onImageLoader((Bitmap) msg.obj, path);
            }

        };

        // 若该Bitmap不在内存缓存中，则启用线程去加载本地的图片，并将Bitmap加入到mMemoryCache中
        if (bitmap == null)
        {
            mImageThreadPool.execute(new Runnable()
            {

                @Override
                public void run()
                {
                    // 先获取图片的缩略图
                    Bitmap mBitmap = decodeThumbBitmapForFile(path, mPoint == null ? 0 : mPoint.x,
                            mPoint == null ? 0 : mPoint.y);
                    Message msg = mHander.obtainMessage();
                    msg.obj = mBitmap;
                    if (isSave)
                    {
                        BitmapUtil.compressBitmapSaveToFile(mBitmap, path);
                    }
                    mHander.sendMessage(msg);

                    // 将图片加入到内存缓存
                    addBitmapToMemoryCache(path, mBitmap);
                }
            });
        } else
        {
            Message msg = mHander.obtainMessage();
            msg.obj = bitmap;
            mHander.sendMessage(msg);
        }
        return bitmap;

    }

    /**
     * 此方法来加载本地图片，这里的mPoint是用来封装ImageView的宽和高，我们会根据ImageView控件的大小来裁剪Bitmap
     * 如果你不想裁剪图片，调用loadNativeImage(final String path, final NativeImageCallBack
     * mCallBack)来加载
     *
     * @param path      图片路径
     * @param mPoint    指定大小
     * @param savePath  将压缩后的图片保存，保存路径
     * @param mCallBack 回调接口
     * @return
     */
    public Bitmap loadNativeImageAndSaveToPath(final String path, final Point mPoint, final String savePath,
                                               final NativeImageCallBack mCallBack)
    {
        // 先获取内存中的Bitmap
        Bitmap bitmap = getBitmapFromMemCache(path);

        final Handler mHander = new Handler()
        {

            @Override
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);
                mCallBack.onImageLoader((Bitmap) msg.obj, path);
            }

        };

        // 若该Bitmap不在内存缓存中，则启用线程去加载本地的图片，并将Bitmap加入到mMemoryCache中
        if (bitmap == null)
        {
            mImageThreadPool.execute(new Runnable()
            {
                @Override
                public void run()
                {
                    // 先获取图片的缩略图
                    Bitmap mBitmap = decodeThumbBitmapForFile(path, mPoint == null ? 0 : mPoint.x,
                            mPoint == null ? 0 : mPoint.y);
                    if (!TextUtils.isEmpty(savePath))
                    {
                        BitmapUtil.compressBitmapSaveToFile(mBitmap, savePath);
                        int degree = readPictureDegree(path);
                        mBitmap = rotateBitmap(mBitmap, degree);
                    }
                    Message msg = mHander.obtainMessage();
                    msg.obj = mBitmap;
                    mHander.sendMessage(msg);

                    // 将图片加入到内存缓存
                    addBitmapToMemoryCache(path, mBitmap);
                }
            });
        } else
        {
            Message msg = mHander.obtainMessage();
            msg.obj = bitmap;
            mHander.sendMessage(msg);
        }
        return bitmap;

    }

    /**
     * 往内存缓存中添加Bitmap
     *
     * @param key
     * @param bitmap
     */
    public void addBitmapToMemoryCache(String key, Bitmap bitmap)
    {
        if (getBitmapFromMemCache(key) == null && bitmap != null)
        {
            mMemoryCache.put(key, bitmap);
        }
    }

    /**
     * @param key 图片对应的Key
     * @author：frj 功能:从内存中移除图片 使用方法：
     */
    public void removeToMemoryCache(String key)
    {
        if (getBitmapFromMemCache(key) != null)
        {
            mMemoryCache.remove(key);
        }
    }

    /**
     * 根据key来获取内存中的图片
     *
     * @param key
     * @return
     */
    public Bitmap getBitmapFromMemCache(String key)
    {
        return mMemoryCache.get(key);
    }

    /**
     * 根据View(主要是ImageView)的宽和高来获取图片的缩略图
     *
     * @param path
     * @param viewWidth
     * @param viewHeight
     * @return
     */
    private Bitmap decodeThumbBitmapForFile(String path, int viewWidth, int viewHeight)
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
        ByteArrayOutputStream baos = null;
        try
        {
            baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 30, baos);
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
     * 根据View(主要是ImageView)的宽和高来计算Bitmap缩放比例。默认不缩放
     *
     * @param options
     * @param viewWidth
     * @param viewHeight
     */
    private int computeScale(BitmapFactory.Options options, int viewWidth, int viewHeight)
    {
        int inSampleSize = 1;
        if (viewWidth == 0 || viewWidth == 0)
        {
            return inSampleSize;
        }
        int bitmapWidth = options.outWidth;
        int bitmapHeight = options.outHeight;

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
    private int readPictureDegree(String path)
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
    private Bitmap rotateBitmap(Bitmap bitmap, int rotate)
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
     * 将图片文件解析成大小小于100K的Bitmap对象。 该方法属于耗时操作，建议线程中调用。
     *
     * @param file 图片文件
     * @return 小于100K大小的Bitmap对象
     */
    public byte[] getThumbBitmapForFile(File file)
    {
        return getThumbBitmapForFile(file, false);
    }

    /**
     * 将图片文件解析成大小小于100K的Bitmap对象。 该方法属于耗时操作，建议线程中调用。
     *
     * @param file     图片文件
     * @param isRotate 表示是否旋转
     * @return 小于100K大小的Bitmap对象
     */
    public byte[] getThumbBitmapForFile(File file, boolean isRotate)
    {
        if (file == null)
        {
            return null;
        }
        if (!file.exists())
        {
            return null;
        }
        try
        {
            if (FileUtils.getFileSize(file) < BitmapUtil.COMPRESSIBILITY_FOR_FILE)
            {
                return getDecodeThumbBitmapForFile(file.getAbsolutePath(), 480, 800, 80,isRotate);
            } else
            {
                Bitmap bitmap = decodeThumbBitmapForFile(file.getAbsolutePath(), 480, 800, 100, isRotate);
                return BitmapUtil.getCompressBitmapBytes(bitmap);
            }
        } catch (Error e)
        {
            e.printStackTrace();
            return null;
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据View(主要是ImageView)的宽和高来获取图片的缩略图的字节集合
     *
     * @param path
     * @param viewWidth
     * @param viewHeight
     * @param compress   压缩比例
     * @return 字节数组
     */
    private byte[] getDecodeThumbBitmapForFile(String path, int viewWidth, int viewHeight, int compress)
            throws Error, Exception
    {
        return getDecodeThumbBitmapForFile(path, viewWidth, viewHeight, compress, false);
    }

    /**
     * 根据View(主要是ImageView)的宽和高来获取图片的缩略图的字节集合
     *
     * @param path
     * @param viewWidth
     * @param viewHeight
     * @param compress   压缩比例
     * @param isRotate   表示是否旋转
     * @return 字节数组
     */
    private byte[] getDecodeThumbBitmapForFile(String path, int viewWidth, int viewHeight, int compress, boolean isRotate)
            throws Error, Exception
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
        Bitmap temp = null;
        if (isRotate)
        {
            temp = rotateBitmap(bm, readPictureDegree(path));
        } else
        {
            temp = bm;
        }
        byte[] buffer = null;
        ByteArrayOutputStream baos = null;
        try
        {
            baos = new ByteArrayOutputStream();
            temp.compress(Bitmap.CompressFormat.JPEG, compress, baos);
            buffer = baos.toByteArray();
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
        return buffer;
    }

    /**
     * 根据View(主要是ImageView)的宽和高来获取图片的缩略图
     *
     * @param path
     * @param viewWidth
     * @param viewHeight
     * @param compress   压缩比例
     * @return
     */
    private Bitmap decodeThumbBitmapForFile(String path, int viewWidth, int viewHeight, int compress)
            throws Error, Exception
    {
        return decodeThumbBitmapForFile(path, viewWidth, viewHeight, compress, false);
    }

    /**
     * 根据View(主要是ImageView)的宽和高来获取图片的缩略图
     *
     * @param path
     * @param viewWidth
     * @param viewHeight
     * @param compress   压缩比例
     * @param isRotate   是否旋转
     * @return
     */
    private Bitmap decodeThumbBitmapForFile(String path, int viewWidth, int viewHeight, int compress, boolean isRotate)
            throws Error, Exception
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
        Bitmap temp = null;
        if (isRotate)
        {
            Bitmap bitmap = rotateBitmap(bm, readPictureDegree(path));
            if (bitmap == null)
            {
                return null;
            }
            temp = bitmap;
        } else
        {
            temp = bm;
        }

        ByteArrayOutputStream baos = null;
        try
        {
            baos = new ByteArrayOutputStream();
            temp.compress(Bitmap.CompressFormat.JPEG, compress, baos);
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
        return temp;
    }

    /**
     * 加载本地图片的回调接口
     *
     * @author xiaanming
     */
    public interface NativeImageCallBack
    {
        /**
         * 当子线程加载完了本地的图片，将Bitmap和图片路径回调在此方法中
         *
         * @param bitmap
         * @param path
         */
        public void onImageLoader(Bitmap bitmap, String path);
    }
}
