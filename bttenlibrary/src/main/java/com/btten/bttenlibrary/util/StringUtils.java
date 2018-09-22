package com.btten.bttenlibrary.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 负责处理与字符串相关的转换等工作
 *
 */
public class StringUtils {

	// 常用的字符编码格式
	public final static String ENCODE_UTF_8 = "UTF-8";
	public final static String ENCODE_GBK = "GBK";
	public final static String ENCODE_ISO_8859 = "ISO-8859-1";

	/**
	 * 将一个字节转换成16进制字符串（2个字节）
	 * 
	 * @param b
	 * @return
	 */
	private static String toHexString(byte b) {
		char[] buf = new char[2];
		char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };

		buf[1] = digits[b & 0x0F];
		b >>>= 4;
		buf[0] = digits[b & 0x0F];

		return new String(buf);
	}

	/**
	 * 将一个字节流数组转换成16进制字符串
	 * 
	 * @param buf
	 * @return
	 */
	public static String getHexString(byte[] buf) {
		if (buf == null) {
			return "null";
		}

		StringBuffer buff = new StringBuffer();

		for (int i = 0; i < buf.length; i++) {
			if (i % 16 == 0) {
				buff.append('\n');
			}
			buff.append(toHexString(buf[i]) + " ");
		}
		buff.append('\n');
		return buff.toString();
	}

	/**
	 * 将Unicode编码的字符串转化为Native编码的字符
	 * 
	 * @param str
	 * @return String
	 * @roseuid 3F8644F30222
	 */
	public static String unicode2Native(String str) {
		return null;
	}

	/**
	 * 将Native编码的字符串转化为Unicode编码的字符(编码格式为：utf-8)
	 * 
	 * @param str
	 * @return String
	 */
	public static String native2Unicode(String str) {
		return native2Unicode(str, ENCODE_UTF_8);
	}

	/**
	 * 将Native编码的字符串转化为Unicode编码的字符
	 * 
	 * @param str
	 *            Native 字符串
	 * @param encode
	 *            编码格式；如果格式无法被解析，将默认使用“UTF-8”。
	 * @return
	 */
	public static String native2Unicode(String str, String encode) {
		try {
			byte[] buffer = new byte[str.length() * 2];
			int j = 0;
			for (int i = 0; i < str.length(); i++) {
				if (str.charAt(i) >= 0x100) {
					char c = str.charAt(i);

					byte[] buf = ("" + c).getBytes();
					buffer[j++] = buf[0];
					buffer[j++] = buf[1];
				} else {
					buffer[j++] = (byte) str.charAt(i);
				}
			}
			return new String(buffer, 0, j);
		} catch (Exception e) {
			byte[] b = str.getBytes();
			try {
				try {
					return new String(b, encode);
				} catch (Exception e1) {
					e1.printStackTrace();
					return new String(b, ENCODE_UTF_8);
				}
			} catch (Exception e1) {
				return "";
			}
		}
	}

	/**
	 * 将字节流数组转换成二进制显示形式的字符串
	 * 
	 * @param buf
	 * @return
	 */
	public static String getBinaryStr(byte[] buf) {
		if (buf == null) {
			return "null";
		}

		StringBuffer buff = new StringBuffer();
		int k = 0;
		for (int i = 0; i < buf.length; i++) {
			short t = buf[i];
			if (t < 0) {
				t += 256;
			}
			if (t < 16) {
				buff.append("0" + Integer.toString(t, 16) + " ");
			} else {
				buff.append(Integer.toString(t, 16) + " ");
			}
			k++;
			if (k == 16) {
				k = 0;
				buff.append("\n");
			}
		}
		buff.append("\n");
		return buff.toString();
	}

	/**
	 * 转换UTF-8编码的中文字符
	 * 
	 * @param srcStr
	 *            UTF-8编码字符串
	 * @return
	 * @throws Exception
	 */
	public static String decodeStrfrmUTF8(String srcStr) throws Exception {
		return URLDecoder.decode(srcStr, ENCODE_UTF_8);
	}

	/**
	 * 将含有中文字符串编码为UTF-8格式
	 * 
	 * @param srcStr
	 *            未编码字符串
	 * @return
	 * @throws Exception
	 */
	public static String encodeStrtoUTF8(String srcStr) throws Exception {
		return URLEncoder.encode(srcStr, ENCODE_UTF_8);
	}

	/**
	 * 
	 * 功能:截取字符串
	 * 
	 * @param str
	 *            要截取的字符串
	 * @param endStr
	 *            表示到该字符串位置为止
	 * @return
	 */
	public static String subString(String str, String endStr) {
		return str.substring(0, str.indexOf(endStr));
	}

	/**
	 * 
	 * 功能:判断是否存在汉字 使用方法：
	 * 
	 * @param str
	 *            需要判断的字符串
	 * @return 如果存在返回true
	 */
	public static boolean isExistHanzi(String str) {
		Pattern p;
		Matcher m;
		p = Pattern.compile("[\u4e00-\u9fa5]");
		m = p.matcher(str);
		if (m.find()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * 功能:判断是否存在汉字 使用方法：
	 * 
	 * @param c
	 *            需要判断的字符串
	 * @return 如果存在返回true
	 */
	public static boolean isExistHanzi(char c) {
		Pattern p;
		Matcher m;
		char[] chars = { c };
		String str = new String(chars);
		p = Pattern.compile("[\u4e00-\u9fa5]");
		m = p.matcher(str);
		if (m.find()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * 功能:判断是否存在字母 使用方法：
	 * 
	 * @param str
	 *            需要判断的字符串
	 * @return 如果存在返回true
	 */
	public static boolean isExistLetter(String str) {
		Pattern p;
		Matcher m;
		p = Pattern.compile("[a-zA-Z]");
		m = p.matcher(str);
		if (m.find()) {
			return true;
		} else {
			return false;
		}
	}
}
