package com.xhw.logcollection.job.ws;

/**
 * 策略更新webservice接口
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-27
 * @note
 * @update
 *
 * 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public interface UpdateStrategyWsServ {
    /**
     * 通过webservice接口获取策略数据
     *
     * @return
     */
    public String resultXML() throws Exception;
}
