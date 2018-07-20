package com.xhw.logcollection.job.service.impl;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.qinhuait.agent.AgentClient;
import com.xhw.logcollection.analyze.sqlparse.StatementVisitorImpl;
import com.xhw.logcollection.job.entity.BusCfgTask;
import com.xhw.logcollection.job.entity.ContextBean;
import com.xhw.logcollection.job.mapper.BusCfgTaskMapper;
import com.xhw.logcollection.job.service.IncrIntegratedServ;
import com.xhw.logcollection.model.dto.ResultColVal;
import com.xhw.logcollection.model.dto.ResultParse;
import com.xhw.logcollection.monitor.entity.BusAuditDdl;
import com.xhw.logcollection.monitor.entity.BusIncrementBreakPointInfo;
import com.xhw.logcollection.monitor.entity.BusIncrementFileInfo;
import com.xhw.logcollection.monitor.mapper.BusAuditDdlMapper;
import com.xhw.logcollection.monitor.mapper.BusIncrementBreakPointInfoMapper;
import com.xhw.logcollection.monitor.mapper.BusIncrementFileInfoMapper;
import com.xhw.logcollection.system.entity.Agent;
import com.xhw.logcollection.system.entity.DataSource;
import com.xhw.logcollection.system.entity.ShareProtocol;
import com.xhw.logcollection.system.mapper.AgentMapper;
import com.xhw.logcollection.system.mapper.DataSourceMapper;
import com.xhw.logcollection.system.mapper.ShareProtocolMapper;
import com.xhw.logcollection.util.*;
import jcifs.smb.NtlmPasswordAuthentication;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.concurrent.Future;
import java.util.regex.Matcher;

/**
 * @Author 孔纲
 * @Date 2018/4/1
 */
@Service
public class IncrIntegratedServImpl implements IncrIntegratedServ {

    private static Logger logger  = LoggerFactory.getLogger(IncrIntegratedServImpl.class);

    @Autowired
    private BusIncrementBreakPointInfoMapper incrementBreakPointInfoMapper;
    @Autowired
    private DataSourceMapper dataSourceMapper;
    @Autowired
    private ShareProtocolMapper shareProtocolMapper;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private BusIncrementFileInfoMapper fileInfoMapper;
    @Autowired
    private BusAuditDdlMapper ddlMapper;
    @Autowired
    private BusCfgTaskMapper cfgTaskMapper;
    @Autowired
    private DruidDataSource dataSource;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Override
    public void parseLog(String xtlb) throws Exception {
        long minSCN = 0; long maxSCN =0;
        // 1 查询断点表，有记录则根据最新scn取获取文件，没有则获取所有文件
        BusIncrementBreakPointInfo breakPointInfo = new BusIncrementBreakPointInfo();
        breakPointInfo.setJgxtlb(xtlb);
        breakPointInfo = incrementBreakPointInfoMapper.selectOne(breakPointInfo);
        if(breakPointInfo != null) minSCN = breakPointInfo.getScn().longValue();
        DataSource database = getDataSource(xtlb);
        String drivername = "oracle.jdbc.driver.OracleDriver";
        String url;
        if("0".equals(database.getRac())){
            url = "jdbc:oracle:thin:@"+ database.getIp() +":"+ database.getPort() +":"+ database.getSid();
        }else{
            url = "jdbc:oracle:thin:@(DESCRIPTION =(ADDRESS = (PROTOCOL = TCP)(HOST = "+ database.getIp()
                    +")(PORT = "+ database.getPort() +"))(CONNECT_DATA = (SERVER = DEDICATED)(SERVICE_NAME = "+ database.getSid() +")))";
        }
        String user = database.getOraUser();
        String schema = database.getOraSchema().toUpperCase(); // shema名大写
        String password = database.getOraPassword();
        Connection connection = JdbcUtil.getConnection(drivername, url, user, password);
        List<String> onlinePathList = getLogFileList(xtlb, connection, minSCN, maxSCN);
        if(onlinePathList == null || onlinePathList.size() == 0){
            throw new RuntimeException("未采集到增量日志文件！");
        }
        //在线日志列表
        Map<String, Object> cfgs = getTableCfgMap();
        logger.info("日志解析任务开始");
        DruidPooledConnection con = dataSource.getConnection();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            startLogmnr(onlinePathList, con);
            String resultSql = getResultQuerySQL(schema, minSCN, maxSCN, cfgs);
            preparedStatement = con.prepareStatement(resultSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet = preparedStatement.executeQuery();
            List<BusIncrementFileInfo> fileInfos = null;
            try {
                logger.debug("成功返回结果");
                // 循环解析日志查询结果
                fileInfos = new ArrayList<>();
                while(resultSet.next()){
                    resultSet.previous();
                    logger.debug("分析结果中...");
                    BusIncrementFileInfo fileInfo = parseLogContent(resultSet, cfgs, xtlb, user, schema);
                    fileInfos.add(fileInfo);
                    //maxSCN = fileInfo.getScnz().longValue();
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("分析结果异常："+ e.getMessage());
            }finally {
                JdbcUtil.closeResource(resultSet, preparedStatement, null);
            }

            // 当获取最大SCN值
            try {
                String queryMaxSCNSQL = "SELECT max(t.SCN) maxscn FROM v$logmnr_contents t ";
                preparedStatement = con.prepareStatement(queryMaxSCNSQL);
                resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){
                    maxSCN = resultSet.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error("查询最大SCN异常："+ e.getMessage());
            } finally {
                JdbcUtil.closeResource(resultSet, preparedStatement, null);
            }

            logger.info("分析完成，开始入库增量文件信息");
            // 更新增量文件表
            for(int i =0; i< fileInfos.size(); i++){
                BusIncrementFileInfo fileInfo = fileInfos.get(i);
                if(i > 0){
                    String sywjm = fileInfos.get(i - 1).getWjm();
                    fileInfo.setSywjm(sywjm);
                }else{
                    fileInfo.setSywjm("null");
                }
                if(i < (fileInfos.size() - 1)){
                    String xywjm = fileInfos.get(i + 1).getWjm();
                    fileInfo.setXywjm(xywjm);
                }else{
                    fileInfo.setXywjm("null");
                }
                fileInfoMapper.insertSelective(fileInfo);
            }
            // 更新增量断点表
            breakPointInfo = new BusIncrementBreakPointInfo();
            breakPointInfo.setJgxtlb(xtlb);
            breakPointInfo.setScn(new BigDecimal(maxSCN));
            breakPointInfo.setSeq(new BigDecimal(0)); // 这个字段暂时没有使用
            breakPointInfo.setGxsj(new Date());
            int cnt = incrementBreakPointInfoMapper.getCountByxtlb(xtlb);
            if(cnt == 0){
                incrementBreakPointInfoMapper.insertSelective(breakPointInfo);
            }else {
                incrementBreakPointInfoMapper.updateByPrimaryKeySelective(breakPointInfo);
            }
        } finally {
            logger.info("结束日志分析");
            String closeSQL = "BEGIN dbms_logmnr.end_logmnr(); END;";
            CallableStatement callableStatementClose = null;
            try {
                callableStatementClose = con.prepareCall(closeSQL);
                callableStatementClose.execute();
            } finally {
                JdbcUtil.closeResource(null, callableStatementClose, con);
            }
        }

    }

    public List<String> getLogFileList(String xtlb, Connection connection, long minSCN, long maxSCN) throws Exception {

        // 3 查询文件列表
        Set<String> arclogs = new HashSet<>();

        CallableStatement call = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            /*
            生成字典
             */
            String generateDict = "BEGIN SYS.DBMS_LOGMNR_D.BUILD( OPTIONS=> SYS.DBMS_LOGMNR_D.STORE_IN_REDO_LOGS); END;";
            call = connection.prepareCall(generateDict);
            call.execute();
            JdbcUtil.closeResource(null, call,null);
            /*
            查询字典文件以及最大scn
             */
            String queryDictLogFiles = "SELECT name,NEXT_CHANGE# next_scn FROM V$ARCHIVED_LOG where status='A' " +
                    "and sequence# >= (SELECT max(sequence#) FROM V$ARCHIVED_LOG WHERE DICTIONARY_BEGIN='YES') " +
                    "and sequence# <= (SELECT max(sequence#) FROM V$ARCHIVED_LOG WHERE DICTIONARY_END='YES')";
            pstmt = connection.prepareStatement(queryDictLogFiles);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                String filePath = rs.getString("name");
                arclogs.add(filePath);
                // 初始化最大scn为当前最新scn
                if(maxSCN == 0){
                    maxSCN = Long.parseLong(rs.getString("next_scn"));
                }
            }
            JdbcUtil.closeResource(rs,pstmt,null);
            /*
            获取归档日志
             */
            String queryArcLogFiles ="select name from v$archived_log where status='A' ";
            if(minSCN != 0){
                queryArcLogFiles = queryArcLogFiles +  "and (FIRST_CHANGE# >= "+ minSCN +" or (FIRST_CHANGE# <= "+ minSCN +" and NEXT_CHANGE# >= "+ minSCN +"))";
            }
            if(maxSCN != 0){
                queryArcLogFiles = queryArcLogFiles + "and (NEXT_CHANGE# <= "+ maxSCN +" or (FIRST_CHANGE# <= "+ maxSCN +" and NEXT_CHANGE# >= "+ maxSCN +"))";
            }
            pstmt = connection.prepareStatement(queryArcLogFiles);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                String filePath = rs.getString("name");
                arclogs.add(filePath);
            }
        } finally {
            JdbcUtil.closeResource(rs,pstmt,connection);
        }

        // 5 从共享目录或者agent方式获取文件到本地
        Vector<String> logFiles = getRemoteLogFiles(arclogs, xtlb);

        // 6 解析
        List<String> onlinePathList = new ArrayList<>();//在线文件路径
        for(String logFile:logFiles){
            onlinePathList.add(logFile);
            logger.info("logfile:" + logFile);
        }

//        minSCN=10410934; maxSCN = 10436213;
//        onlinePathList.add("d:\\smb/1A91122FEB9D4F8B81E359B89A6EF5C5/O1_MF_1_665_FDTTN3P9_.ARC");
//        onlinePathList.add("d:\\smb/1A91122FEB9D4F8B81E359B89A6EF5C5/O1_MF_1_675_FDV31G57_.ARC");
//        onlinePathList.add("d:\\smb/1A91122FEB9D4F8B81E359B89A6EF5C5/O1_MF_1_671_FDV0SDPF_.ARC");
//        onlinePathList.add("d:\\smb/1A91122FEB9D4F8B81E359B89A6EF5C5/O1_MF_1_663_FDTT98XW_.ARC");
//        onlinePathList.add("d:\\smb/1A91122FEB9D4F8B81E359B89A6EF5C5/O1_MF_1_667_FDTW0X1V_.ARC");
//        onlinePathList.add("d:\\smb/1A91122FEB9D4F8B81E359B89A6EF5C5/O1_MF_1_673_FDV29SSH_.ARC");
//        onlinePathList.add("d:\\smb/1A91122FEB9D4F8B81E359B89A6EF5C5/O1_MF_1_669_FDTZMLRT_.ARC");
//        onlinePathList.add("d:\\smb/1A91122FEB9D4F8B81E359B89A6EF5C5/O1_MF_1_676_FDV31H2Q_.ARC");
//        onlinePathList.add("d:\\smb/1A91122FEB9D4F8B81E359B89A6EF5C5/O1_MF_1_668_FDTW0Y0C_.ARC");
//        onlinePathList.add("d:\\smb/1A91122FEB9D4F8B81E359B89A6EF5C5/O1_MF_1_666_FDTTN4N9_.ARC");
//        onlinePathList.add("d:\\smb/1A91122FEB9D4F8B81E359B89A6EF5C5/O1_MF_1_662_FDTR79FX_.ARC");
//        onlinePathList.add("d:\\smb/1A91122FEB9D4F8B81E359B89A6EF5C5/O1_MF_1_670_FDTZMMQT_.ARC");
//        onlinePathList.add("d:\\smb/1A91122FEB9D4F8B81E359B89A6EF5C5/O1_MF_1_664_FDTT9B10_.ARC");
//        onlinePathList.add("d:\\smb/1A91122FEB9D4F8B81E359B89A6EF5C5/O1_MF_1_674_FDV29TRY_.ARC");
//        onlinePathList.add("d:\\smb/1A91122FEB9D4F8B81E359B89A6EF5C5/O1_MF_1_672_FDV0SFS9_.ARC");

        return onlinePathList;
    }

    private DataSource getDataSource(String xtlb) {
        // 2 获得数据库信息
        DataSource database = null;
        try {
            database = new DataSource();
            database.setJgxtlb(xtlb);
            // 1 正常 2 删除
            database.setJlzt("1");
            database = dataSourceMapper.selectOne(database);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("目标数据源配置异常，增量采集任务中止！系统类别（"+ xtlb +"）");
        }
        if(database == null){
            throw new RuntimeException("目标数据源未配置，增量采集任务中止！系统类别（"+ xtlb +"）");
        }
        return database;
    }

    private Vector<String> getRemoteLogFiles(Set<String> arclogs, String xtlb) throws Exception {
        logger.info("增量日志文件获取任务开始");
        // 4 获取协议信息
        HashMap<String, Object> params = new HashMap<>();
        params.put("jgxtlb",xtlb);
        List<ShareProtocol> shareProtocols = shareProtocolMapper.queryShareProtocol(params);
        Vector<String> logFiles = new Vector<>();
        Vector<Future> futures = new Vector<>();
        for(final String arcFile:arclogs){
            Future<?> future = ContextBean.commonTaskThreadPool.submit(() -> {
                File localFile = null;
                if(shareProtocols!= null && shareProtocols.size() > 0){
                    // 如果NFS和SMB都配置，只取第一条
                    ShareProtocol protocol = shareProtocols.get(0);
                    // 协议类型(1=SMB/2=NFS)
                    String xylx = protocol.getXylx();
                    // 系统类型（1=window/2=Linux）
                    String systemType = protocol.getXtlb();
                    String ip = protocol.getMlgxip();
                    String username = protocol.getMlgxyhm();
                    String password = protocol.getMlgxmm();
                    String remote_arc_dir = protocol.getMlgxgdrzmc();
                    String remote_arc_path = protocol.getMlgxgdrz();
                    String remote_log_dir = protocol.getMlgxzxrzmc();
                    String remote_log_path = protocol.getMlgxzxrz();
                    if("1".equals(xylx)){
                        // SMB协议需要账户和密码，NFS协议不需要
                        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(ip, username, password);
                        // 获取归档日志
                        String arc_url = "smb://"+ ip + "/" + remote_arc_dir;
                        String arcLogDir = LoadGlobalPropertiesUtil.getProperty(Constant.FILE_PUT_INCR_DIR_ARC_LOG);
                        // 获取归档日志 本地存在在 归档目录/随机uuid 下面
                        String remoteUrl = arcFile;

                        remoteUrl = remoteUrl.replaceFirst("(?i)" + Matcher.quoteReplacement(remote_arc_path),"");
                        remoteUrl = arc_url.concat("/").concat(remoteUrl);
                        remoteUrl = remoteUrl.replace("\\","/");
                        logger.debug("type:{} file:{}","smb", remoteUrl);
                        localFile = SMBUtils.smbGetFile(remoteUrl, arcLogDir.concat("/").concat(xtlb), auth);
                    }else if("2".equals(xylx)){
                        // 获取归档日志
                        String arc_url = "nfs://"+ ip + "/" + remote_arc_dir;
                        String arcLogDir = LoadGlobalPropertiesUtil.getProperty(Constant.FILE_PUT_INCR_DIR_ARC_LOG);
                        // 获取归档日志 本地存在在 归档目录/随机uuid 下面
                        String remoteUrl = arcFile;
                        remoteUrl = remoteUrl.replaceFirst("(?i)" + Matcher.quoteReplacement(remote_arc_path),"");
                        remoteUrl = arc_url.concat("/").concat(remoteUrl);
                        remoteUrl = remoteUrl.replace("\\","/");
                        String fileName = remoteUrl.substring(remoteUrl.lastIndexOf("/"));
                        logger.debug("type:{} file:{}","nfs", remoteUrl);
                        NFSUtils.nfsGetFile(remoteUrl, null, arcLogDir.concat("/").concat(xtlb));
                        localFile = new File(arcLogDir.concat("/").concat(xtlb).concat("/").concat(fileName));
                    }
                }else{
                    String arcLogDir = LoadGlobalPropertiesUtil.getProperty(Constant.FILE_PUT_INCR_DIR_ARC_LOG);
                    // 查询agent配置，如果有则利用agent获取文件
                    HashMap<String, Object> p = new HashMap<>();
                    p.put("jgxtlb",xtlb);
                    p.put("zt","1"); // 状态。1=启用，0=未启用
                    List<Agent> agents = agentMapper.queryAgent(p);
                    if(agents!=null && agents.size() > 0){
                        // 重复配置只取第一个
                        Agent agent = agents.get(0);
                        String host = agent.getDlip();
                        BigDecimal port = agent.getDldkh();
                        // agent 获取文件
                        String remoteUrl = arcFile;
                        remoteUrl = remoteUrl.replace("\\","/");
                        String fileName = remoteUrl.substring(remoteUrl.lastIndexOf("/"));
                        try {
                            AgentClient client = new AgentClient(host, port.intValue(),arcLogDir.concat("/").concat(xtlb));
                            client.getFile(arcFile);
                            localFile = new File(arcLogDir.concat("/").concat(xtlb).concat("/").concat(fileName));
                        }catch (Exception e){
                            e.printStackTrace();
                            logger.error("agent 获取文件异常！agent host:{}, port:{}, file:{}", host, port, arcFile);
                        }
                    }
                }

                String realpath = null;
                InputStream is = null;
                boolean b = false;
                try {
                    realpath = LoadGlobalPropertiesUtil.getProperty(Constant.ORACLE_FTP_REALPATH);
                    String host = LoadGlobalPropertiesUtil.getProperty(Constant.ORACLE_FTP_HOST);
                    String port = LoadGlobalPropertiesUtil.getProperty(Constant.ORACLE_FTP_PORT);
                    String username = LoadGlobalPropertiesUtil.getProperty(Constant.ORACLE_FTP_USERNAME);
                    String password = LoadGlobalPropertiesUtil.getProperty(Constant.ORACLE_FTP_PASSWORD);
                    is = new FileInputStream(localFile);
                    b = FtpUtil.uploadFile(host, Integer.parseInt(port), username, password, null, xtlb, localFile.getName(), is);
                    if(b){
                        String filepath = realpath.concat("/").concat(xtlb).concat("/").concat(localFile.getName());
                        logFiles.add(filepath);
                    }else{
                        logger.error("文件上传oracle服务器失败：" + localFile.getPath());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    IOUtils.closeQuietly(is);
                }
            });
            futures.add(future);
        }

        while(true){
            try {
                int finished = 0;
                for(Future future:futures){
                    if(future.isDone()) finished++;
                }
                logger.info("日志文件获取进度({})：{}/{} 成功数：{}",xtlb, finished, futures.size(), logFiles.size());
                if(finished == futures.size()){
                    break;
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return logFiles;
    }

    private String getResultQuerySQL(String schema, long minSCN, long maxSCN, Map<String, Object> cfgs) {
        StringBuilder queryLogResult = new StringBuilder("SELECT * FROM v$logmnr_contents t where 1=1 ");
        if(StringUtil.isNotEmpty(schema)){
            queryLogResult.append(" and t.SEG_OWNER = '").append(schema).append("'");
        }
        if(minSCN > 0){
            queryLogResult.append(" and t.scn >= ").append(minSCN);
        }
        if(maxSCN > 0){
            queryLogResult.append(" and t.scn <= ").append(maxSCN);
        }
        if(cfgs!=null && cfgs.size()>0){
            Set<String> table_names = cfgs.keySet();
            String join_table_name = StringUtils.join(table_names, "','");
            queryLogResult.append(" and t.table_name in ('").append(join_table_name).append("')");
        }
        // 1 = INSERT 2 = DELETE 3 = UPDATE 5 = DDL
        queryLogResult.append(" and t.operation_code in (1,2,3,5) ");
        queryLogResult.append(" order by t.SCN asc ");
        logger.debug("查询结果SQL");
        logger.debug(queryLogResult.toString());
        return queryLogResult.toString();
    }

    private void startLogmnr(List<String> logPathList, DruidPooledConnection con) throws SQLException {
        // 添加所有日志文件,开启解析模式
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" BEGIN");
        for (int i = 0; i<logPathList.size(); i++){
            if(i == 0){
                sbSQL.append(" sys.dbms_logmnr.add_logfile(logfilename=>'"+logPathList.get(i)+"', options=>dbms_logmnr.NEW);");
            }else{
                sbSQL.append(" dbms_logmnr.add_logfile(logfilename=>'"+logPathList.get(i)+"', options=>dbms_logmnr.ADDFILE);");
            }
        }
        sbSQL.append("dbms_logmnr.start_logmnr(OPTIONS => DBMS_LOGMNR.DICT_FROM_REDO_LOGS + DBMS_LOGMNR.COMMITTED_DATA_ONLY);");
        sbSQL.append(" END;");
        logger.debug("日志解析SQL");
        logger.debug(sbSQL.toString());
        CallableStatement callableStatement = con.prepareCall(sbSQL.toString());
        callableStatement.execute();
    }

    private Map<String, Object> getTableCfgMap() {
        // 查询单表配置
        Map<String, Object> cfgs = new HashMap<>();
        List<BusCfgTask> busCfgTasks = cfgTaskMapper.selectAll();
        for(BusCfgTask cfgTask:busCfgTasks){
            String bm = cfgTask.getBm().toUpperCase(); // 表名大写
            String zlinsert = cfgTask.getZlinsert();
            String zlupdate = cfgTask.getZlupdate();
            String zldelete = cfgTask.getZldelete();
            Map<String, String> config = new HashMap<>();
            config.put("zlinsert", zlinsert);
            config.put("zlupdate", zlupdate);
            config.put("zldelete", zldelete);
            cfgs.put(bm, config);
        }
        return cfgs;
    }

    private void parseLogContentByFileName(ResultSet resultSet, Map<String, Object> cfgs, String xtlb, String fileName) throws Exception {
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
        String fileDir = LoadGlobalPropertiesUtil.getProperty(Constant.FILE_PUT_INCR_DIR_AWAIT);
        File file = FileUtil.getFile(fileDir, fileName);
        OutputStream out = new FileOutputStream(file);
        XMLStreamWriter writer = output.createXMLStreamWriter(out, "UTF-8");
        writer.writeStartDocument();
        writer.writeStartElement("root");
        // 写属性节点
        writer.writeStartElement("ora_type");
        writer.writeCharacters("2");
        writer.writeEndElement();
        writer.writeStartElement("ora_xtlb");
        writer.writeCharacters(xtlb);
        writer.writeEndElement();

        Long currentCommitSCN = null;
        boolean firstRecord = true;

        while (resultSet.next()) {
            String commit_scn = resultSet.getString("COMMIT_SCN");
            String sessionid = resultSet.getString("SESSION#");
            String serial = resultSet.getString("SERIAL#");
            String username = resultSet.getString("USERNAME");
            String machine_name = resultSet.getString("MACHINE_NAME");
            String session_info = resultSet.getString("SESSION_INFO");
            Date commit_timestamp = resultSet.getDate("COMMIT_TIMESTAMP");
            String scn = resultSet.getString("SCN");
            String seq = resultSet.getString("SEQUENCE#");
            String seg_type_name = resultSet.getString("SEG_TYPE_NAME");
            Date timestamp = resultSet.getDate("TIMESTAMP");
            String sql_redo = resultSet.getString("SQL_REDO");
            String sql_undo = resultSet.getString("SQL_UNDO");
            String redo_value = resultSet.getString("REDO_VALUE");
            String undo_value = resultSet.getString("UNDO_VALUE");
            String seg_owner = resultSet.getString("SEG_OWNER");
            String os_username = resultSet.getString("OS_USERNAME");
            String table_space = resultSet.getString("TABLE_SPACE");
            String table_name = resultSet.getString("TABLE_NAME");
            String operation = resultSet.getString("OPERATION");

            // DDL 直接跳过
            if("DDL".equals(operation)){
                continue;
            }

            Long commitSCN = (commit_scn != null) ? Long.valueOf(commit_scn) : null;
            if (commitSCN == null || currentCommitSCN != commitSCN) {
                if (!firstRecord) {
                    writer.writeEndElement(); // ora_datalist
                    writer.writeEndElement(); // ora_transaction
                }
                // 开启新事务
                writer.writeStartElement("ora_transaction");
                writer.writeAttribute("sessionid", sessionid);
                writer.writeAttribute("serial", serial);
                writer.writeStartElement("ora_user");
                writer.writeCharacters(username);
                writer.writeEndElement();
                writer.writeStartElement("ora_client");
                writer.writeCharacters(machine_name);
                writer.writeEndElement();
                writer.writeStartElement("ora_time");
                String commitTimestamp = (commit_timestamp != null) ? sdf.format(commit_timestamp) : "";
                writer.writeCharacters(commitTimestamp);
                writer.writeEndElement();
                writer.writeStartElement("ora_program");
                writer.writeCharacters(session_info);
                writer.writeEndElement();
                writer.writeStartElement("ora_datalist");
                currentCommitSCN = commitSCN;
            }
            firstRecord = false;
            // 只解析insert update delete语句
            if ("INSERT".equals(operation) || "UPDATE".equals(operation) || "DELETE".equals(operation)) {
                /*
                检查该记录是否需要采集
                 */
                Map<String, String> config = (Map<String, String>) cfgs.get(table_name);
                if ("INSERT".equals(operation) && "0".equals(config.get("zlinsert"))) {
                    continue;
                }
                if ("UPDATE".equals(operation) && "0".equals(config.get("zlupdate"))) {
                    continue;
                }
                if ("DELETE".equals(operation) && "0".equals(config.get("zldelete"))) {
                    continue;
                }
                writer.writeStartElement("ora_record");
                writer.writeStartElement("ora_schema");
                writer.writeCharacters(seg_owner);
                writer.writeEndElement();
                writer.writeStartElement("tab_name");
                writer.writeCharacters(table_name);
                writer.writeEndElement();
                writer.writeStartElement("tab_action");
                writer.writeCharacters(operation);
                writer.writeEndElement();

                CCJSqlParserManager parser = new CCJSqlParserManager();
                ResultParse resultParse = new ResultParse();
                Statement stmt = parser.parse(new StringReader(sql_redo));
                stmt.accept(new StatementVisitorImpl(resultParse));
                if (operation.equalsIgnoreCase("INSERT")) {
                    writer.writeStartElement("data_value");
                    List<ResultColVal> resultColValList = resultParse.getDataVal().listAll();
                    for (ResultColVal resultColVal : resultColValList) {
                        writer.writeStartElement(resultColVal.getCol().replace("\"", ""));
                        writer.writeCharacters(StringUtil.NVLLIF(resultColVal.getVal(), ""));
                        writer.writeEndElement();
                    }
                    writer.writeEndElement();

                } else if (operation.equalsIgnoreCase("UPDATE")) {

                    ResultParse resultParseOld = new ResultParse();
                    Statement stmtOld = parser.parse(new StringReader(sql_undo));
                    stmtOld.accept(new StatementVisitorImpl(resultParseOld));
                    List<ResultColVal> resultColValList = resultParse.getDataVal().listAll();
                    writer.writeStartElement("data_value");
                    for (ResultColVal resultColVal : resultColValList) {
                        writer.writeStartElement(resultColVal.getCol().replace("\"", ""));
                        writer.writeCharacters(StringUtil.NVLLIF(resultColVal.getVal(), ""));
                        writer.writeEndElement();
                    }
                    writer.writeEndElement();
                    List<ResultColVal> whereVal = resultParse.getWhereVal().listAll();
                    writer.writeStartElement("where_value");
                    for (ResultColVal resultColVal : whereVal) {
                        writer.writeStartElement(resultColVal.getCol().replace("\"", ""));
                        writer.writeCharacters(StringUtil.NVLLIF(resultColVal.getVal(), ""));
                        writer.writeEndElement();
                    }
                    writer.writeEndElement();
                    List<ResultColVal> oldVals = resultParseOld.dataVal.listAll();
                    writer.writeStartElement("old_value");
                    for (ResultColVal oldVal : oldVals) {
                        writer.writeStartElement(oldVal.getCol().replace("\"", ""));
                        writer.writeCharacters(StringUtil.NVLLIF(oldVal.getVal(), ""));
                        writer.writeEndElement();
                    }
                    writer.writeEndElement();

                } else if (operation.equalsIgnoreCase("DELETE")) {
                    List<ResultColVal> whereVal = resultParse.getWhereVal().listAll();
                    writer.writeStartElement("where_value");
                    for (ResultColVal resultColVal : whereVal) {
                        writer.writeStartElement(resultColVal.getCol().replace("\"", ""));
                        writer.writeCharacters(StringUtil.NVLLIF(resultColVal.getVal(), ""));
                        writer.writeEndElement();
                    }
                    writer.writeEndElement();

                }
                writer.writeEndElement(); // ora_record
            }
        }
        writer.writeEndElement(); // ora_datalist
        writer.writeEndElement(); // ora_transaction
        writer.writeEndElement(); // root
        writer.writeEndDocument();
        writer.flush();
        writer.close();
        out.close();
    }

    private BusIncrementFileInfo parseLogContent(ResultSet resultSet, Map<String, Object> cfgs, String xtlb, String user, String schema) throws JAXBException, IOException, XMLStreamException, SQLException, JSQLParserException {
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
        String fileDir = LoadGlobalPropertiesUtil.getProperty(Constant.FILE_PUT_INCR_DIR_AWAIT);
        // 解析文件最大值 单位MB
        long maxSize = ContextBean.getBusCfgGlobalBean().getRzjxwjzdz() * 1024 * 1024;
        String sn = JedisUtil.getSN();
        String azdm = ContextBean.getBusCfgGlobalBean().getAzdm();
        String fileName = azdm + xtlb + "2" + DateUtil.dateNow2str("yyyyMMdd") + sn;
        File file = FileUtil.getFile(fileDir, fileName);
        OutputStream out = new FileOutputStream(file);
        XMLStreamWriter writer = output.createXMLStreamWriter(out, "UTF-8");
        writer.writeStartDocument();
        writer.writeStartElement("root");
        // 写属性节点
        writer.writeStartElement("ora_type");
        writer.writeCharacters("2");
        writer.writeEndElement();
        writer.writeStartElement("ora_xtlb");
        writer.writeCharacters(xtlb);
        writer.writeEndElement();

        // 记录当前事务点SCN，用于组装xml使用
        Long currentCommitSCN = null;
        boolean firstRecord = true;
        int update = 0;
        int delete = 0;
        int insert = 0;
        int scnq = 0;
        int scnz = 0;
        int seqq = 0;
        int seqz = 0;
        while(resultSet.next()){
            String commit_scn = resultSet.getString("COMMIT_SCN");
            String sessionid = resultSet.getString("SESSION#");
            String serial = resultSet.getString("SERIAL#");
            String username = resultSet.getString("USERNAME");
            String machine_name = resultSet.getString("MACHINE_NAME");
            String session_info = resultSet.getString("SESSION_INFO");
            Date commit_timestamp = resultSet.getDate("COMMIT_TIMESTAMP");
            String scn = resultSet.getString("SCN");
            String seq = resultSet.getString("SEQUENCE#");
            String seg_type_name = resultSet.getString("SEG_TYPE_NAME");
            Date timestamp = resultSet.getDate("TIMESTAMP");
            String sql_redo = resultSet.getString("SQL_REDO");
            String sql_undo = resultSet.getString("SQL_UNDO");
            String redo_value = resultSet.getString("REDO_VALUE");
            String undo_value = resultSet.getString("UNDO_VALUE");
            String seg_owner = resultSet.getString("SEG_OWNER");
            String os_username = resultSet.getString("OS_USERNAME");
            String table_space = resultSet.getString("TABLE_SPACE");
            String table_name = resultSet.getString("TABLE_NAME");
            String operation = resultSet.getString("OPERATION");

            /*
            如果是DDL语句直接入库
             */
            if("DDL".equals(operation)){
                BusAuditDdl auditDdl = new BusAuditDdl();
                auditDdl.setJgxtlb(xtlb);
                auditDdl.setOrauser(user);
                auditDdl.setOraschema(schema);
                auditDdl.setScn(BigDecimal.valueOf(Long.valueOf(scn)));
                auditDdl.setSeq(Long.valueOf(seq));
                auditDdl.setDxlx(getDXLX(seg_type_name));
                auditDdl.setDxm(table_name);
                auditDdl.setCzsj(timestamp);
                auditDdl.setCzlx(getCZLX(sql_redo));
                auditDdl.setNr(sql_redo);
                auditDdl.setGxsj(new Date());
                ddlMapper.insertSelective(auditDdl);
                continue;
            }

            Long commitSCN = (commit_scn != null) ? Long.valueOf(commit_scn) : null;
            if(commitSCN == null || currentCommitSCN != commitSCN){
                if(!firstRecord){
                    // 判断文件大小
                    writer.flush();
                    long length = file.length();
                    if(length > (maxSize * 0.9)){
                        resultSet.previous(); // 遍历指针回退一格
                        break; // 结束本次解析
                    }else{
                        writer.writeEndElement(); // ora_datalist
                        writer.writeEndElement(); // ora_transaction
                    }
                }
                // 开启新事务
                writer.writeStartElement("ora_transaction");
                writer.writeAttribute("sessionid", sessionid);
                writer.writeAttribute("serial", serial);
                writer.writeStartElement("ora_user");
                writer.writeCharacters(username);
                writer.writeEndElement();
                writer.writeStartElement("ora_client");
                writer.writeCharacters(machine_name);
                writer.writeEndElement();
                writer.writeStartElement("ora_time");
                String commitTimestamp = (commit_timestamp != null) ? sdf.format(commit_timestamp) : "";
                writer.writeCharacters(commitTimestamp);
                writer.writeEndElement();
                writer.writeStartElement("ora_program");
                writer.writeCharacters(session_info);
                writer.writeEndElement();
                writer.writeStartElement("ora_datalist");
                currentCommitSCN = commitSCN;
            }

            firstRecord = false;

            // 记录scn，seq信息
            if(scnq == 0){
                scnq = Integer.valueOf(scn);
                seqq = Integer.valueOf(seq);
            }
            scnz = Integer.valueOf(scn);
            seqz = Integer.valueOf(seq);

            // 只解析insert update delete语句
            if ("INSERT".equals(operation) || "UPDATE".equals(operation) || "DELETE".equals(operation)) {

                /*
                检查该记录是否需要采集
                 */
                Map<String, String> config = (Map<String, String>) cfgs.get(table_name);
                if("INSERT".equals(operation) && "0".equals(config.get("zlinsert"))){
                    continue;
                }
                if("UPDATE".equals(operation) && "0".equals(config.get("zlupdate"))){
                    continue;
                }
                if("DELETE".equals(operation) && "0".equals(config.get("zldelete"))){
                    continue;
                }

                writer.writeStartElement("ora_record");
                writer.writeStartElement("ora_schema");
                writer.writeCharacters(seg_owner);
                writer.writeEndElement();
                writer.writeStartElement("tab_name");
                writer.writeCharacters(table_name);
                writer.writeEndElement();
                writer.writeStartElement("tab_action");
                writer.writeCharacters(operation);
                writer.writeEndElement();

                CCJSqlParserManager parser = new CCJSqlParserManager();
                ResultParse resultParse = new ResultParse();
                Statement stmt = parser.parse(new StringReader(sql_redo));
                stmt.accept(new StatementVisitorImpl(resultParse));
                if(operation.equalsIgnoreCase("INSERT")){
                    writer.writeStartElement("data_value");
                    List<ResultColVal> resultColValList = resultParse.getDataVal().listAll();
                    for (ResultColVal resultColVal:resultColValList) {
                        writer.writeStartElement(resultColVal.getCol().replace("\"",""));
                        writer.writeCharacters(StringUtil.NVLLIF(resultColVal.getVal(),""));
                        writer.writeEndElement();
                    }
                    writer.writeEndElement();

                    insert++;
                } else if(operation.equalsIgnoreCase("UPDATE")){

                    ResultParse resultParseOld = new ResultParse();
                    Statement stmtOld = parser.parse(new StringReader(sql_undo));
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
                } else if(operation.equalsIgnoreCase("DELETE")){
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

                writer.writeEndElement(); // ora_record
            }

        }

        writer.writeEndElement(); // ora_datalist
        writer.writeEndElement(); // ora_transaction
        writer.writeEndElement(); // root
        writer.writeEndDocument();
        writer.flush();
        writer.close();
        out.close();

        // 返回增量文件对象
        BusIncrementFileInfo fileInfo = new BusIncrementFileInfo();
        fileInfo.setWjm(file.getName());
        fileInfo.setWjzt("1");
        fileInfo.setGxsj(new Date());
        fileInfo.setJgxtlb(xtlb);
        FileInputStream fis = new FileInputStream(file);
        String md5 = DigestUtils.md5Hex(fis);
        fileInfo.setMd(md5);
        fis.close();
        fileInfo.setWjdx(new BigDecimal(file.length()));
        fileInfo.setSjldelete(new BigDecimal(delete));
        fileInfo.setSjlinsert(new BigDecimal(insert));
        fileInfo.setSjlupdate(new BigDecimal(update));
        fileInfo.setScnq(new BigDecimal(scnq));
        fileInfo.setSeqq(Long.valueOf(seqq));
        fileInfo.setScnz(new BigDecimal(scnz));
        fileInfo.setSeqz(Long.valueOf(seqz));
        fileInfo.setGxsj(new Date());
        fileInfo.setScsj(new Date());
        fileInfo.setCwzt("0");
        return fileInfo;
    }

    private String getCZLX(String sql_redo) {
        String czlx = "99";
        if(sql_redo.toUpperCase().startsWith("CREATE")){
            czlx = "01";
        }else if(sql_redo.toUpperCase().startsWith("DROP")){
            czlx = "02";
        }else if(sql_redo.toUpperCase().startsWith("ALTER")){
            czlx = "03";
        }else if(sql_redo.toUpperCase().startsWith("RENAME")){
            czlx = "04";
        }else if(sql_redo.toUpperCase().startsWith("TRUNCATE")){
            czlx = "05";
        }else if(sql_redo.toUpperCase().startsWith("REVOKE")){
            czlx = "06";
        }
        return czlx;
    }

    private String getDXLX(String seg_type_name) {
        String dxlx = "99";
        if("TABLE".equals(seg_type_name)){
            dxlx = "01";
        }else if("VIEW".equals(seg_type_name)){
            dxlx = "02";
        }else if("PROCEDURE".equals(seg_type_name)){
            dxlx = "03";
        }else if("TABLESPACE".equals(seg_type_name)){
            dxlx = "04";
        }else if("FUNCTION".equals(seg_type_name)){
            dxlx = "05";
        }else if("TRIGGER".equals(seg_type_name)){
            dxlx = "06";
        }else if("INDEX".equals(seg_type_name)){
            dxlx = "07";
        }else if("PACKAGE".equals(seg_type_name)){
            dxlx = "08";
        }else if("PACKAGE BODY".equals(seg_type_name)){
            dxlx = "09";
        }else if("SEQUENCE".equals(seg_type_name)){
            dxlx = "10";
        }else if("SYNONYM".equals(seg_type_name)){
            dxlx = "11";
        }else if("ROLE".equals(seg_type_name)){
            dxlx = "12";
        }
        return dxlx;
    }

    @Override
    public void parseLogByFileName(String fileName) throws Exception {
        // 1、根据文件名获取采集相关信息
        BusIncrementFileInfo fileInfo = new BusIncrementFileInfo();
        fileInfo.setWjm(fileName);
        fileInfo = fileInfoMapper.selectOne(fileInfo);
        if(fileInfo == null){
            logger.error("重采异常，无该文件信息！");
            throw new RuntimeException("重采异常，无该文件信息！");
        }
        String xtlb = fileInfo.getJgxtlb();
        long minSCN = fileInfo.getScnq().longValue();
        long maxSCN = fileInfo.getScnz().longValue();

        DataSource database = getDataSource(xtlb);
        String drivername = "oracle.jdbc.driver.OracleDriver";
//        String url = "jdbc:oracle:thin:@"+ database.getIp() +":"+ database.getPort() +":"+ database.getSid();
        String url;
        if("0".equals(database.getRac())){
            url = "jdbc:oracle:thin:@"+ database.getIp() +":"+ database.getPort() +":"+ database.getSid();
        }else{
            url = "jdbc:oracle:thin:@(DESCRIPTION =(ADDRESS = (PROTOCOL = TCP)(HOST = "+ database.getIp()
                    +")(PORT = "+ database.getPort() +"))(CONNECT_DATA = (SERVER = DEDICATED)(SERVICE_NAME = "+ database.getSid() +")))";
        }
        String user = database.getOraUser();
        String schema = database.getOraSchema().toUpperCase(); // shema名大写
        String password = database.getOraPassword();
        Connection connection = JdbcUtil.getConnection(drivername, url, user, password);
        List<String> onlinePathList = getLogFileList(xtlb, connection, minSCN, maxSCN);
        if(onlinePathList == null || onlinePathList.size()== 0){
            throw new RuntimeException("未采集到增量日志文件！");
        }
        //在线日志列表
        Map<String, Object> cfgs = getTableCfgMap();
        logger.info("日志解析任务开始");
        DruidPooledConnection con = dataSource.getConnection();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            startLogmnr(onlinePathList, con);
            String resultSql = getResultQuerySQL(schema, minSCN, maxSCN, cfgs);
            preparedStatement = con.prepareStatement(resultSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet = preparedStatement.executeQuery();
            BusIncrementFileInfo file = new BusIncrementFileInfo();
            file.setWjm(fileName);
            try {
                logger.debug("成功返回结果");
                // 循环解析日志查询结果
                parseLogContentByFileName(resultSet, cfgs, xtlb, fileName);
                file.setWjzt("1");
                file.setGxsj(new Date());
            } catch (Exception e) {
                e.printStackTrace();

                logger.error("分析结果异常："+ e.getMessage());
                file.setWjzt(Constant.STOCK_FILE_STUS_DATA_LOST);
                file.setGxsj(new Date());
                file.setCwzt("1"); //错误状态。0-无错误，1-出错
                file.setCcsj(new Date());
                file.setCwxxms(e.getMessage());
            }finally {
                JdbcUtil.closeResource(resultSet, preparedStatement, null);
                // 更新增量文件表
                fileInfoMapper.updateByFile(file);
            }
            logger.info("分析完成，开始入库增量文件信息");
        } finally {
            logger.info("结束日志分析");
            String closeSQL = "BEGIN dbms_logmnr.end_logmnr(); END;";
            CallableStatement callableStatementClose = null;
            try {
                callableStatementClose = con.prepareCall(closeSQL);
                callableStatementClose.execute();
            } finally {
                JdbcUtil.closeResource(null, callableStatementClose, con);
            }
        }
    }

}
