package com.xhw.logcollection.monitor.mapper;

import com.xhw.logcollection.monitor.entity.BusStockDealStatus;
import tk.mybatis.mapper.common.Mapper;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

public interface BusStockDealStatusMapper extends Mapper<BusStockDealStatus> {

    /**
     * 根据条件获取存量数据处理列表
     * @Author:wanghaiyang
     * @Date: 2018/2/28 17:10
     * @params  paraMap
     * @Modified by:
     */
    List<BusStockDealStatus> listByCondition(Map<String,Object> paraMap);

    /**
     * 获取待同步数据列表
     * @Author wanghaiyang
     * @param  paraMap
     * @Date 2018/3/9 11:24
     */
    List<BusStockDealStatus> listSync(Map<String,Object> paraMap);

    /**
     * 根据存量文件表的信息，统计采集数据量，文件数等信息
     * @author konggang
     * @date 2018/4/26 16:35
     */
    List<BusStockDealStatus> queryStockDealStatus(Map<String,Object> paraMap);

    /**
     * 查询存量文件入库情况
     * @author konggang  
     * @date 2018/5/11 10:05
     */ 
    List<BusStockDealStatus> queryRKWJS();

    void deleteALL();
}