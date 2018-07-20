package com.xhw.logcollection.job.service;

/**
 *  解析指定系统的 数据库日志文件 服务接口
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-03-08
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
@Deprecated
public interface IncrExecuteTaskServ {
    /**
     * 解析指定系统的 数据库日志文件
     * @param jgxtlb
     * @throws Exception
     */
    public void parseFiles(String jgxtlb) throws Exception;

    /**
     * 根据文件名解析日志（重新采集）
     * @author konggang
     * @date 2018/3/20 17:18
     */
    public void parseByFileName(String fileName) throws Exception;
}
