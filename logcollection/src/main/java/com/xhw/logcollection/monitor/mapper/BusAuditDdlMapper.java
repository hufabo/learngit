package com.xhw.logcollection.monitor.mapper;

import com.xhw.logcollection.monitor.entity.BusAuditDdl;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface BusAuditDdlMapper extends Mapper<BusAuditDdl> {

    /**
     * 根据条件获取数据审计列表
     * @Author wanghaiyang
     * @param paraMap
     * @Date 2018/3/1 16:35
     */
    List<BusAuditDdl> listByCondition(Map<String,Object> paraMap);

    /**
     * 获取需要上传的数据
     * @Author wanghaiyang
     * @param  paraMap
     * @Date 2018/3/9 8:55
     */
    List<BusAuditDdl> listSync(Map<String,Object> paraMap);
}