package com.xhw.logcollection.job.service;

/**
 *  采集指定系统的 数据库日志文件 服务接口
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-03-08
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
@Deprecated
public interface IncrGetSourceFileTaskServ {

    /**
     *  采集指定系统的 数据库日志文件
     * @param xtlb 数据源类别名称
     * @throws Exception
     */
    public void getSourceFiles(String xtlb) throws Exception;

}
