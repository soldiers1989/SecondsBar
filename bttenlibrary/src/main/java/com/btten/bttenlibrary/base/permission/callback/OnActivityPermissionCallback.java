package com.btten.bttenlibrary.base.permission.callback;

import android.support.annotation.NonNull;

/**
 * function:
 * author: Administrator
 * modify date: 2017/5/31
 */

public interface OnActivityPermissionCallback
{
    void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
}
