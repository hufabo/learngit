package com.xhw.logcollection.model.dto;

import com.xhw.logcollection.analyze.sqlparse.StatementVisitorImpl;
import com.xhw.logcollection.job.entity.ContextBean;
import com.xhw.logcollection.monitor.entity.BusIncrementBreakPointInfo;
import com.xhw.logcollection.monitor.entity.BusIncrementFileInfo;
import com.xhw.logcollection.monitor.mapper.BusIncrementBreakPointInfoMapper;
import com.xhw.logcollection.monitor.mapper.BusIncrementFileInfoMapper;
import com.xhw.logcollection.util.*;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import org.apache.commons.codec.digest.DigestUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 增量日志xml转换
 * @version 0.0.1
 * @Author wanghaiyang
 * @Description
 * @Date 2018/3/29 14:00
 * @Modified By:
 */
@XmlRootElement(name = "root")
public class LogParser4IncreDto implements Serializable {

    private String oraType;

    private String oraXtlb;

    private String oraTransaction;

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

    public String getOraTransaction() {
        return oraTransaction;
    }

    public void setOraTransaction(String oraTransaction) {
        this.oraTransaction = oraTransaction;
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

    public void setSegments(List<Map<String, Object>> segments) {
        this.segments = segments;
    }

    public void write2File(List<IncrementDataDto> dataDtoList, BusIncrementBreakPointInfoMapper incrementBreakPointInfoMapper, BusIncrementFileInfoMapper fileInfoMapper) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        CCJSqlParserManager parser = new CCJSqlParserManager();
        //解析结果
        ResultParse resultParse = new ResultParse();
        int update = 0;
        int delete = 0;
        int insert = 0;
        int scnq = 0;
        int scnz = 0;
        int seqq = 0;
        int seqz = 0;

        // Step 1. 创建JAXB上下文
        JAXBContext context = JAXBContext.newInstance(LogParser4IncreDto.class);
        // Step 2. 创建Marshaller
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
        // Step 3. 使用StAX写入开始标签和基本信息。
        XMLOutputFactory output = XMLOutputFactory.newInstance();
        output.setProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES, true);
        // 文件路径
        String fileDir = LoadGlobalPropertiesUtil.getProperty(Constant.FILE_PUT_INCR_DIR_AWAIT);
        // 解析文件最大值 单位MB
        long maxSize = ContextBean.getBusCfgGlobalBean().getRzjxwjzdz() * 1024 * 1024;
        String sn = JedisUtil.getSN();
        String azdm = ContextBean.getBusCfgGlobalBean().getAzdm();
        String fileName = azdm + this.getOraXtlb() + "2" + DateUtil.dateNow2str("yyyyMMdd") + sn;
        File file = FileUtil.createFile(fileDir.concat(File.separator).concat(fileName));
        OutputStream out = new FileOutputStream(file);
        XMLStreamWriter writer = output.createXMLStreamWriter(out, "UTF-8");
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

        if (dataDtoList != null && dataDtoList.size()>0) {
            String beginTime = sdf.format(new Date());
            String endTime = null;
            int count = 0;
            if(this.segments == null) this.segments = new ArrayList<>();
            scnq = Integer.valueOf(dataDtoList.get(this.currentIndex).getScn());
            seqq = Integer.valueOf(dataDtoList.get(this.currentIndex).getSequence());
            for(int i = this.currentIndex; i<dataDtoList.size(); i++){
                IncrementDataDto dto = dataDtoList.get(i);
                writer.writeStartElement("ora_transaction");
                writer.writeAttribute("sessionid",dto.getSession());
                writer.writeAttribute("serial",dto.getSerial());

                writer.writeStartElement("ora_user");
                writer.writeCharacters(StringUtil.NVLLIF(dto.getUserName(),""));
                writer.writeEndElement();
                writer.writeStartElement("ora_client");
                writer.writeCharacters(StringUtil.NVLLIF(dto.getMachineName(),""));
                writer.writeEndElement();
                writer.writeStartElement("ora_time");
                if(dto.getCommitTimeStamp()!=null){
                    writer.writeCharacters(sdf.format(dto.getCommitTimeStamp()));
                }else {
                    writer.writeCharacters("");
                }
                writer.writeEndElement();
                writer.writeStartElement("ora_program");
                String sessionInfo = dto.getSessionInfo();
                writer.writeCharacters(StringUtil.NVLLIF(sessionInfo.substring(sessionInfo.lastIndexOf("=")+1,sessionInfo.length()),""));
                writer.writeEndElement();

                writer.writeStartElement("ora_datalist");
                writer.writeStartElement("ora_record");
                writer.writeAttribute("recnum","1");
                writer.writeStartElement("ora_schema");
                writer.writeCharacters(StringUtil.NVLLIF(dto.getSegOwner(),""));
                writer.writeEndElement();
                writer.writeStartElement("tab_name");
                writer.writeCharacters(StringUtil.NVLLIF(dto.getTableName(),""));
                writer.writeEndElement();
                writer.writeStartElement("tab_action");
                writer.writeCharacters(StringUtil.NVLLIF(dto.getOperation(),""));
                writer.writeEndElement();

                Statement stmt = parser.parse(new StringReader(dto.getSqlRedo()));
                stmt.accept(new StatementVisitorImpl(resultParse));
                System.out.println(resultParse.toDataValueXml());
                System.out.println(resultParse.toWhereValueXml());
                System.out.println(resultParse.toOldValueXml());
                if(dto.getOperation().equalsIgnoreCase("INSERT")){
                    writer.writeStartElement("data_value");
                    List<ResultColVal> resultColValList = resultParse.getDataVal().listAll();
                    for (ResultColVal resultColVal:resultColValList) {
                        writer.writeStartElement(resultColVal.getCol().replace("\"",""));
                        writer.writeCharacters(StringUtil.NVLLIF(resultColVal.getVal(),""));
                        writer.writeEndElement();
                    }
                    writer.writeEndElement();

                    insert++;
                } else if(dto.getOperation().equalsIgnoreCase("UPDATE")){

                    ResultParse resultParseOld = new ResultParse();
                    Statement stmtOld = parser.parse(new StringReader(dto.getSqlUndo()));
                    stmtOld.accept(new StatementVisitorImpl(resultParseOld));
                    List<ResultColVal> resultColValList = resultParse.getDataVal().listAll();
                    writer.writeStartElement("data_value");
                    for (ResultColVal resultColVal:resultColValList) {
                        writer.writeStartElement(resultColVal.getCol().replace("\"",""));
                        writer.writeCharacters(StringUtil.NVLLIF(resultColVal.getVal(),""));
                        writer.writeEndElement();
                    }
                    writer.writeEndElement();
                    List<ResultColVal> whereVal = resultParse.getWhereVal().listAll();
                    writer.writeStartElement("where_value");
                    for (ResultColVal resultColVal:whereVal) {
                        writer.writeStartElement(resultColVal.getCol().replace("\"",""));
                        writer.writeCharacters(StringUtil.NVLLIF(resultColVal.getVal(),""));
                        writer.writeEndElement();
                    }
                    writer.writeEndElement();
                    List<ResultColVal> oldVals = resultParseOld.dataVal.listAll();
                    writer.writeStartElement("old_value");
                    for (ResultColVal oldVal:oldVals) {
                        writer.writeStartElement(oldVal.getCol().replace("\"",""));
                        writer.writeCharacters(StringUtil.NVLLIF(oldVal.getVal(),""));
                        writer.writeEndElement();
                    }
                    writer.writeEndElement();

                    update++;
                } else if(dto.getOperation().equalsIgnoreCase("DELETE")){
                    List<ResultColVal> whereVal = resultParse.getWhereVal().listAll();
                    writer.writeStartElement("where_value");
                    for (ResultColVal resultColVal:whereVal) {
                        writer.writeStartElement(resultColVal.getCol().replace("\"",""));
                        writer.writeCharacters(StringUtil.NVLLIF(resultColVal.getVal(),""));
                        writer.writeEndElement();
                    }
                    writer.writeEndElement();

                    delete++;
                }
                writer.writeEndElement();
                writer.writeEndElement();
                writer.writeEndElement();
                writer.flush();

                /**
                 * 更新断点信息
                 */
                BusIncrementBreakPointInfo breakPointInfo = new BusIncrementBreakPointInfo();
                breakPointInfo.setJgxtlb(this.getOraXtlb());
                breakPointInfo.setScn(new BigDecimal(dto.getScn()));
                breakPointInfo.setSeq(new BigDecimal(dto.getSequence()));
                breakPointInfo.setGxsj(new Date());
                int cnt = incrementBreakPointInfoMapper.getCountByxtlb(this.getOraXtlb());
                if(cnt==0){
                    incrementBreakPointInfoMapper.insertSelective(breakPointInfo);
                }else {
                    incrementBreakPointInfoMapper.updateByPrimaryKeySelective(breakPointInfo);
                }

                long length = file.length();
                // 状态记录
                count++;
//                endTime = lst.get(i).get(timeField);
                // 文件大小达到目标值，停止写入节点信息
                // 临界空间设置为总空间的10%，比如设置100M最大值，文件大于90M的时候就停止写入文件，防止文件超过最大值
                if(length > (maxSize * 0.9)){
                    scnz = Integer.valueOf(dataDtoList.get(i).getScn());
                    seqz = Integer.valueOf(dataDtoList.get(i).getSequence());
                    Map<String, Object> segment = new HashMap<>();
                    segment.put("file",file);
                    segment.put("count", count);
                    segment.put("beginTime",beginTime);
                    segment.put("endTime",endTime);
                    segment.put("scnq",scnq);
                    segment.put("scnz",scnz);
                    segment.put("seqq",seqq);
                    segment.put("seqz",seqz);
                    this.segments.add(segment);
                    writer.writeEndDocument();
                    writer.flush();

//                    /**
//                     * 记录文件信息
//                     */
//                    BusIncrementFileInfo fileInfo = new BusIncrementFileInfo();
//                    fileInfo.setWjm(file.getName());
//                    fileInfo.setWjzt("2");
//                    fileInfo.setGxsj(new Date());
//                    fileInfo.setJgxtlb(this.getOraXtlb());
//                    fileInfo.setMd(DigestUtils.md5Hex(new FileInputStream(file)));
//                    fileInfo.setWjdx(new BigDecimal(file.length()));
//                    fileInfo.setSjldelete(new BigDecimal(delete));
//                    fileInfo.setSjlinsert(new BigDecimal(insert));
//                    fileInfo.setSjlupdate(new BigDecimal(update));
//                    fileInfo.setScnq(new BigDecimal(dataDtoList.get(this.currentIndex).getScn()));
//                    fileInfo.setSeqq(Long.valueOf(dataDtoList.get(this.currentIndex).getSequence()));
//                    fileInfo.setScnz(new BigDecimal(dataDtoList.get(i).getScn()));
//                    fileInfo.setSeqz(Long.valueOf(dataDtoList.get(i).getSequence()));
//                    File preFile = (File)this.segments.get(this.segments.size()-2).get("file");
//                    fileInfo.setSywjm(preFile.getName());
//                    fileInfo.setGxsj(new Date());
//                    fileInfo.setCwzt("0");
//                    fileInfoMapper.insertSelective(fileInfo);

                    this.setHasFinished(false);
                    this.setCurrentIndex(i);

//                    write2File(dataDtoList,incrementBreakPointInfoMapper,fileInfoMapper);
                }
            }
            Map<String, Object> segment = new HashMap<>();
            segment.put("file",file);
            segment.put("count", count);
            segment.put("beginTime",beginTime);
            segment.put("endTime",endTime);
            this.segments.add(segment);
        }
        // 写入结束标签
        writer.writeEndDocument();
        writer.flush();

        /**
         * 记录文件信息
         */
//        BusIncrementFileInfo fileInfo = new BusIncrementFileInfo();
//        fileInfo.setWjm(file.getName());
//        fileInfo.setWjzt("2");
//        fileInfo.setGxsj(new Date());
//        fileInfo.setJgxtlb(this.getOraXtlb());
//        fileInfo.setMd(DigestUtils.md5Hex(new FileInputStream(file)));
//        fileInfo.setWjdx(new BigDecimal(file.length()));
//        fileInfo.setSjldelete(new BigDecimal(delete));
//        fileInfo.setSjlinsert(new BigDecimal(insert));
//        fileInfo.setSjlupdate(new BigDecimal(update));
//        fileInfo.setScnq(new BigDecimal(dataDtoList.get(this.currentIndex).getScn()));
//        fileInfo.setSeqq(Long.valueOf(dataDtoList.get(this.currentIndex).getSequence()));
//        fileInfo.setScnz(new BigDecimal(dataDtoList.get(dataDtoList.size()-2).getScn()));
//        fileInfo.setSeqz(Long.valueOf(dataDtoList.get(dataDtoList.size()-2).getSequence()));
//        File preFile = (File)this.segments.get(this.segments.size()-2).get("file");
//        fileInfo.setSywjm(preFile.getName());
//        fileInfo.setGxsj(new Date());
//        fileInfo.setCwzt("0");
//        fileInfoMapper.insertSelective(fileInfo);
        this.setCurrentIndex(dataDtoList.size());
        this.setHasFinished(true);

    }

}
