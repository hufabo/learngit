package com.xhw.logcollection.job.ws;

/**
 *  增量数据文件信息webservice服务接口
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-03-06
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public interface IncrFileInfoWsServ {
    /**
     * 增量数据文件信息上报接口
     *
     * @throws Exception
     */
    public boolean reportIncrFileInfo() throws Exception;
}
