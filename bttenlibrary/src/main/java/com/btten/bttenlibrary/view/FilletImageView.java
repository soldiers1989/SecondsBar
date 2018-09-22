package com.btten.bttenlibrary.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.btten.bttenlibrary.R;

/**
 * function:圆角ImageView
 * author: frj
 * modify date: 2016/11/29
 */

public class FilletImageView extends FrameLayout
{
    //上边两个圆角半径
    private float topRadius = 0;
    //下边两个圆角半径
    private float bottomRadius = 0;

    public FilletImageView(Context context)
    {
        super(context);
        /*
            设置默认的圆角值为6px
         */
        topRadius = 6;
        bottomRadius = 6;
    }

    public FilletImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setCustomAttributes(attrs);
    }

    public FilletImageView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        setCustomAttributes(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FilletImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        setCustomAttributes(attrs);
    }

    /**
     * 设置自定义属性
     *
     * @param attrs
     */
    private void setCustomAttributes(AttributeSet attrs)
    {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.FilleIMageView);
        topRadius = a.getDimensionPixelSize(
                R.styleable.FilleIMageView_topRadius,
                0);
        bottomRadius = a
                .getDimensionPixelSize(R.styleable.FilleIMageView_bottomRadius, 0);
        int radius = a.getDimensionPixelSize(R.styleable.FilleIMageView_allRadius, 0);
        if (radius != 0)
        {
            if (topRadius == 0)
            {
                topRadius = radius;
            }
            if (bottomRadius == 0)
            {
                bottomRadius = radius;
            }
        }
        a.recycle();
    }

    /**
     * 设置各个角统一的圆角大小
     */
    public void setRadius(float radius)
    {
        topRadius = radius;
        bottomRadius = radius;
    }


    public float getTopRadius()
    {
        return topRadius;
    }

    public void setTopRadius(float topRadius)
    {
        this.topRadius = topRadius;
    }

    public float getBottomRadius()
    {
        return bottomRadius;
    }

    public void setBottomRadius(float bottomRadius)
    {
        this.bottomRadius = bottomRadius;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.save();
        if (topRadius != 0 || bottomRadius != 0)
        {
            //控件高度
            int height = getMeasuredHeight();
            //控件宽度
            int width = getMeasuredWidth();
            //上圆角之间的宽度
            float width_top_rect = width - 2 * topRadius;
            //下圆角之间的宽度
            float width_bottom_rect = width - 2 * bottomRadius;
            //圆角之间的高度
            float height_rect = height - topRadius - bottomRadius;
            //1.截取上圆角之间的矩形
            if (topRadius != 0)
            {
                canvas.clipRect(topRadius, 0, width - topRadius, topRadius,Region.Op.UNION);
            }
            //2.截取下圆角之间的矩形
            if (bottomRadius != 0)
            {
                canvas.clipRect(bottomRadius, height - bottomRadius, width - bottomRadius, bottomRadius,Region.Op.UNION);
            }
            //3.截取上下两个圆角之间的区域
            canvas.clipRect(0, topRadius, width, height - bottomRadius, Region.Op.UNION);
            //4.截取左上角圆角区域（取扇形）
            RectF rectF_topLeft = new RectF(0, 0, 2 * topRadius, 2 * topRadius);
            Path path_topLeft = new Path();
            path_topLeft.addArc(rectF_topLeft, 270 - 90, 90);
            path_topLeft.lineTo(topRadius, topRadius);
            path_topLeft.close();
            canvas.clipPath(path_topLeft, Region.Op.UNION);
            //5.截取右上角圆角区域
            RectF rectF_topRight = new RectF(width - 2 * topRadius, 0, width, 2 * topRadius);
            Path path_topRight = new Path();
            path_topRight.addArc(rectF_topRight, 270, 90);
            path_topRight.lineTo(width - topRadius, topRadius);
            path_topRight.close();
            canvas.clipPath(path_topRight, Region.Op.UNION);
            //6.截取左下角圆角区域
            RectF rectF_bottomLeft = new RectF(0, height - 2 * bottomRadius, 2 * bottomRadius, height);
            Path path_bottomLeft = new Path();
            path_bottomLeft.addArc(rectF_bottomLeft, 270 - 90, 90);
            path_bottomLeft.lineTo(bottomRadius, height - bottomRadius);
            path_bottomLeft.close();
            canvas.clipPath(path_bottomLeft, Region.Op.UNION);
            //7.截取右下角圆角区域
            RectF rectF_bottomRight = new RectF(width - 2 * bottomRadius, height - 2 * bottomRadius, width, height);
            Path path_bottomRight = new Path();
            path_bottomRight.addArc(rectF_bottomRight, 270 - 90, 90);
            path_bottomRight.lineTo(width - bottomRadius, height - bottomRadius);
            path_bottomRight.close();
            canvas.clipPath(path_bottomRight, Region.Op.UNION);
        }
        super.onDraw(canvas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas)
    {
        super.dispatchDraw(canvas);
        canvas.restore();
    }
}
