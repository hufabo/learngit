package com.xhw.logcollection.job.ws;

/**
 * 存量数据处理状态webservice服务接口
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-03-06
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public interface StockFileStatusWsServ {
    /**
     * 存量数据处理状态写入接口
     *
     * @throws Exception
     */
    public boolean reportStockFileStatus() throws Exception;

}
