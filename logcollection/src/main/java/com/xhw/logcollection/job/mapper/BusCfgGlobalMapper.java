package com.xhw.logcollection.job.mapper;

import com.xhw.logcollection.job.entity.BusCfgGlobal;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface BusCfgGlobalMapper extends Mapper<BusCfgGlobal> {

    /**
     * 根据条件获取日志采集全局参数列表
     * @Author:wanghaiyang
     * @Date: 2018/2/28 15:23
     * @params  paraMap
     * @Modified by:
     */
    List<BusCfgGlobal> listByCondition(Map<String,Object> paraMap);

    void deleteALL();
}