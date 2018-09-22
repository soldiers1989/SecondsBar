package com.btten.bttenlibrary.util;

import android.util.Base64;

/**
 * Base64 字符串工具类
 * 
 */
public class Base64Utils {

	/**
	 * 
	 * 将字节转换成字符串（Base64） 功能: 使用方法：
	 * 
	 * @param bytes
	 *            字节数据
	 * @return Base64字符串
	 */
	public static String byteToString(byte[] bytes) {
		
		/*
		 * 使用Base64.NO_WRAP 类型可以很好的和OBject-C（IOS）兼容
		 */
		return Base64.encodeToString(bytes, Base64.NO_WRAP);
	}

	/**
	 * 功能:将Base64 字符串转换为字节数组 使用方法：
	 * 
	 *            Base64字符串
	 * @return 字节数组
	 */
	public static byte[] StringToByte(String baseStr) {
		
		/*
		 * 使用Base64.NO_WRAP 类型可以很好的和OBject-C（IOS）兼容
		 */
		return Base64.decode(baseStr, Base64.NO_WRAP);
	}
}
