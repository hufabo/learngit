package com.xhw.logcollection.job.service;

import com.xhw.logcollection.job.entity.BusCfgTask;

/**
 *  存量数据采集任务拆分服务接口
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-03-07
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public interface StockSplitTaskServ {

    /**
     * 拆分存量采集任务
     *
     * @param beanTask 存量拆分任务
     *
     * @throws Exception
     */
    public void splitTask(BusCfgTask beanTask) throws Exception;
}
