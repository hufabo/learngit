package com.xhw.logcollection.job.mapper;

import com.xhw.logcollection.job.entity.MonitorTaskRunlog;
import com.xhw.logcollection.job.entity.MonitorWarn;
import com.xhw.logcollection.model.dto.MonitorWarnDto;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface MonitorTaskRunlogMapper extends Mapper<MonitorTaskRunlog> {

    public long selectNextKey();

    List<MonitorWarnDto> getTaskRunLogList(Map<String,Object> paraMap);

    List<MonitorTaskRunlog> listByCondition(Map<String,Object> paraMap);

    Long getTaskCountByCondition(Map<String,Object> paraMap);
}