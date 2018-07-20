package com.xhw.logcollection.job.service;

import com.github.pagehelper.PageInfo;
import com.xhw.logcollection.job.entity.BusCfgTask;

import java.util.List;
import java.util.Map;

/**
 * 单表日志采集参数服务接口
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-27
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public interface BusCfgTaskServ {
    /**
     * 查询全部
     * @return
     */
    public List<BusCfgTask> selectAll();

    /**
     * 批量保存及更新
     *
     * @param lst
     * @throws Exception
     */
    public void batchSave(List<BusCfgTask> lst) throws Exception;

    public void batchDelAndSave(List<BusCfgTask> lst) throws Exception;

    /**
     * 根据条件查询单表日志采集参数列表
     * @Author:wanghaiyang
     * @Date: 2018/2/28 15:06
     * @params  paraMap 参数
     * @Modified by:
     */
    PageInfo<BusCfgTask> listByCondition(Map<String,Object> paraMap);

    /**
     * 获取所有系统类别代码
     * @Author wanghaiyang
     * @param  * @param null
     * @Date 2018/3/7 15:42
     */
    List<String> listJgxtlb();

}
