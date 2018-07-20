package com.xhw.logcollection.monitor.service;

import com.github.pagehelper.PageInfo;
import com.xhw.logcollection.model.dto.BusStockBreakFileDto;
import com.xhw.logcollection.monitor.entity.BusStockBreakPointInfo;

import java.util.List;
import java.util.Map;

/**
 * 存量数据断点信息服务接口
 * @version 0.0.1
 * @Author wanghaiyang
 * @Description
 * @Date 2018/2/28 17:17
 * @Modified By:
 */
public interface BusStockBreakPointInfoServ {

    /**
     * 根据条件获取断点文件列表
     * @Author wanghaiyang
     * @param  paraMap
     * @Date 2018/3/2 15:14
     */
    PageInfo<BusStockBreakFileDto> listBreakFileByCondition(Map<String,Object> paraMap);

    /**
     * 根据条件获取存量断点列表
     * @author konggang
     * @date 2018/3/27 9:29
     */
    PageInfo<BusStockBreakPointInfo> listByCondition(Map<String,Object> paraMap);
}
