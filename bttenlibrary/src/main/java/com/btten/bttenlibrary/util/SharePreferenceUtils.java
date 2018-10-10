package com.btten.bttenlibrary.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.btten.bttenlibrary.application.BtApplication;

/**
 * SharePreference操作类
 */
public class SharePreferenceUtils
{
    /**
     * 用户名
     */
    public static final String ACCOUNT = "account";
    /**
     * 密码
     */
    public static final String PASSWORD = "password";

    /**
     * 保存在Sharepreference文件中的uid的key
     */
    public static final String KEY_UID = "key_uid";
    /**
     * 保存在Sharepreference文件中的username的key
     */
    public static final String KEY_USERNAME = "key_username";

    /**
     * sharepreference 文件名称
     */
    public static String SHAREPREFERENCE_NAME = "btten_sharepreference";

    /**
     * @param username 用户名
     * @param password 密码
     * @author：Frj 功能:保存用户名密码信息 使用方法：
     */
    public static void saveAccount(String username, String password)
    {
        // 获取sharepreferences实例
        SharedPreferences sharedPreferences = BtApplication.getApplication().getSharedPreferences(SHAREPREFERENCE_NAME,
                Context.MODE_PRIVATE);
        // 编辑器用来编辑
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ACCOUNT, username);
        editor.putString(PASSWORD, password);
        editor.commit();
    }

    /**
     * @param key   key
     * @param value 值
     * @author：Frj 功能:保存数据至本地存储文件 使用方法：
     */
    public static void savePreferences(String key, String value)
    {
        SharedPreferences sharedPreferences = BtApplication.getApplication().getSharedPreferences(SHAREPREFERENCE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 保存数据至本地存储文件
     *
     * @param key   key
     * @param value 值
     */
    public static void savePreferences(String key, int value)
    {
        SharedPreferences sharedPreferences = BtApplication.getApplication().getSharedPreferences(SHAREPREFERENCE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 保存数据值本地存储文件
     *
     * @param key   key
     * @param value 值
     */
    public static void savePreferences(String key, boolean value)
    {
        SharedPreferences sharedPreferences = BtApplication.getApplication().getSharedPreferences(SHAREPREFERENCE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * @param key key
     * @return
     * @author：Frj 功能:通过key获取值 使用方法：
     */
    public static String getValueByString(String key)
    {
        SharedPreferences sharedPreferences = BtApplication.getApplication().getSharedPreferences(SHAREPREFERENCE_NAME,
                Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    /**
     * @param key key
     * @return
     * @author：Frj 功能:通过key获取值 使用方法：
     */
    public static int getValueByint(String key)
    {
        return getValueByint(key, 0);
    }

    /**
     * @param key key
     * @return
     * @author：Frj 功能:通过key获取值 使用方法：
     */
    public static int getValueByint(String key, int defaultValue)
    {
        SharedPreferences sharedPreferences = BtApplication.getApplication().getSharedPreferences(SHAREPREFERENCE_NAME,
                Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, defaultValue);
    }

    /**
     * 通过Key获取Boolean 值
     *
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public static boolean getValueByBoolean(String key, boolean defaultValue)
    {
        SharedPreferences sharedPreferences = BtApplication.getApplication().getSharedPreferences(SHAREPREFERENCE_NAME,
                Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    /**
     * @author：Frj 功能:注销登录 使用方法：
     */
    public static void logOutAccount()
    {
        SharedPreferences sharedPreferences = BtApplication.getApplication().getSharedPreferences(SHAREPREFERENCE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ACCOUNT, "");
        editor.putString(PASSWORD, "");
        editor.commit();
    }


    /**
     * 清空首选项
     */
    public static void clearSharePre()
    {
        SharedPreferences sharedPreferences = BtApplication.getApplication().getSharedPreferences(SHAREPREFERENCE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }


    /**
     * 获取用户名密码
     *
     * @return 如果未保存用户名和密码则返回null，如果已保存用户名和密码，返回数组，第一项为用户名，第二项为密码
     */
    public static String[] getAccount()
    {
        SharedPreferences sharedPreferences = BtApplication.getApplication().getSharedPreferences(SHAREPREFERENCE_NAME,
                Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(ACCOUNT, "");
        if (TextUtils.isEmpty(username))
        {
            return null;
        }
        String password = sharedPreferences.getString(PASSWORD, "");
        if (TextUtils.isEmpty(password))
        {
            return null;
        }
        return new String[]{username, password};
    }

    /**
     * 设置有系统消息
     */
    public static void setSystemMsg(boolean value)
    {
        SharePreferenceUtils.savePreferences("msg_system", value);
    }

    /**
     * 设置有公告消息
     */
    public static void setAnnoMsg(boolean value)
    {
        SharePreferenceUtils.savePreferences("msg_anno", value);
    }

    /**
     * 获取是否有系统消息
     *
     * @return true表示有
     */
    public static boolean getSystemMsg()
    {
        return SharePreferenceUtils.getValueByBoolean("msg_system", false);
    }

    /**
     * 获取是否有公告消息
     *
     * @return true表示有
     */
    public static boolean getAnnoMsg()
    {
        return SharePreferenceUtils.getValueByBoolean("msg_anno", false);
    }
}
