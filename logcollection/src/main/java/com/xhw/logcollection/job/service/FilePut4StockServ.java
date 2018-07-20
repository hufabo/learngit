package com.xhw.logcollection.job.service;

/**
 * 存量文件上传任务接口
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-03-01
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public interface FilePut4StockServ {
    /**
     * 推送指定的文件
     * @param fileName 文件名
     * @throws Exception
     */
    public void putFile(String fileName) throws Exception;
}
