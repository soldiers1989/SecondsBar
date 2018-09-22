/**
 * <Title:MyGifView.java>
 * <Description:显示Gif动画>
 * <Company:>
 * <Author:Frj>
 * <Mender:2014-9-11>
 * <Version:1.0>
 */
package com.btten.bttenlibrary.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import com.btten.bttenlibrary.util.DensityUtil;

/**
 * 功能：显示Gif动画
 * <p>
 * 使用：调用setGif()开始显示。调用setStop()停止显示。 注意：某些.gif图可能会获取不到时间间隔，原因可能是.gif不标准。
 */
public class GifView extends View
{

    private long movieStar;
    private Movie movie;
    private int resId;

    private int screenWidth = 0; // 屏幕宽度
    private int screenHeight = 0; // 屏幕高度

    /**
     * @param context
     * @param attrs
     */
    public GifView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();
        screenHeight = wm.getDefaultDisplay().getHeight();
    }

    /**
     * @param resId 资源Id(raw文件中)
     * @author：Frj 功能:设置显示的gif动图 使用方法：
     */
    public void setGif(int resId)
    {
        this.resId = resId;
        movie = Movie.decodeStream(getResources().openRawResource(resId));
    }

    /**
     * 获取持续时长
     * @return 持续时长
     */
    public int getDuration()
    {
        if (movie != null)
        {
            return movie.duration();
        }
        return 0;
    }

    /**
     * @author：Frj 功能:停止播放动画 使用方法：
     */
    public void setStop()
    {
        movie = null;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        long curTime = android.os.SystemClock.uptimeMillis();
        // 第一次播放
        if (movieStar == 0)
        {
            movieStar = curTime;
        }
        if (movie != null)
        {
            int duraction = movie.duration();
            int relTime = (int) ((curTime - movieStar) % duraction);
            movie.setTime(relTime);
            int width = movie.width();
            int height = movie.height();
            float x = (screenWidth - width) / 2; // Gif动画显示起始X轴点
            float y = (screenHeight - height - DensityUtil.dip2px(getContext(), 45)) / 2; // Gif动画显示起始Y轴点
            // 这里同时减去了界面顶部的高度
            // movie.draw(canvas, 0, 0);
            movie.draw(canvas, x, y);
            // 强制重绘
            invalidate();
        }
        super.onDraw(canvas);
    }
}
