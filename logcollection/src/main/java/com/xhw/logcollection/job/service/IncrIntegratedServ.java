package com.xhw.logcollection.job.service;

/**
 * @Author 孔纲
 * @Date 2018/4/1
 */
public interface IncrIntegratedServ {

    /**
     * 采集并解析最新的增量日志
     *
     * @author konggang
     * @date 2018/4/1 15:54
     */
    public void parseLog(String xtlb) throws Exception;

    public void parseLogByFileName(String fileName) throws Exception;

}