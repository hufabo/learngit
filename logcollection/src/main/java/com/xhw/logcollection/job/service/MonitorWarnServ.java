package com.xhw.logcollection.job.service;

import com.github.pagehelper.PageInfo;
import com.xhw.logcollection.job.entity.MonitorWarn;
import com.xhw.logcollection.model.dto.MonitorWarnDto;

import java.util.List;
import java.util.Map;

/**
 * 异常告警业务层
 * @author 孔纲
 * @date 2018/3/5
 */
public interface MonitorWarnServ {

    /**
     * 保存告警信息
     * @author konggang
     * @date 2018/3/26 15:53
     */
    public void save(MonitorWarn monitorWarn) throws Exception;

    /**
     * 根据条件获取列表
     * @author konggang
     * @date 2018/3/2 17:39
     */
    PageInfo<MonitorWarnDto> listByCondition(Map<String, Object> paraMap);

    /**
     * 处理异常
     * @author konggang
     * @date 2018/5/3 10:55
     */
    void handle(long runid, long warnid);
}
