package com.btten.bttenlibrary.base.permission.callback;

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

/**
 * function:
 * author: Administrator
 * modify date: 2017/5/31
 */

public interface BaseCallback
{

    void onSkip(@NonNull String permissionName);

    void onNext(@NonNull String permissionName);

    void onStatusBarColorChange(@ColorInt int color);

    void onPermissionRequest(@NonNull String permission, boolean canSkip);
}

