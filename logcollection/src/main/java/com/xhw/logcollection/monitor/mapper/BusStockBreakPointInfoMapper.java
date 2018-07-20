package com.xhw.logcollection.monitor.mapper;

import com.xhw.logcollection.model.dto.BusStockBreakFileDto;
import com.xhw.logcollection.monitor.entity.BusStockBreakPointInfo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface BusStockBreakPointInfoMapper extends Mapper<BusStockBreakPointInfo> {

    /**
     * 根据条件获取断点文件列表
     * @Author wanghaiyang
     * @param  paraMap
     * @Date 2018/3/2 15:14
     */
    List<BusStockBreakFileDto> listBreakFileByCondition(Map<String,Object> paraMap);

    /**
     * 获取待上传数据列表
     * @Author wanghaiyang
     * @param  paraMap
     * @Date 2018/3/9 11:37
     */
    List<BusStockBreakPointInfo> listSync(Map<String,Object> paraMap);

    /**
     * 根据条件获取数据断点列表
     * @author konggang
     * @date 2018/3/16 15:32
     */
    List<BusStockBreakPointInfo> listByCondition(Map<String,Object> paraMap);
}