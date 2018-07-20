package com.xhw.logcollection.util;

import com.xhw.logcollection.model.dto.LogParser4StockDto;
import org.dom4j.Document;
import org.dom4j.Element;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlSchema;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.util.*;

/**
 * xml转换工具类
 * @Author 孔纲
 * @Date 2018/3/8
 */
public class XmlUtil {

    public static void main(String[] args) {
        LogParser4StockDto stockDto = new LogParser4StockDto();
        List<Map<String,String>> datas = new ArrayList<>();
        Map<String, String> data = new HashMap<>();
        data.put("name","langang");
        data.put("age","26");
        datas.add(data);
        Map<String, String> data2 = new HashMap<>();
        data2.put("name","jianghugang");
        data2.put("age","27");
        data2.put("id","007");
        datas.add(data2);
        stockDto.setDatas(datas);
        stockDto.setTabName("test-table");
        String xml = null;
        try {
            xml = XmlUtil.beanToXml(stockDto, LogParser4StockDto.class);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        System.out.println(xml);
    }

    /**
     * 对象转xml
     * @author konggang
     * @date 2018/3/8 17:32
     */
    public static String beanToXml(Object obj, Class<?> load) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(load);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        // 去掉生成xml的默认报文头
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        StringWriter writer = new StringWriter();
        marshaller.marshal(obj,writer);
        return writer.toString();
    }

    /**
     * xml转对象
     * @author konggang
     * @date 2018/3/8 17:32
     */
    public static Object xmlToBean(String xmlPath,Class<?> load) throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(load);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Object object = unmarshaller.unmarshal(new StringReader(xmlPath));
        return object;
    }

    /*
    xml写入文件
     */
    public static void write2File(String xml, String filePath) throws IOException {
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if(!parentDir.exists()){
            parentDir.mkdirs();
        }
        try {
            RandomAccessFile rf = new RandomAccessFile(filePath, "rw");
            rf.writeBytes(xml);
            rf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Object> Dom2Map(Document doc){
        Map<String, Object> map = new HashMap<String, Object>();
        if(doc == null)
            return map;
        Element root = doc.getRootElement();
        for (Iterator iterator = root.elementIterator(); iterator.hasNext();) {
            Element e = (Element) iterator.next();
            List list = e.elements();
            if(list.size() > 0){
                map.put(e.getName(), Dom2Map(e));
            }else
                map.put(e.getName(), e.getText());
        }
        return map;
    }

    public static Map Dom2Map(Element e){
        Map map = new HashMap();
        List list = e.elements();
        if(list.size() > 0){
            for (int i = 0;i < list.size(); i++) {
                Element iter = (Element) list.get(i);
                List mapList = new ArrayList();

                if(iter.elements().size() > 0){
                    Map m = Dom2Map(iter);
                    if(map.get(iter.getName()) != null){
                        Object obj = map.get(iter.getName());
                        if(!obj.getClass().getName().equals("java.util.ArrayList")){
                            mapList = new ArrayList();
                            mapList.add(obj);
                            mapList.add(m);
                        }
                        if(obj.getClass().getName().equals("java.util.ArrayList")){
                            mapList = (List) obj;
                            mapList.add(m);
                        }
                        map.put(iter.getName(), mapList);
                    }else
                        map.put(iter.getName(), m);
                }
                else{
                    if(map.get(iter.getName()) != null){
                        Object obj = map.get(iter.getName());
                        if(!obj.getClass().getName().equals("java.util.ArrayList")){
                            mapList = new ArrayList();
                            mapList.add(obj);
                            mapList.add(iter.getText());
                        }
                        if(obj.getClass().getName().equals("java.util.ArrayList")){
                            mapList = (List) obj;
                            mapList.add(iter.getText());
                        }
                        map.put(iter.getName(), mapList);
                    }else
                        map.put(iter.getName(), iter.getText());
                }
            }
        }else
            map.put(e.getName(), e.getText());
        return map;
    }
}
