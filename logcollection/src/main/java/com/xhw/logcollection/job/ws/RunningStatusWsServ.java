package com.xhw.logcollection.job.ws;

/**
 *  软件运行状态信息webservice服务接口
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-03-06
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public interface RunningStatusWsServ {
    /**
     * 上报软件运行状态信息
     *
     * @throws Exception
     */
    public boolean reportRunningStatus() throws Exception;
}
