package com.xhw.logcollection.job.ws;

import java.util.List;

/**
 * 日志解析文件反馈处理webservice接口
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-03-05
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public interface FileResultStatusWsServ {
    /**
     * 通过webservice接口获取日志解析文件反馈数据
     * @param files 文件名集合
     *
     * @return
     */
    public String resultXML(List<String> files) throws Exception;
}
