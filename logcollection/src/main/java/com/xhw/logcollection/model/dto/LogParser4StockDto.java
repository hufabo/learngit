package com.xhw.logcollection.model.dto;

import com.xhw.logcollection.job.entity.ContextBean;
import com.xhw.logcollection.util.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.util.*;

/**
 * @Author 孔纲
 * @Date 2018/3/13
 */
@XmlRootElement(name = "root")
public class LogParser4StockDto implements Serializable {

    private String oraType;

    private String oraXtlb;

    private String oraUser;

    private String oraClient;

    private String oraTime;

    private String oraSchema;

    private String tabName;

    private List<Map<String, String>> datas;

    /*
    以下属性为扩展属性
     */
    private boolean hasFinished = false; // 是否写入完成

    private int currentIndex = 0; // 当前索引

    private List<Map<String, Object>> segments; // 分段保存的信息

    @XmlElement(name = "ora_type")
    public String getOraType() {
        return oraType;
    }

    public void setOraType(String oraType) {
        this.oraType = oraType;
    }

    @XmlElement(name = "ora_xtlb")
    public String getOraXtlb() {
        return oraXtlb;
    }

    public void setOraXtlb(String oraXtlb) {
        this.oraXtlb = oraXtlb;
    }

    @XmlElement(name = "ora_user")
    public String getOraUser() {
        return oraUser;
    }

    public void setOraUser(String oraUser) {
        this.oraUser = oraUser;
    }

    @XmlElement(name = "ora_client")
    public String getOraClient() {
        return oraClient;
    }

    public void setOraClient(String oraClient) {
        this.oraClient = oraClient;
    }

    @XmlElement(name = "ora_time")
    public String getOraTime() {
        return oraTime;
    }

    public void setOraTime(String oraTime) {
        this.oraTime = oraTime;
    }

    @XmlElement(name = "ora_schema")
    public String getOraSchema() {
        return oraSchema;
    }

    public void setOraSchema(String oraSchema) {
        this.oraSchema = oraSchema;
    }

    @XmlElement(name = "tab_name")
    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    @XmlElementWrapper(name = "tab_datalist")
    @XmlElement(name = "tab_record")
    @XmlJavaTypeAdapter(MapAdapter.class)
    public List<Map<String, String>> getDatas() {
        return datas;
    }

    public void setDatas(List<Map<String, String>> datas) {
        this.datas = datas;
    }

    public boolean isHasFinished() {
        return hasFinished;
    }

    @XmlTransient
    public void setHasFinished(boolean hasFinished) {
        this.hasFinished = hasFinished;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    @XmlTransient
    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public List<Map<String, Object>> getSegments() {
        return segments;
    }

    @XmlTransient
    public void setSegments(List<Map<String, Object>> segments) {
        this.segments = segments;
    }

    public void write2File(String timeField) throws Exception {
        // Step 1. 创建JAXB上下文
        JAXBContext context = JAXBContext.newInstance();
        // Step 2. 创建Marshaller
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);

        // Step 3. 使用StAX写入开始标签和基本信息。
        XMLOutputFactory output = XMLOutputFactory.newInstance();
        output.setProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES, true);
        // 文件路径
        String fileDir = LoadGlobalPropertiesUtil.getProperty(Constant.FILE_PUT_STOCK_DIR_AWAIT);
        // 解析文件最大值 单位MB
        long maxSize = ContextBean.getBusCfgGlobalBean().getRzjxwjzdz() * 1024 * 1024;
        String sn = JedisUtil.getSN();
        String azdm = ContextBean.getBusCfgGlobalBean().getAzdm();
        String fileName = azdm + this.getOraXtlb() + "1" + DateUtil.dateNow2str("yyyyMMdd") + sn;
        File file = FileUtil.createFile(fileDir.concat(File.separator).concat(fileName));
        OutputStream out = null;
        XMLStreamWriter writer = null;
        try {
            out = new FileOutputStream(file);
            writer = output.createXMLStreamWriter(out, "UTF-8");
            writer.writeStartDocument();
            writer.writeStartElement("root");

            // 写属性节点
            writer.writeStartElement("ora_type");
            // writer.writeAttribute("attr", "123"); // 属性 <ora_type attr="123"> 建议紧接着start之后加
            writer.writeCharacters(StringUtil.NVLLIF(this.getOraType(),"")); // character 节点值不能为null,否则会空指针
            // writer.writeComment("456"); // 注解 <!--456-->
            writer.writeEndElement();
            writer.writeStartElement("ora_xtlb");
            writer.writeCharacters(StringUtil.NVLLIF(this.getOraXtlb(),""));
            writer.writeEndElement();
            writer.writeStartElement("ora_user");
            writer.writeCharacters(StringUtil.NVLLIF(this.getOraUser(),""));
            writer.writeEndElement();
            writer.writeStartElement("ora_client");
            writer.writeCharacters(StringUtil.NVLLIF(this.getOraClient(),""));
            writer.writeEndElement();
            writer.writeStartElement("ora_time");
            writer.writeCharacters(StringUtil.NVLLIF(this.getOraTime(),""));
            writer.writeEndElement();
            writer.writeStartElement("ora_schema");
            writer.writeCharacters(StringUtil.NVLLIF(this.getOraSchema(),""));
            writer.writeEndElement();
            writer.writeStartElement("tab_name");
            writer.writeCharacters(StringUtil.NVLLIF(this.getTabName(),""));
            writer.writeEndElement();
            writer.writeStartElement("tab_datalist");

            List<Map<String, String>> lst = this.getDatas();
            if (lst != null) {
                String beginTime = lst.get(this.currentIndex).get(timeField);
                String endTime = null;
                int count = 0;
                if(this.segments == null) this.segments = new ArrayList<>();
                for(int i = this.currentIndex; i<lst.size(); i++){
                    writer.writeStartElement("tab_record");
                    writer.writeAttribute("recnum", String.valueOf(count));
                    Map<String, String> item = lst.get(i);
                    Iterator<Map.Entry<String, String>> iterator = item.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<String, String> entry = iterator.next();
                        writer.writeStartElement(entry.getKey());
                        writer.writeCharacters(StringUtil.NVLLIF(entry.getValue(),""));
                        writer.writeEndElement();
                    }
                    writer.writeEndElement();
                    writer.flush();
                    long length = file.length();
                    // 状态记录
                    count++;
                    endTime = lst.get(i).get(timeField);
                    // 文件大小达到目标值，停止写入节点信息
                    // 临界空间设置为总空间的10%，比如设置100M最大值，文件大于90M的时候就停止写入文件，防止文件超过最大值
                    if(length > (maxSize * 0.9)){
                        Map<String, Object> segment = new HashMap<>();
                        segment.put("file",file);
                        segment.put("count", count);
                        segment.put("beginTime",beginTime);
                        segment.put("endTime",endTime);
                        this.segments.add(segment);
                        this.setHasFinished(false);
                        this.setCurrentIndex(i);
                        // 写入结束标签
                        writer.writeEndElement();
                        writer.writeEndElement();
                        writer.writeEndDocument();
                        writer.flush();
                        return;
                    }
                }
                this.setCurrentIndex(lst.size());
                Map<String, Object> segment = new HashMap<>();
                segment.put("file",file);
                segment.put("count", count);
                segment.put("beginTime",beginTime);
                segment.put("endTime",endTime);
                this.segments.add(segment);
            }
            this.setHasFinished(true);
            // 写入结束标签
            writer.writeEndElement();
            writer.writeEndElement();
            writer.writeEndDocument();
            writer.flush();
        } finally {
            writer.close();
            out.close();
        }

    }
}
