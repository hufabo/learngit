package com.xhw.logcollection.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 常量类
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-27
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public class Constant {
    /**
     * begin 日志采集全局参数表 关键字
     */
    public final static String BUSCFGGLOBAL_AZDM="azdm";        //安装代码
    public final static String BUSCFGGLOBAL_CLSJKZDZ="clsjkzdz";       //存量数据单个数据块最大数据量
    public final static String BUSCFGGLOBAL_CLSJKLJZDZ="clsjkljzdz";   //存量数据采集任务最大数据库连接数
    public final static String BUSCFGGLOBAL_CLRWQDSJ="clrwqdsj";       //存量数据采集任务启动时间
    public final static String BUSCFGGLOBAL_CLRWJSSJ="clrwjssj";       //存量数据采集任务结束时间
    public final static String BUSCFGGLOBAL_ZLCJZQ="zlcjzq";           //增量数据采集周期
    public final static String BUSCFGGLOBAL_RZJXWJZDZ="rzjxwjzdz";     //日志解析文件最大值
    public final static String BUSCFGGLOBAL_CLSCML="clscml";   //存量数据文件上传目录
    public final static String BUSCFGGLOBAL_ZLSCML="zlscml";   //增量数据文件上传目录
    public final static String BUSCFGGLOBAL_JGXTLB="jgxtlb";   //交管信息系统类别

    //定义 日志采集全局参数表 关键字及说明
    public static Map<String ,String> BUSCFGGLOBAL_MAP = new HashMap<>();
    static {
        BUSCFGGLOBAL_MAP.put(BUSCFGGLOBAL_AZDM,"安装代码");
        BUSCFGGLOBAL_MAP.put(BUSCFGGLOBAL_CLSJKZDZ,"存量数据单个数据块最大数据量");
        BUSCFGGLOBAL_MAP.put(BUSCFGGLOBAL_CLSJKLJZDZ,"存量数据采集任务最大数据库连接数");
        BUSCFGGLOBAL_MAP.put(BUSCFGGLOBAL_CLRWQDSJ,"存量数据采集任务启动时间");
        BUSCFGGLOBAL_MAP.put(BUSCFGGLOBAL_CLRWJSSJ,"存量数据采集任务结束时间");
        BUSCFGGLOBAL_MAP.put(BUSCFGGLOBAL_ZLCJZQ,"增量数据采集周期");
        BUSCFGGLOBAL_MAP.put(BUSCFGGLOBAL_RZJXWJZDZ,"日志解析文件最大值");
        BUSCFGGLOBAL_MAP.put(BUSCFGGLOBAL_CLSCML,"存量数据文件上传目录");
        BUSCFGGLOBAL_MAP.put(BUSCFGGLOBAL_ZLSCML,"增量数据文件上传目录");
        BUSCFGGLOBAL_MAP.put(BUSCFGGLOBAL_JGXTLB,"交管信息系统类别");
    }
    /**
     * end 日志采集全局参数表 关键字
     */


    /**
     * begin 单表日志采集参数 xml标签名称
     */
    public final static String BUSCFGTASK_JGXTLB="jgxtlb";	//系统类别代码
    public final static String BUSCFGTASK_BM="bm";	        //表名
    public final static String BUSCFGTASK_BMMS="bmms";	    //表名描述
    public final static String BUSCFGTASK_CLCJBJ="clcjbj";	//存量数据采集标记，0-不采集，1-采集
    public final static String BUSCFGTASK_SJCZD="sjczd";    //时间戳字段
    public final static String BUSCFGTASK_CLQSRQ="clqsrq";  //存量数据起始日期
    public final static String BUSCFGTASK_CLGLTJ="clgltj";  //存量数据过滤条件
    public final static String BUSCFGTASK_CLWCBJ="clwcbj";  //存量数据采集完成标记,0-未启动，1-正在执行，2-已完成
    public final static String BUSCFGTASK_ZLKHDGLLX="zlkhdgllx";    //增量数据客户端过滤类型
    public final static String BUSCFGTASK_ZLINSERT="zlinsert";      //是否采集“insert”增量数据，0-否，1-是
    public final static String BUSCFGTASK_ZLUPDATE="zlupdate";      //是否采集“update”增量数据
    public final static String BUSCFGTASK_ZLDELETE="zldelete";      //是否采集“delete”增量数据
    public final static String BUSCFGTASK_CJSJ="cjsj";  //策略创建时间
    public final static String BUSCFGTASK_GXSJ="gxsj";  //策略更新时间
    /**
     * end 单表日志采集参数 xml标签名称
     */

    /**
     * begin 后端线程任务编号
     */
    public final static String TASKID_FILE_PUT_TASK="01";	        //日志解析文件传输线程任务
    public final static String TASKID_FILE_RE_PUT_TASK="02";	    //错误数据重传线程任务
    public final static String TASKID_UPDATE_STRATEGY_TASK="03";    //采集策略更新线程任务
    public final static String TASKID_STOCK_SPLIT_TASK="04";        //存量数据采集线程拆分任务
    public final static String TASKID_STOCK_EXECUTE_TASK="05";      //存量数据采集线程执行任务
    public final static String TASKID_INCR_GET_SOURCE_FILE_TASK="06";//增量源数据文件采集线程任务
    public final static String TASKID_INCR_EXECUTE_TASK="07";	    //增量数据采集线程执行任务
    public final static String TASKID_FILE_RESULT_STATUS_TASK="08"; //日志解析文件反馈处理线程任务
    public final static String TASKID_HTTP_INTERACTION_TASK="09";   //HTTP传输守程任务
    public final static String TASKID_REPORT_HEARTBEAT_TASK="10";   //心跳状态上报线程任务
    public final static String TASKID_RUNLOG_TASK="11";             //软件运行状况检查任务
    public final static String TASKID_MONITOR_STAT_TASK="12";       //采集情况统计任务
    public final static String TASKID_INCR_INTEGRATED_TASK="13";    //增量完整流程任务

    /**
     * end 后端线程任务编号
     */

    //文件上传任务：文件上传任务最大并发量
    public final static String FILE_PUT_MAX_THREADS="file_put.max_threads";
    //文件上传任务：待上传的存量日志解析文件目录
    public final static String FILE_PUT_STOCK_DIR_AWAIT="file_put.stock.dir.await";
    //文件上传任务：存量日志解析文件目录,上传成功备份目录
    public final static String FILE_PUT_STOCK_DIR_SUCCEEDED="file_put.stock.dir.succeeded";
    //文件上传任务：存量日志解析文件目录,上传失败备份目录
    public final static String FILE_PUT_STOCK_DIR_FAILED="file_put.stock.dir.failed";
    //文件上传任务：存量日志解析文件目录,备份目录
    public final static String FILE_PUT_STOCK_DIR_BACKUP="file_put.stock.dir.backup";

    //文件上传任务：待上传的增量日志解析文件目录
    public final static String FILE_PUT_INCR_DIR_AWAIT="file_put.incr.dir.await";
    //文件上传任务：增量日志解析文件目录,上传成功备份目录
    public final static String FILE_PUT_INCR_DIR_SUCCEEDED="file_put.incr.dir.succeeded";
    //文件上传任务：增量日志解析文件目录,上传失败备份目录
    public final static String FILE_PUT_INCR_DIR_FAILED="file_put.incr.dir.failed";
    //文件上传任务：增量日志解析文件目录,备份目录
    public final static String FILE_PUT_INCR_DIR_BACKUP="file_put.incr.dir.backup";

    //增量采集数据库在线日志文件目录
    public final static String FILE_PUT_INCR_DIR_LOG="file_put.incr.dir.log";
    //增量采集数据库归档日志文件目录
    public final static String FILE_PUT_INCR_DIR_ARC_LOG="file_put.incr.dir.arc.log";

    //临时文件目录，比如临时放置解压文件
    public final static String FILE_PUT_DIR_TEMP="file_put.dir.temp";

    //文件上传方式
    public final static String FILE_PUT_TYPE="file_put.type";

    //FTP服务器信息
    public final static String FILE_PUT_FTP_HOST="file_put.ftp.host";
    public final static String FILE_PUT_FTP_PORT="file_put.ftp.port";
    public final static String FILE_PUT_FTP_USERNAME="file_put.ftp.username";
    public final static String FILE_PUT_FTP_PASSWORD="file_put.ftp.password";

    //oracle解析目录ftp信息
    public final static String ORACLE_FTP_REALPATH="oracle.ftp.realpath";
    public final static String ORACLE_FTP_HOST="oracle.ftp.host";
    public final static String ORACLE_FTP_PORT="oracle.ftp.port";
    public final static String ORACLE_FTP_USERNAME="oracle.ftp.username";
    public final static String ORACLE_FTP_PASSWORD="oracle.ftp.password";

    //NFS文件服务器信息
    public final static String FILE_PUT_NFS_URL="file_put.nfs.url";

    // 存量数据文件表 - 文件状态
    public final static String STOCK_FILE_STUS_COLLECTED="1";
    public final static String STOCK_FILE_STUS_UPLOAD_SERVER="2";
    public final static String STOCK_FILE_STUS_UPLOAD_BJ="3";
    public final static String STOCK_FILE_STUS_STORAGE="4";
    public final static String STOCK_FILE_STUS_RETRANS="5";
    public final static String STOCK_FILE_STUS_FILE_MISSING="6";
    public final static String STOCK_FILE_STUS_DATA_LOST="7";
}
