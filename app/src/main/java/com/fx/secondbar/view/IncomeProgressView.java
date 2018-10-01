package com.fx.secondbar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.btten.bttenlibrary.util.DensityUtil;
import com.fx.secondbar.application.FxApplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * function:收益进度条
 * author: frj
 * modify date: 2018/10/1
 */
public class IncomeProgressView extends View
{
    //进度条颜色#00ffff
    private int progressColor = Color.parseColor("#00ffff");
    //外圆背景颜色，默认#353C4C
    private int outsideBgColor = Color.parseColor("#353C4C");
    //内圆背景颜色，默认#747474
    private int innerBgColor = Color.parseColor("#747474");
    //提示文本值
    private String tipText = "收益\n累计中";
    //提示文本颜色，默认颜色#00ffff
    private int tipTextColor = Color.parseColor("#00ffff");
    //提示文本字体大小
    private int tipTextSize = DensityUtil.sp2px(FxApplication.getInstance(), 12);
    //最大进度，默认6000（单位为时间）
    private int maxProgress = 60 * 1000;
    //宽
    private int mWidth;
    //高
    private int mHeight;
    //圆尺寸
    private int size;
    //进度条宽度
    private int progressWidth = DensityUtil.dip2px(FxApplication.getInstance(), 3);
    //内圆与外圆之间的间隙
    private int outsideInnerSpace = DensityUtil.dip2px(FxApplication.getInstance(), 8);

    private Paint paintOutside;
    private Paint paintInner;
    private Paint paintProgress;
    private TextPaint textPaint;
    //外圆半径
    private int outSideRadius;
    //内圆半径
    private int innerRadius;
    //进度条的矩形区域
    private RectF rectF;
    //文字区域
    private StaticLayout layout;

    private int textPosX = 0;

    //动画相关,该值表示百分比
    private int mPercent = 0;
    //步长
    private int step = maxProgress / 100;

    //是否销毁
    private boolean isDestroy = false;
    //是否开始
    private boolean isStart = false;

    private OnProgressChangeListener onProgressChangeListener;

    public IncomeProgressView(Context context)
    {
        this(context, null);
    }

    public IncomeProgressView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        initPaint();
        initThread();
    }

    /**
     * 初始化画笔
     */
    private void initPaint()
    {
        paintInner = new Paint();
        paintInner.setStyle(Paint.Style.FILL);
        paintInner.setColor(innerBgColor);
        // 抗锯齿
        paintInner.setAntiAlias(true);
        // 使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
        paintInner.setDither(true);

        paintOutside = new Paint();
        paintOutside.setStyle(Paint.Style.FILL);
        paintOutside.setColor(outsideBgColor);
        // 抗锯齿
        paintOutside.setAntiAlias(true);
        // 使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
        paintOutside.setDither(true);

        paintProgress = new Paint();
        paintProgress.setStyle(Paint.Style.FILL);
        paintProgress.setColor(progressColor);
        // 抗锯齿
        paintProgress.setAntiAlias(true);
        // 使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
        paintProgress.setDither(true);

        textPaint = new TextPaint();
        textPaint.setColor(tipTextColor);
        // 抗锯齿
        textPaint.setAntiAlias(true);
        // 使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
        textPaint.setDither(true);
        textPaint.setTextSize(tipTextSize);
    }

    /**
     * 初始化线程
     */
    private void initThread()
    {
        ExecutorService service1 = Executors.newSingleThreadExecutor();
        service1.execute(new Runnable()
        {
            @Override
            public void run()
            {
                while (!isDestroy)
                {
                    if (isStart)
                    {
                        if (mPercent == 100)
                        {
                            mPercent = 0;
                        }
                        mPercent++;
                        postInvalidate();
                        try
                        {
                            Thread.sleep(step);
                        } catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }


    /**
     * 设置进度变化监听器
     *
     * @param onProgressChangeListener
     */
    public void setOnProgressChangeListener(OnProgressChangeListener onProgressChangeListener)
    {
        this.onProgressChangeListener = onProgressChangeListener;
    }

    /**
     * 设置开始进度
     */
    public void setStartProgress(int percent)
    {
        mPercent = percent;
    }

    /**
     * 获取当前进度值
     *
     * @return
     */
    public int getPercent()
    {
        return mPercent;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         * 设置宽度
         */
        int width_specMode = MeasureSpec.getMode(widthMeasureSpec);
        int width_specSize = MeasureSpec.getSize(widthMeasureSpec);
        // match_parent , 和实际值
        if (width_specMode == MeasureSpec.EXACTLY)
        {
            mWidth = width_specSize;
            mWidth = mWidth - getPaddingLeft() - getPaddingRight();
        } else
        {
            DisplayMetrics displayMetrics = getContext().getResources()
                    .getDisplayMetrics();
            mWidth = displayMetrics.widthPixels - getPaddingLeft()
                    - getPaddingRight();
        }

        /**
         * 设置高度
         */
        int height_specMode = MeasureSpec.getMode(heightMeasureSpec);
        int height_specSize = MeasureSpec.getSize(heightMeasureSpec);
        // match_parent , 和实际值
        if (height_specMode == MeasureSpec.EXACTLY)
        {
            mHeight = height_specSize;
        } else
        {
            DisplayMetrics displayMetrics = getContext().getResources()
                    .getDisplayMetrics();
            mHeight = displayMetrics.heightPixels - getPaddingTop()
                    - getPaddingBottom();
        }
        setMeasuredDimension(mWidth, mHeight);
        //根据较小值，为圆的直径
        size = Math.min(mWidth, mHeight);
        outSideRadius = size / 2;
        innerRadius = outSideRadius - outsideInnerSpace;
        rectF = new RectF(size / 2 - (innerRadius + progressWidth), size / 2 - (innerRadius + progressWidth), size / 2 + innerRadius + progressWidth, size / 2 + innerRadius + progressWidth);
        layout = new StaticLayout(tipText, textPaint, innerRadius * 2,
                Layout.Alignment.ALIGN_CENTER, 1.0F, 0.0F, true);
        float width = 0;
        for (int i = 0; i < layout.getLineCount(); i++)
        {
            float lineWidth = layout.getLineWidth(i);
            width = Math.max(width, lineWidth);
        }
        //文字开始绘制的X轴位置,因为layout有个宽度，宽度为内圆的直径，实际上就是内圆的左侧位置
        textPosX = (mWidth / 2 - innerRadius);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        //画外圆
        canvas.drawCircle(size / 2f, size / 2f, outSideRadius, paintOutside);
        //绘制圆环
        canvas.drawArc(rectF, 270, mPercent / 100f * 360, true, paintProgress);
        //画内圆
        canvas.drawCircle(size / 2f, size / 2f, innerRadius, paintInner);
        //绘制文字
        canvas.save();
        canvas.translate(textPosX, (mHeight - layout.getHeight()) / 2);//从中间开始画
        layout.draw(canvas);
        canvas.restore();
        if (onProgressChangeListener != null)
        {
            onProgressChangeListener.onProgress(mPercent);
        }
    }

    /**
     * 开始
     */
    public void start()
    {
        isStart = true;
    }

    /**
     * 结束
     */
    public void stop()
    {
        isStart = false;
    }

    /**
     * 取消动画（关闭线程）
     */
    public void destroy()
    {
        isDestroy = true;
    }

    public interface OnProgressChangeListener
    {

        //进度变化监听器
        void onProgress(int progress);
    }
}
