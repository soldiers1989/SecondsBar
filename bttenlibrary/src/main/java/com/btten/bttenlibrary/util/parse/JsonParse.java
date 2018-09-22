package com.btten.bttenlibrary.util.parse;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

/**
 * Json解析类
 * 
 * 使用的是：Gson解析
 * 
 * @author frj
 * 
 */
public class JsonParse {

	/**
	 * 将jJSONObject对象解析成指定类型对象
	 * 
	 * @param <T>
	 * 
	 * @param response
	 *            JsonObject对象
	 * @param clazz
	 *            返回的类型
	 * @return 返回解析JSONObject后的对象
	 * @throws JsonParseException
	 */
	public static <T> T JSONObjectToObject(JSONObject response, Class<?> clazz)
			throws JsonParseException {
		return (T) JSONObjectToObject(response.toString(), clazz);
	}

	/**
	 * 将json字符串解析成指定类型对象
	 * 
	 * @param json
	 *            json字符串
	 * @param clazz
	 *            返回的类型
	 * @return 返回解析json字符串后的对象
	 * @throws JsonParseException
	 *             解析失败
	 */
	public static <T> T JSONObjectToObject(String json, Class<?> clazz)
			throws JsonParseException {
		Gson gson = new Gson();
		return  (T) gson.fromJson(json, clazz);
	}

	/**
	 * 将json字符串解析成指定类型的集合对象
	 * 
	 * @param json
	 *            json字符串
	 * @return 返回解析json字符串后的集合对象
	 * @throws JsonParseException
	 *             解析失败
	 */
	public static <T> ArrayList<T> JSONArrayToArrayList(String json)
			throws JsonParseException {
		Gson gson = new Gson();
		return gson.fromJson(json, new TypeToken<ArrayList<T>>() {
		}.getType());
	}

	/**
	 * 将JSONArray对象解析成指定类型的集合对象
	 * 
	 * @param jsonArray
	 *            JSONArray对象
	 * @return 返回解析JSONArray对象后的集合对象
	 * @throws JsonParseException
	 *             解析失败
	 */
	public static <T> ArrayList<T> JSONArrayToArrayList(JSONArray jsonArray)
			throws JsonParseException {
		return JSONArrayToArrayList(jsonArray.toString());
	}
}
