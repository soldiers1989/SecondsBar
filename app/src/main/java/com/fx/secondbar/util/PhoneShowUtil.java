package com.fx.secondbar.util;

import android.text.TextUtils;

/**
 * function:手机号中间四位替换为*
 * author: frj
 * modify date: 2018/10/28
 */
public class PhoneShowUtil
{

    /**
     * 处理手机号字符串，将中间四位变为*号
     *
     * @param phone
     * @return
     */
    public static String handlerPhoneStr(String phone)
    {
        if (TextUtils.isEmpty(phone))
        {
            return "";
        }
        if (phone.length() != 11)
        {
            return phone;
        }
        char[] chars = phone.toCharArray();
        chars[3] = '*';
        chars[4] = '*';
        chars[5] = '*';
        chars[6] = '*';
        return new String(chars);
    }
}
