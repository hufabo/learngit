package com.xhw.logcollection.job.service;

/**
 * 存量日志解析文件反馈处理服务接口
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-03-01
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public interface FileResultStatus4StockServ {
    /**
     * 同步文件入库状态
     * @throws Exception
     */
    public void syncFileStatus() throws Exception;
}
