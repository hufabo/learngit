package com.xhw.logcollection.analyze.logfile;

import com.xhw.logcollection.model.dto.IncrementDataDto;
import com.xhw.logcollection.util.BaseDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 日志解析工具类
 * @version 0.0.1
 * @Author wanghaiyang
 * @Description
 * @Date 2018/2/27 13:03
 * @Modified By:
 */
@Deprecated
public class LogAnalyzeUtil {

    private static BaseDao baseDao = new BaseDao();

    private Connection con = baseDao.getConnection();

    /**
     * 调用logminer生成数据字典文件
     * @Author:wanghaiyang
     * @Date: 2018/2/27 11:58
     * @params  dicFile 字典文件名
     * @params  dicPath 字典文件目录路径
     * @Modified by:
     */
    public void createDictionary(String dicFile,String dicPath) throws Exception{
        String createDictSql = "BEGIN dbms_logmnr_d.build(dictionary_filename => '"+dicFile+"', dictionary_location =>'"+dicPath+"'); END;";
        CallableStatement callableStatement = con.prepareCall(createDictSql);
        callableStatement.execute();
    }

    /**
     * 启动logminer分析,得到增量日志数据
     * @Author:wanghaiyang
     * @Date: 2018/2/27 11:56
     * @params  xtlbdm 系统类别代码
     * @params  startScn 开始scn号
     * @params  logPathList 日志文件路径
     * @params  dicFilePath 字典文件路径
     * @throws Exception
     * @Modified by:
     */
    public List<IncrementDataDto> startLogmur(String xtlbdm,int startScn,List<String> logPathList,String dicFilePath) throws Exception{
        List<IncrementDataDto> logList = new ArrayList<IncrementDataDto>();//创建日志列表
        ResultSet resultSet = null;
        // 获取源数据库连接
        Statement statement = con.createStatement();
        // 添加所有日志文件，本代码仅分析联机日志
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" BEGIN");
        for (int i = 0;i<logPathList.size();i++){
            sbSQL.append(" dbms_logmnr.add_logfile(logfilename=>'"+logPathList.get(i)+"', options=>dbms_logmnr.ADDFILE);");
        }
        sbSQL.append("DBMS_LOGMNR.start_logmnr (\n" +
                "      DictFileName   => '"+dicFilePath+"'\n" +
                "      ,StartScn => "+startScn+"\n" +
                "   );");
        sbSQL.append(" END;");
        CallableStatement callableStatement = con.prepareCall(sbSQL+"");
        callableStatement.execute();
        // 打印获分析日志文件信息
        resultSet = statement.executeQuery("select * from v$logmnr_contents vl\n" +
                "left join lc.bus_cfg_task cfg on vl.TABLE_NAME=cfg.bm\n" +
                "where vl.SCN>(select bp.scn from lc.bus_increment_break_point_info bp where bp.jgxtlb='"+xtlbdm+"')\n" +
                "and vl.REDO_VALUE>(select bp.seq from lc.bus_increment_break_point_info bp where bp.jgxtlb='"+xtlbdm+"')\n" +
                "order by timestamp desc");
        int flag = 0;
        while(resultSet.next()){
            IncrementDataDto log = new IncrementDataDto();//创建日志对象
            log.setScn(resultSet.getString("SCN"));
            log.setStartScn(resultSet.getString("START_SCN"));
            log.setCommintScn(resultSet.getString("COMMIT_SCN"));
            log.setTimeStamp(resultSet.getDate("TIMESTAMP"));
            log.setStartTimeStamp(resultSet.getDate("START_TIMESTAMP"));
            log.setCommitTimeStamp(resultSet.getDate("COMMIT_TIMESTAMP"));
            log.setMachineName(resultSet.getString("MACHINE_NAME"));
            log.setOsUserName(resultSet.getString("OS_USERNAME"));
            log.setUserName(resultSet.getString("USERNAME"));
            log.setOperation(resultSet.getString("OPERATION"));
            log.setSegOwner(resultSet.getString("SEG_OWNER"));
            log.setSegTypeName(resultSet.getString("SEG_TYPE_NAME"));
            log.setSequence(resultSet.getString("SEQUENCE#"));
            log.setSession(resultSet.getString("SESSION#"));
            log.setSessionInfo(resultSet.getString("SESSION_INFO"));
            log.setSerial(resultSet.getString("SERIAL#"));
            log.setSqlRedo(resultSet.getString("SQL_REDO"));
            log.setSqlUndo(resultSet.getString("SQL_UNDO"));
            log.setTableName(resultSet.getString("TABLE_NAME"));
            log.setTableSpace(resultSet.getString("TABLE_SPACE"));
            log.setRedoValue(resultSet.getString("REDO_VALUE"));
            log.setUndoValue(resultSet.getString("UNDO_VALUE"));
            logList.add(log);
        }
        return logList;
    }

    /**
     * 关闭日志解析，关闭数据库链接
     * @Author:wanghaiyang
     * @Date: 2018/2/27 13:02
     * @Modified by:
     */
    public void endLogmnr(){
        try {
            String closeSQL = "BEGIN dbms_logmnr.end_logmnr(); END;";
            CallableStatement callableStatementClose = null;
            callableStatementClose = con.prepareCall(closeSQL+"");
            callableStatementClose.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
