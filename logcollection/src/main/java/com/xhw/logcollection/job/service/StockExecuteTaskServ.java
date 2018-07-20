package com.xhw.logcollection.job.service;

import com.xhw.logcollection.monitor.entity.BusStockBreakPointInfo;

/**
 *  存量数据采集断点任务服务接口
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-03-07
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public interface StockExecuteTaskServ {
    /**
     * 存量采集任务
     *
     * @param beanTask 断点任务
     *
     * @throws Exception
     */
    public void executeTask(BusStockBreakPointInfo beanTask) throws Exception;

    /**
     * 根据文件名执行采集
     * @author konggang
     * @date 2018/3/20 16:40
     */
    public void executeByFile(String fileName) throws Exception;
}
