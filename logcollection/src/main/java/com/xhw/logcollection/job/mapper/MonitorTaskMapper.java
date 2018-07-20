package com.xhw.logcollection.job.mapper;

import com.xhw.logcollection.job.entity.MonitorTask;
import com.xhw.logcollection.model.dto.MonitorTaskDto;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface MonitorTaskMapper extends Mapper<MonitorTask> {

    /**
     * 根据条件返回列表
     * @author konggang
     * @date 2018/3/5 11:04
     */
    List<MonitorTaskDto> listByCondition(Map<String,Object> paraMap);

    /*
    根据条件返回任务列表
     */
    List<MonitorTask> queryList(Map<String,Object> paraMap);

    /**
     * 根据条件统计任务数量
     * @author konggang
     * @date 2018/3/6 13:43
     */
    Long getTaskCountByCondition(Map<String,Object> paraMap);


    /*
    初始化任务状态（应用启动时调用）为1-停止；解决应用故障中止后运行状态仍然是2的情况
     */
    void initTaskStatus();
}