package com.fx.secondbar.util;

import android.support.annotation.DrawableRes;

import com.fx.secondbar.R;

/**
 * function:等级工具
 * author: frj
 * modify date: 2018/10/2
 */
public class LevelUtils
{

    /**
     * 获取等级icon
     *
     * @param level
     * @return
     */
    public static @DrawableRes
    int getLevelIcons(int level)
    {
        switch (level)
        {
            case 1:
                return R.mipmap.ic_level_1;
            case 2:
                return R.mipmap.ic_level_2;
            case 3:
                return R.mipmap.ic_level_3;
            case 4:
                return R.mipmap.ic_level_4;
            case 5:
                return R.mipmap.ic_level_5;
            case 6:
                return R.mipmap.ic_level_6;
            case 7:
                return R.mipmap.ic_level_7;
            case 8:
                return R.mipmap.ic_level_8;
            default:
                return R.mipmap.ic_level_1;
        }
    }
}
