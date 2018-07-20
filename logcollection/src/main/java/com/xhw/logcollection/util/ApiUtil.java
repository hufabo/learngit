package com.xhw.logcollection.util;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.poi.ss.formula.functions.T;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 接口参数及返回值操作工具类
 *
 * @Author rentie
 * @Date 2017/10/16 8:25
 */

public class ApiUtil {

	/**
	 * API json参数解析
	 *
	 * @param data      json字符串
	 * @param classType json字符串需要解析成的对应类的类类型,
	 *                  查询时传HashMap.class，
	 *                  其他情况传对应实体类的类类型
	 * @Auther rentie
	 * @Date 2017/10/16 8:29
	 */
	public static Object apiParamAnalysis(String data, Class classType) throws JSONException {
		Gson gson = new GsonBuilder().serializeNulls().create();
		JSONObject jsonObject = null;
		Object object = null;
		String str = data;

		try {
			jsonObject = JSONObject.fromObject(str);
		} catch (JSONException jsonException) {
			throw jsonException;
		} catch (Exception e) {
			str = StringUtil.strHandler(str);
			jsonObject = JSONObject.fromObject(str);
		}

		if(!StringUtil.isEmpty(jsonObject.get("data").toString())){
			 str = Base64Util.decode(jsonObject.get("data").toString());
		}

		object = gson.fromJson(str, classType);
		return object;
	}

	/**
	 * API json参数解析(不加密数据)
	 *
	 * @param data      json字符串
	 * @param classType json字符串需要解析成的对应类的类类型,
	 *                  查询时传HashMap.class，
	 *                  其他情况传对应实体类的类类型
	 * @Auther rentie
	 * @Date 2017/10/16 8:29
	 */
	public static Object apiParamUnencrypted(String data, Class classType) throws JSONException {
		Gson gson = new GsonBuilder().serializeNulls().create();
		JSONObject jsonObject = null;
		Object object = null;
		String str = data;

		try {
			jsonObject = JSONObject.fromObject(str);
		} catch (JSONException jsonException) {
			throw jsonException;
		} catch (Exception e) {
			str = StringUtil.strHandler(str);
			jsonObject = JSONObject.fromObject(str);
		}

		if(!StringUtil.isEmpty(jsonObject.get("data").toString())){
			str = jsonObject.get("data").toString();
			object = gson.fromJson(str, classType);
			return object;
		}else {
			return object;
		}

	}
	/**
	 * API 返回结果封装（base64加密）
	 *
	 * @param pageInfo PageInfo对象
	 * @Auther rentie
	 * @Date 2017/10/16 9:34
	 */
	public static String apiParamEncapsulation(PageInfo<T> pageInfo) throws Exception {

		JSONObject jsonObject = new JSONObject();
		Gson gson = new GsonBuilder().serializeNulls().create();

		if (pageInfo.getList() != null && pageInfo.getList().size() > 0) {
			// 处理时间转json的问题
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor());
			JSONArray jsonArr = JSONArray.fromObject(pageInfo.getList(), jsonConfig);

			jsonObject.put("total", pageInfo.getTotal());
			jsonObject.put("rows", jsonArr);
		} else {
			jsonObject.put("total", 0);
			jsonObject.put("rows", "");
		}
		return Base64Util.encode(gson.toJson(jsonObject));
	}

	/**
	 * API 返回结果封装（base64加密）
	 *
	 * @param list 如果service结果为List
	 * @Auther rentie
	 * @Date 2017/10/16 9:34
	 */
	public static String apiParamEncapsulation(List<T> list) throws Exception {

		JSONObject jsonObject = new JSONObject();
		Gson gson = new GsonBuilder().serializeNulls().create();
		if (list != null && list.size() > 0) {
			// 处理时间转json的问题
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor());
			JSONArray jsonArr = JSONArray.fromObject(list, jsonConfig);

			jsonObject.put("total", list.size());
			jsonObject.put("rows", jsonArr);
		} else {
			jsonObject.put("total", 0);
			jsonObject.put("rows", "");
		}
		return Base64Util.encode(gson.toJson(jsonObject));
	}

	/**
	 * API 返回结果封装（base64加密）
	 *
	 * @param object 如果service结果为单个对象，则传入单个对象 ，其他参数为空
	 * @Auther rentie
	 * @Date 2017/10/16 9:34
	 */
	public static String apiParamEncapsulation(Object object) throws Exception {

		JSONObject jsonObject = new JSONObject();
		Gson gson = new GsonBuilder().serializeNulls().create();

		if (object != null) {
			// 处理时间转json的问题
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor());
			JSONArray jsonArr = JSONArray.fromObject(object, jsonConfig);

			jsonObject.put("total", 1);
			jsonObject.put("rows", jsonArr);
		} else {
			jsonObject.put("total", 0);
			jsonObject.put("rows", "");
		}
		return Base64Util.encode(gson.toJson(jsonObject));


	}

	/**
	 * API 返回结果封装（不加密）
	 *
	 * @param pageInfo PageInfo对象
	 * @Auther rentie
	 * @Date 2017/10/16 9:34
	 */
	public static String apiResultUnencrypted(PageInfo<T> pageInfo) throws Exception {

		JSONObject jsonObject = new JSONObject();
		Gson gson = new GsonBuilder().serializeNulls().create();

		if (pageInfo.getList() != null && pageInfo.getList().size() > 0) {
			// 处理时间转json的问题
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor());
			JSONArray jsonArr = JSONArray.fromObject(pageInfo.getList(), jsonConfig);

			jsonObject.put("total", pageInfo.getTotal());
			jsonObject.put("rows", jsonArr);
		} else {
			jsonObject.put("total", 0);
			jsonObject.put("rows", "");
		}
		return gson.toJson(jsonObject);
	}

	/**
	 * API 返回结果封装（不加密）
	 *
	 * @param list 如果service结果为List
	 * @Auther rentie
	 * @Date 2017/10/16 9:34
	 */
	public static String apiResultUnencrypted(List<T> list) throws Exception {

		JSONObject jsonObject = new JSONObject();
		Gson gson = new GsonBuilder().serializeNulls().create();
		if (list != null && list.size() > 0) {
			// 处理时间转json的问题
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor());
			JSONArray jsonArr = JSONArray.fromObject(list, jsonConfig);

			jsonObject.put("total", list.size());
			jsonObject.put("rows", jsonArr);
		} else {
			jsonObject.put("total", 0);
			jsonObject.put("rows", "");
		}
		return gson.toJson(jsonObject);
	}

	/**
	 * API 返回结果封装（不加密）
	 *
	 * @param object 如果service结果为单个对象，则传入单个对象 ，其他参数为空
	 * @Auther rentie
	 * @Date 2017/10/16 9:34
	 */
	public static String apiResultUnencrypted(Object object) throws Exception {

		JSONObject jsonObject = new JSONObject();
		Gson gson = new GsonBuilder().serializeNulls().create();

		if (object != null) {
			// 处理时间转json的问题
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor());
			JSONArray jsonArr = JSONArray.fromObject(object, jsonConfig);

			jsonObject.put("total", 1);
			jsonObject.put("rows", jsonArr);
		} else {
			jsonObject.put("total", 0);
			jsonObject.put("rows", "");
		}
		return gson.toJson(jsonObject);


	}

}
