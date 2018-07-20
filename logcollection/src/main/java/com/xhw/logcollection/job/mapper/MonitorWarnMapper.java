package com.xhw.logcollection.job.mapper;

import com.xhw.logcollection.job.entity.MonitorWarn;
import com.xhw.logcollection.model.dto.MonitorWarnDto;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 异常报警mapper
 *
 * @author 孔纲
 * @date 2018/3/5
 */
public interface MonitorWarnMapper extends Mapper<MonitorWarn> {

    /**
     * 根据条件返回列表
     * @author konggang
     * @date 2018/3/5 11:04
     */
    List<MonitorWarnDto> listByCondition(Map<String,Object> paraMap);

    /**
     * 返回下一个warnid（数据库最大+1）
     * @author konggang
     * @date 2018/3/26 16:10
     */
    public long selectNextWarnId(@Param(value="runid")Long runid);
}
