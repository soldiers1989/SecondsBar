/**
 * <Title:Md5.java>
 * <Description:对字符串Md5加密>
 * <Company: >
 * <Author:Frj>
 * <Mender:2014-4-17>
 * <Version:1.0>
 */
package com.btten.bttenlibrary.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 功能：对字符串Md5加密
 */
public class Md5 {
	/**
	 * 
	 * 功能:对字符串进行Md5加密 使用方法：
	 * 
	 * @param val
	 *            要加密的字符串
	 * @return 32位长度的Md5字符串
	 * @throws NoSuchAlgorithmException
	 */
	public static String getMD5(String val) throws NoSuchAlgorithmException {
		// 返回字符串
		String md5Str = null;
		StringBuffer buf = new StringBuffer();
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(val.getBytes());
		byte[] b = md5.digest();// 加密

		int i;
		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if (i < 0) {
				i += 256;
			}
			if (i < 16) {
				buf.append("0");
			}
			// 将整型 十进制 i 转换为16位，用十六进制参数表示的无符号整数值的字符串表示形式。
			buf.append(Integer.toHexString(i));
		}
		// 32位的加密
		md5Str = buf.toString();
		// // 16位的加密
		// md5Str = buf.toString().substring(8, 24);
		return md5Str;
	}

	/**
	 * 
	 * 功能:对字符串进行Md5加密 使用方法：
	 * 
	 * @param val
	 *            要加密的字符串
	 * @return 16位长度的Md5字符串
	 * @throws NoSuchAlgorithmException
	 */
	public static String getMD5By16(String val) throws NoSuchAlgorithmException {
		String md5Str = getMD5(val);
		return md5Str.substring(8, 24);
	}
}
