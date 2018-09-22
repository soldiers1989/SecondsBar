package com.fx.secondbar.ui.home.item;

import com.btten.bttenlibrary.base.FragmentSupport;

/**
 * function:运用于使用在ViewPager中的Fragment基类
 * author: frj
 * modify date: 2017/6/30
 */

public abstract class FragmentViewPagerBase extends FragmentSupport {
    /**
     * 表示ViewPager显示当前的Fragment
     */
    public abstract void onStarShow();
}
