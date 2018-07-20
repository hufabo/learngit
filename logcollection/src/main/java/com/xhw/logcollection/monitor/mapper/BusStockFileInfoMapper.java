package com.xhw.logcollection.monitor.mapper;

import com.xhw.logcollection.monitor.entity.BusStockFileInfo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface BusStockFileInfoMapper extends Mapper<BusStockFileInfo> {

    /**
     * 更新存量文件信息
     * @Author wanghaiyang
     * @param  busStockFileInfo
     * @Date 2018/3/2 16:15
     */
    boolean updateFileInfo(BusStockFileInfo busStockFileInfo);

    /**
     * 获取待上传数据列表
     * @Author wanghaiyang
     * @param paraMap
     * @Date 2018/3/9 13:03
     */
    List<BusStockFileInfo> listSync(Map<String,Object> paraMap);

    /**
     * 根据条件返回存量文件信息列表
     * 比如 查询需要重传的存量文件
     * @author konggang
     * @date 2018/3/9 16:14
     */
    List<BusStockFileInfo> listByCondition(Map<String, Object> params);

    List<BusStockFileInfo> getUploadList(Map<String, Object> params);
}