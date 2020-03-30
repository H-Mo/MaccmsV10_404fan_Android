package com.a404fan.video.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author linmo
 * JSON工具类
 */
public class JsonUtil {


	/**
	 * 将JSON解析成JavaBean
	 * @param json 
	 * @param clz
	 * @return
	 */
	public static <T> T parseObject(String json, Class<T> clz){
		if(clz == String.class){
			return (T)json;
		}
		Gson gson = new Gson();
		T bean = gson.fromJson(json, clz);
		return bean;
	}

	/**
	 * 解析成集合，捕捉报错，错误时返回空集合
	 * @param json
	 * @param clz
	 * @param <T>
     * @return
     */
	public static <T> List<T> parseList(String json, Class<T> clz){
		if(TextUtils.isEmpty(json)){
			return new ArrayList<T>();
		}
		if(!json.startsWith("[") && !json.endsWith("]")){
			return new ArrayList<T>();
		}
		try {
			List<T> lst =  new ArrayList<T>();
			JsonArray array = new JsonParser().parse(json).getAsJsonArray();
			for(final JsonElement elem : array){
				lst.add(new Gson().fromJson(elem, clz));
			}
			return lst;
		}catch (Exception e){
			e.printStackTrace();
		}
		return new ArrayList<T>();

	}


}
