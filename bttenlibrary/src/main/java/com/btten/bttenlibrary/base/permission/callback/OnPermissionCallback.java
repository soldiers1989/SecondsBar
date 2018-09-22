package com.btten.bttenlibrary.base.permission.callback;

import android.support.annotation.NonNull;

/**
 * function:权限请求相关回调
 * author: Administrator
 * modify date: 2017/5/31
 */
public interface OnPermissionCallback
{
    void onPermissionGranted(@NonNull String[] permissionName);

    void onPermissionDeclined(@NonNull String[] permissionName);

    void onPermissionPreGranted(@NonNull String permissionsName);

    void onPermissionNeedExplanation(@NonNull String permissionName);

    void onPermissionReallyDeclined(@NonNull String permissionName);

    void onNoPermissionNeeded();
}
