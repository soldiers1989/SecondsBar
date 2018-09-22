package com.btten.bttenlibrary.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 功能：Json数据的工具类，用于操作Json
 */
public class JsonUtil {
	/**
	 * 
	 * @author：Frj 功能:用于处理json数据中出现变量名首字母非小写的情况，利用正则表达式匹配变量，然后把首字母变成小写
	 * @param json
	 * @return
	 */
	public static String dealWithFirstChar(String json) {
		String originalInput = json;
		StringBuilder sb = new StringBuilder(json);
		String regex = "\"(\\w+)\":";
		Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(sb);
		ArrayList<String> result = new ArrayList<String>();
		while (m.find()) {
			String valueName = m.group(1);
			String newValueName = null;
			char[] words = valueName.toCharArray();
			if (Character.isUpperCase(words[0]))// 首字母大写不符合变量命名规范
			{
				words[0] = Character.toLowerCase(words[0]);
				newValueName = new String(words);
				String regx1 = "\"" + valueName + "\":";
				String replace = "\"" + newValueName + "\":";
				originalInput = originalInput.replaceAll(regx1, replace);
			}
			result.add(valueName);
			sb.delete(0, m.end(0));
			m = p.matcher(sb);
		}
		return originalInput;
	}

	/**
	 * 
	 * @author：Frj 功能:用于处理json数据中出现变量名为Java关键字的问题，利用正则表达式匹配变量，然后将变量名更改为非关键字
	 * @param json
	 *            含有关键字的json 字符串
	 * @param keyword
	 *            要替换的关键字
	 * @param notKeyword
	 *            用来替换关键字的非关键字
	 * @return 没有关键字的json字符串
	 */
	public static String replaceKeyword(String json, String keyword, String notKeyword) {
		String originalInput = json;
		StringBuilder sb = new StringBuilder(json);
		String regex = "\"(\\w+)\":";
		Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(sb);
		while (m.find()) {
			String valueName = m.group(1);
			if (valueName.equalsIgnoreCase(keyword)) {
				String regx1 = "\"" + valueName + "\":";
				String replace = "\"" + notKeyword + "\":";
				originalInput = originalInput.replaceAll(regx1, replace);
			}
			sb.delete(0, m.end(0));
			m = p.matcher(sb);
		}
		return originalInput;
	}

	/**
	 * 
	 * @author：Frj 功能:Json数据括号自动补全 使用方法：
	 * @param json
	 *            不完整的Json数据（差括号的）
	 * @return
	 */
	public static String autoComplete(String json) {
		LinkedList<Character> stack = new LinkedList<Character>();
		String returnStr = "";
		char[] charArray = json.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			if (charArray[i] == '[' || charArray[i] == '{') // 入栈
			{
				stack.addFirst(charArray[i]);
			} else if (charArray[i] == ']') {
				// 判断是否闭合
				char last = stack.peekFirst();
				if (last != '[') // 不闭合 补
				{
					returnStr += "}";
				} else
				// 闭合
				{
					stack.pollFirst();
				}
			} else if (charArray[i] == '}') // 判断是否闭合
			{
				char last = stack.peekFirst();
				if (last != '{') // 不闭合 补
				{
					returnStr += "]";
				} else
				// 闭合
				{
					stack.pollFirst();
				}
			}
			returnStr += charArray[i];
		}
		return returnStr;
	}

	/**
	 * 
	 * @author：Frj 功能:将Unicode编码转换成汉字 使用方法：
	 * @param str
	 *            Unicode编码的字符串
	 * @return
	 */
	public static String UnicodeToString(String str) {
		Pattern p = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
		Matcher m = p.matcher(str);
		char ch;
		while (m.find()) {
			ch = (char) Integer.parseInt(m.group(2), 16);
			str = str.replace(m.group(1), ch + "");
		}
		return str;
	}

	/**
	 * 
	 * @author：Frj 功能:将汉字转换成Unicode编码 使用方法：
	 * @param str
	 *            字符串
	 * @return
	 */
	public static String toUnicode(String str) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) <= 256) {
				sb.append("\\u00");
			} else {
				sb.append("\\u");
			}
			sb.append(Integer.toHexString(str.charAt(i)).toUpperCase());
		}
		return sb.toString();
	}
}
