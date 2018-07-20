package com.xhw.logcollection.job.service.impl;

import com.xhw.logcollection.analyze.logfile.LogAnalyzeUtil;
import com.xhw.logcollection.job.service.IncrExecuteTaskServ;
import com.xhw.logcollection.model.dto.IncrementDataDto;
import com.xhw.logcollection.model.dto.LogParser4IncreDto;
import com.xhw.logcollection.monitor.mapper.BusIncrementBreakPointInfoMapper;
import com.xhw.logcollection.monitor.mapper.BusIncrementFileInfoMapper;
import com.xhw.logcollection.util.Constant;
import com.xhw.logcollection.util.LoadGlobalPropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *  解析指定系统的 数据库日志文件 服务接口实现类
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-03-08
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
@Service
@Deprecated
public class IncrExecuteTaskServImpl implements IncrExecuteTaskServ {

    private Logger logger = LoggerFactory.getLogger(IncrExecuteTaskServImpl.class);

    //private LogAnalyzeUtil analyzeUtil = new LogAnalyzeUtil();//日志解析工具类

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//规定日期格式

    private DecimalFormat df3 = new DecimalFormat("000000");//规定xml文件顺序号格式。6 位，每天从 1 开始到 999999。

    private LogParser4IncreDto logParser4IncreDto = new LogParser4IncreDto();

    @Autowired
    private BusIncrementFileInfoMapper fileInfoMapper;

    @Autowired
    private BusIncrementBreakPointInfoMapper incrementBreakPointInfoMapper;


    /**
     * 解析指定系统的 数据库日志文件
     *
     * @param jgxtlb 数据源类别名称
     * @throws Exception
     */
    @Override
    public void parseFiles(String jgxtlb) throws Exception {
        logger.info("解析指定系统的 数据库日志文件 服务接口实现类，" + jgxtlb);

        List<IncrementDataDto> onlineLog = null;//在线日志
        List<IncrementDataDto> arcLog = null;//归档日志
        int scn = 0;

        //1、从本机获取指定的数据库日志文件
        //2、解析指定的Oracle归档日志文件或在线日志文件
        //3、获取解析结果
        //4、按限制要求生成解析日志文件
        //5、记录数据文件结果、断点信息等

        // TODO 具体逻辑有待补充
        String arcLogDir = LoadGlobalPropertiesUtil.getProperty(Constant.FILE_PUT_INCR_DIR_ARC_LOG)+"\\"+jgxtlb;//归档日志目录
        String logDir = LoadGlobalPropertiesUtil.getProperty(Constant.FILE_PUT_INCR_DIR_LOG)+"\\"+jgxtlb;//在线日志目录
        String upDir = LoadGlobalPropertiesUtil.getProperty(Constant.FILE_PUT_INCR_DIR_AWAIT)+"\\"+jgxtlb;//增量xml待上传目录

//        /**
//         * 1.调用createDictionary方法创建字典文件
//         */
//        String dicFile = "dictionary.ora";//日志解析需指定数据库要生成的字典文件
//        String dicPath = "C:\\Oracle\\oradata\\orcl";//字典文件保存的目录路径
//        analyzeUtil.createDictionary(dicFile, dicPath);

        /**
         * 2.解析并获取解析后的结果
         */
        String xtlbdm = "";//系统类别代码
        //不同的系统有不同的日志文件
//        List<String> onlinePathList = getFileName(logDir);//在线文件路径
//        List<String> arcPathList = getFileName(arcLogDir);//归档文建路径
        List<String> onlinePathList = new ArrayList<>();//在线文件路径
        List<String> arcPathList = new ArrayList<>();//归档文建路径
        onlinePathList.add("C:\\ORACLE\\ORADATA\\ORCL\\REDO03.LOG");
        onlinePathList.add("C:\\ORACLE\\ORADATA\\ORCL\\REDO02.LOG");
        onlinePathList.add("C:\\ORACLE\\ORADATA\\ORCL\\REDO01.LOG");
        arcPathList.add("C:\\ORACLE\\FLASH_RECOVERY_AREA\\ORCL\\ARCHIVELOG\\2018_04_01\\O1_MF_1_461_FD1SN6NQ_.ARC");
        arcPathList.add("C:\\ORACLE\\FLASH_RECOVERY_AREA\\ORCL\\ARCHIVELOG\\2018_04_01\\O1_MF_1_460_FD14FYXS_.ARC");
        arcPathList.add("C:\\ORACLE\\FLASH_RECOVERY_AREA\\ORCL\\ARCHIVELOG\\2018_04_01\\O1_MF_1_459_FD0JVKG0_.ARC");
        arcPathList.add("C:\\ORACLE\\FLASH_RECOVERY_AREA\\ORCL\\ARCHIVELOG\\2018_04_01\\O1_MF_1_458_FD0JVGK3_.ARC");
        arcPathList.add("C:\\ORACLE\\FLASH_RECOVERY_AREA\\ORCL\\ARCHIVELOG\\2018_04_01\\O1_MF_1_457_FD0JTZY1_.ARC");
        arcPathList.add("C:\\ORACLE\\FLASH_RECOVERY_AREA\\ORCL\\ARCHIVELOG\\2018_04_01\\O1_MF_1_456_FD0JTYWN_.ARC");
        //完整的oracle数据库日志解析需要的字典文件路径
        String dicFilePath = "C:\\Oracle\\oradata\\orcl\\dictionary.ora";
//        if(onlinePathList!=null && onlinePathList.size()>0){
//            //在线日志列表
//            Map<String, Object> result = analyzeUtil.startLogmur(xtlbdm, onlinePathList, scn, 0, null);
//            onlineLog = (List<IncrementDataDto>) result.get("datas");
//        }
//        if(arcPathList!=null && arcPathList.size()>0){
//            //归档日志列表
//            Map<String, Object> result = analyzeUtil.startLogmur(xtlbdm,arcPathList,scn, 0, null);
//            arcLog = (List<IncrementDataDto>) result.get("datas");
//        }

        //合并集合
        onlineLog.addAll(arcLog);

        /**
         * 4、按限制要求生成解析日志文件
         * 5、记录数据文件结果、断点信息等
         */
        logParser4IncreDto.setOraXtlb(jgxtlb);
        logParser4IncreDto.setOraType("02");
        logParser4IncreDto.write2File(onlineLog,incrementBreakPointInfoMapper,fileInfoMapper);

    }

    @Override
    public void parseByFileName(String fileName) throws Exception {

    }

    /**
     * 获取目录下的文件名路径列表
     * @param directory
     * @return
     */
    public List<String> getFileName(String directory) {
        File f = new File(directory);
        List<String> fileNameList = new ArrayList<String>();
        if (!f.exists()) {
            logger.info(directory + " not exists");
            return null;
        }
        File fa[] = f.listFiles();
        for (int i = 0; i < fa.length; i++) {
            File fs = fa[i];
            if (fs.isDirectory()) {
                System.out.println(fs.getName() + " [目录]");
            } else {
                fileNameList.add(directory+"//"+fs.getName());
                System.out.println(fs.getName());
            }
        }
        return fileNameList;
    }

}
