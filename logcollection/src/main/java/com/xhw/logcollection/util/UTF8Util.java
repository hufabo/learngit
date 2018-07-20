package com.xhw.logcollection.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Description:webservice编码统一UTF-8格式
 * @author wanghaiyang
 * @version: 1.0
 * @Create Date Time: 2017.12.13 9:23
 * @note
 *    修改日期      修改人    修改内容
 *    -----------------------------------------------
 *
*/
public class UTF8Util {

	
	/**
	 * 将中文字符转换为utf-8格式
	 * @param xmlDoc
	 * @return
	 */
	public static String encodeUTF8(String xmlDoc){
		String str = "";
		try {
			str = URLEncoder.encode(xmlDoc, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * 将utf-8格式转换为中文字符
	 * @param str
	 * @return
	 */
	public static String decodeUTF8(String str){
		String xmlDoc = "";
		try {
			xmlDoc = URLDecoder.decode(str, "utf-8");
		} catch (Exception ex) {
			xmlDoc = ex.toString();
		}
		return xmlDoc;
	}
	
	
}
