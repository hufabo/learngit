package com.xhw.logcollection.job.service;

import com.github.pagehelper.PageInfo;
import com.xhw.logcollection.job.entity.BusCfgGlobal;
import com.xhw.logcollection.job.entity.BusCfgGlobalBean;

import java.util.List;
import java.util.Map;

/**
 * 日志采集全局参数服务接口
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-27
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public interface BusCfgGlobalServ {
    /**
     * 查询全部
     * @return
     */
    public List<BusCfgGlobal> selectAll();

    /**
     * 批量保存及更新
     *
     * @param lst
     * @throws Exception
     */
    public void batchSave(List<BusCfgGlobal> lst) throws Exception;

    public void batchDelAndSave(List<BusCfgGlobal> lst) throws Exception;

    /**
     * 根据条件获取日志采集全局参数列表
     * @Author:wanghaiyang
     * @Date: 2018/2/28 15:23
     * @params  paraMap
     * @Modified by:
     */
    PageInfo<BusCfgGlobal> listByCondition(Map<String,Object> paraMap);

    /**
     * 获取全局参数bean类
     *
     * @return
     * @throws Exception
     */
    public BusCfgGlobalBean getBusCfgGlobalBean() throws Exception;
}
