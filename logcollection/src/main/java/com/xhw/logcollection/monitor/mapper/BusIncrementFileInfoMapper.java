package com.xhw.logcollection.monitor.mapper;

import com.xhw.logcollection.monitor.entity.BusIncrementFileInfo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface BusIncrementFileInfoMapper extends Mapper<BusIncrementFileInfo> {

    /**
     * 根据条件获取增量断点文件列表
     * @Author wanghaiyang
     * @param  paraMap
     * @Date 2018/3/1 14:12
     */
    List<BusIncrementFileInfo> listByCondition(Map<String,Object> paraMap);

    List<BusIncrementFileInfo> getUploadList(Map<String,Object> paraMap);

    /**
     * 根据文件名更新增量文件信息
     * @Author wanghaiyang
     * @param  busIncrementFileInfo
     * @Date 2018/3/2 14:26
     */
    boolean updateByFile(BusIncrementFileInfo busIncrementFileInfo);

    /**
     * 获取需要上传的数据
     * @Author wanghaiyang
     * @param  paraMap
     * @Date 2018/3/9 8:55
     */
    List<BusIncrementFileInfo> listSync(Map<String,Object> paraMap);
}