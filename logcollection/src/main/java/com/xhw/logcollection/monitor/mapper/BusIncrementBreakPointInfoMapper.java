package com.xhw.logcollection.monitor.mapper;

import com.xhw.logcollection.monitor.entity.BusIncrementBreakPointInfo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 增量数据断点mapper
 * wanghaiyang
 * 2018.03.01 13:31
 */
public interface BusIncrementBreakPointInfoMapper extends Mapper<BusIncrementBreakPointInfo> {

    /**
     * 根据条件获取增量数据断点列表
     * @param paraMap
     * @return
     */
    List<BusIncrementBreakPointInfo> listByCondition(Map<String,Object> paraMap);

    /**
     * 获取待上传数据列表
     * @Author wanghaiyang
     * @param  paraMap
     * @Date 2018/3/9 11:43
     */
    List<BusIncrementBreakPointInfo> listSync(Map<String,Object> paraMap);

    /**
     * 根据系统类型获取
     * @Author wanghaiyang
     * @param  xtlb
     * @Date 2018/3/29 16:57
     */
    int getCountByxtlb(String xtlb);
}