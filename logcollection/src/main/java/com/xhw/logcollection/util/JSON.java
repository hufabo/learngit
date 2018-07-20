package com.xhw.logcollection.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * json异常处理级格式转换
 * @author wanghaiyang
 * @Time 20107.08.25 16:21
 */
public class JSON {
	/**
	 * "There is a cycle in the hierarchy"异常处理
	 */
	public static String jsonArrayError(Object o){
		JsonConfig jsonConfig = new JsonConfig();

		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);

		JSONArray json =JSONArray.fromObject(o, jsonConfig);

		return  json.toString();
	}
	public static String jsonObjectError(Object o){
		JsonConfig jsonConfig = new JsonConfig();

		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);

		JSONObject json =JSONObject.fromObject(o, jsonConfig);

		return  json.toString();
	}
	/**
	 * 将ajax传递的json数据提取解析
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	public static String foundParam(HttpServletRequest request) throws Exception{
		JSONObject json=null;
		 BufferedReader br = new BufferedReader(new InputStreamReader(  
                 (ServletInputStream) request.getInputStream(), "utf-8"));
         StringBuffer sb = new StringBuffer("");  
         String temp;
         while ((temp = br.readLine()) != null) {  
             sb.append(temp);  
         }  
         br.close();  
         String acceptjson = sb.toString();  
		return acceptjson;
	}

	/**
	 * 数据处理，将时间转换成字符串
	 * @Author:wanghaiyang
	 * @Date: 2018/2/9 16:30
	 * @params  data
	 * @Modified by:
	 */
	public static <T> JSONArray dataTrans(List<T> data){
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor());
		JSONArray dataArr = JSONArray.fromObject(data,jsonConfig);
		return dataArr;
	}
}
