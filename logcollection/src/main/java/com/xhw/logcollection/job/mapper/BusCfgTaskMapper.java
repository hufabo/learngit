package com.xhw.logcollection.job.mapper;

import com.xhw.logcollection.job.entity.BusCfgTask;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface BusCfgTaskMapper extends Mapper<BusCfgTask> {

    /**
     * 根据条件查询单表日志采集参数列表
     * @Author:wanghaiyang
     * @Date: 2018/2/28 15:06
     * @params  paraMap 参数
     * @Modified by:
     */
    List<BusCfgTask> listByCondition(Map<String,Object> paraMap);

    void deleteALL();
}