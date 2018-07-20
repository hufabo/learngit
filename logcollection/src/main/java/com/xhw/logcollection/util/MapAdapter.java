package com.xhw.logcollection.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.*;

/**
 * JAXB XML适配器
 * @Author 孔纲
 * @Date 2018/3/20
 */
public class MapAdapter extends XmlAdapter<Object, Map<String, Object>> {

    /**
     * MAP转换为xml对象
     */
    @Override
    public Object marshal(Map<String, Object> data) throws Exception {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = builder.newDocument();
        Element rowEle = document.createElement("datas");
        Iterator<Map.Entry<String, Object>> itera = data.entrySet().iterator();
        while (itera.hasNext()) {
            Map.Entry<String, Object> entry = itera.next();
            String key = entry.getKey();
            // TODO 这里的字符串转换，简单处理的，对于Date等特殊类型的数据还需要进一步处理
            String value = String.valueOf(entry.getValue());
            if (key == null || key.equals("")) {
                continue;
            }
            if (value == null) {
                value = "";
            }
            Element detailEle = document.createElement(key);
            detailEle.setTextContent(value);
            rowEle.appendChild(detailEle);
        }
        document.appendChild(rowEle);
        return rowEle;
    }

    /**
     * 把XML转化成MAP
     */
    @Override
    public Map<String, Object> unmarshal(Object data) throws Exception {
        if(data == null){
            return null;
        }
        Node rowNode = (Element) data;
        NodeList detailList = rowNode.getChildNodes();
        int detailCount = detailList.getLength();
        Map<String, Object> detailMap = new HashMap<>();
        for(int j = 0; j < detailCount; j++){
            Node detailNode = detailList.item(j);
            String key = detailNode.getNodeName();
            String value = detailNode.getTextContent();
            if(key == null || "".equals(key)){
                continue;
            }
            detailMap.put(key, value);
        }
        return detailMap;
    }

}
