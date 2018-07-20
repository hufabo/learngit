package com.xhw.logcollection.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xhw.logcollection.job.entity.BusCfgTask;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.poi.ss.formula.functions.T;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.xml.namespace.QName;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Description:web service基类
 * @author wanghaiyang
 * @version: 1.0
 * @Create Date Time: 2018.03.08 11:38
 * @note
 *    修改日期      修改人    修改内容
 *    -----------------------------------------------
 *
*/
public class WSUtil {

	/**
	 * 静态初始化相应的配置
	 */
	static{
		Properties properties = new Properties();
		try {
			properties.load(WSUtil.class.getResourceAsStream("/application.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		address = properties.getProperty("address");
		xtlb = properties.getProperty("xtlb");
		jkxlh = properties.getProperty("jkxlh");
		targetNamespace = properties.getProperty("targetNamespace");
	}

	/**
	 * web service 地址
	 */
	private static String address;
	/**
	 * 系统类别
	 */
	private static String xtlb;
	/**
	 * 接口序列号
	 */
	private static String jkxlh;

	/**
	 * 目标命名空间
	 */
	private static String targetNamespace;

	/**
	 *
	 * @Author wanghaiyang
	 * @param  jkid 接口标识
	 * @param  localPart 调用方法名
	 * @param  xmlDoc 数据
	 * @Date 2018/3/9 10:36
	 */
	public Map<String,String> sync(String jkid,String localPart,String xmlDoc){
		// TODO 接口调通后 返回真实数据
		// String rst = this.commonXml(jkid,xmlDoc,localPart);

		// TODO 为了方便测试 这里将请求的报文保存到临时目录
		try {
			String tempDir = LoadGlobalPropertiesUtil.getProperty("file_put.dir.temp");
			XmlUtil.write2File(xmlDoc, tempDir.concat("/request/").concat(jkid).concat(".xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		String rst =
				"<root>\n" +
				"\t<head>\n" +
				"\t\t<code>1</code>\n" +
				"\t\t<message>数据保存成功</message>\n" +
				"\t\t<keystr>wqerwrweqrwerwq</keystr>\n" +
				"\t</head>\n" +
				"</root>";
		Map<String,String> rstMap = this.parseResultXml(rst);
		return rstMap;
	}

	/**
	 * Description:
	 * @author shenzucai
	 * @time 2017.12.19 14:06
	 * @param jkid 接口标识
	 * @param xmlDoc xml参数
	 * @param localPart 调用的service方法名
	 * @return java.lang.String
	 * @throws Exception
	 * @note
	 */
	public String commonXml(String jkid, String xmlDoc, String localPart){
		// TODO 为了方便测试 这里将请求的报文保存到临时目录
		try {
			String tempDir = LoadGlobalPropertiesUtil.getProperty("file_put.dir.temp");
			XmlUtil.write2File(xmlDoc, tempDir.concat("/request/").concat(jkid).concat(".xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		String result = null;
		// TODO 待调试接口
//		try {
//			RPCServiceClient client = new RPCServiceClient();
//			Options options = client.getOptions();
//			EndpointReference targetErf = new EndpointReference(address);
//			options.setTo(targetErf);
//			options.setTimeOutInMilliSeconds(120000);
//
//			options.setManageSession(true);
//			options.setProperty(HTTPConstants.REUSE_HTTP_CLIENT, true);
//
//			options.setAction(localPart);
//			if(!xmlDoc.startsWith("<?xml ")){
//				xmlDoc="<?xml version='1.0' encoding='GBK'?>"+xmlDoc;
//			}
//			QName opAddEntry = new QName(targetNamespace, localPart);
//			Object[] opAddEntryArgs = new Object[] { xtlb, jkxlh, jkid, UTF8Util.encodeUTF8(xmlDoc)};
//			Class[] classes = new Class[]{String.class};
//			result = (String) client.invokeBlocking(opAddEntry, opAddEntryArgs, classes)[0];
//			result = UTF8Util.decodeUTF8(result);
//			client.cleanupTransport();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return result;
	}

	public String toXml(List dataList, String dataNodeName){
		StringBuilder xml = new StringBuilder("<root>");
		JSONArray arr = JSONArray.fromObject(dataList);
		for (int i = 0;i<arr.size();i++){
			xml.append("<"+dataNodeName+">");
			JSONObject obj = arr.getJSONObject(i);
			Iterator iterator = obj.keys();
			while (iterator.hasNext()){
				String node = iterator.next().toString();
				xml.append("<"+node+">");
				xml.append(obj.getString(node));
				xml.append("</"+node+">");
			}
			xml.append("</"+dataNodeName+">");
		}
		xml.append("</root>");
		return xml.toString();
	}

	/**
	 * xml结果转换
	 * @Author wanghaiyang
	 * @param  xmlDoc
	 * @Date 2018/3/8 15:19
	 */
	public Map<String,String> parseResultXml(String xmlDoc){
		Map<String, String> m_result=new HashMap<String, String>();
		try {
			Document doc= DocumentHelper.parseText(xmlDoc);
			Element root= doc.getRootElement();
			Element head=root.element("head");
			List<Element> q_elements=head.elements();
			for (int i = 0; i < q_elements.size(); i++) {
				Element item=q_elements.get(i);
				String name=item.getName();
				String value=item.getText();
				m_result.put(name, value);
			}
			if(root.element("body")!=null){
				Iterator<Element> violations=root.element("body").elementIterator();
				while(violations.hasNext()){
					Element elment=violations.next();
					List<Element> items=elment.elements();
					for (int i = 0; i < items.size(); i++) {
						Element item=items.get(i);
						String name=item.getName();
						String value=item.getText();
						m_result.put(name, value);
						System.out.println("name="+name+"\t value="+value);
					}
				}
			}

		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return m_result;
	}

}
