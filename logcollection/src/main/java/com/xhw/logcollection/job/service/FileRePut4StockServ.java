package com.xhw.logcollection.job.service;

/**
 * 存量错误数据重传任务服务接口
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-03-02
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public interface FileRePut4StockServ {
    /**
     * 重传数据
     *
     * @throws Exception
     */
    public void fileRePut() throws Exception;
}
