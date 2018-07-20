package com.xhw.logcollection.monitor.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xhw.logcollection.monitor.entity.BusIncrementFileInfo;
import com.xhw.logcollection.monitor.mapper.BusIncrementFileInfoMapper;
import com.xhw.logcollection.monitor.service.BusIncrementFileInfoServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version 0.0.1
 * @Author wanghaiyang
 * @Description
 * @Date 2018/2/28 17:16
 * @Modified By:
 */
@Service
public class BusIncrementFileInfoServImpl implements BusIncrementFileInfoServ {

    @Autowired
    private BusIncrementFileInfoMapper fileInfoMapper;

    /**
     * 根据条件获取增量断点文件列表
     *
     * @param paraMap
     * @Author wanghaiyang
     * @Date 2018/3/1 14:12
     */
    @Override
    public PageInfo<BusIncrementFileInfo> listByCondition(Map<String, Object> paraMap) {
        if(paraMap.get("curentPage")!=null && !paraMap.get("curentPage").toString().equals("")){
            PageHelper.startPage(Integer.valueOf(paraMap.get("curentPage").toString()),Integer.valueOf(paraMap.get("pageSize").toString()));
        }
        List<BusIncrementFileInfo> fileInfoList = fileInfoMapper.listByCondition(paraMap);
        PageInfo<BusIncrementFileInfo> pageInfo = new PageInfo<BusIncrementFileInfo>(fileInfoList);
        return pageInfo;
    }

    /**
     * 根据状态获取增量文件列表
     *
     * @param paraMap
     * @Author wanghaiyang
     * @Date 2018/3/2 14:18
     */
    @Override
    public List<BusIncrementFileInfo> listByZt(Map<String, Object> paraMap) {
        PageHelper.startPage(1,Integer.valueOf(paraMap.get("count").toString()));
        List<BusIncrementFileInfo> fileInfoList = fileInfoMapper.listByCondition(paraMap);
        PageInfo<BusIncrementFileInfo> pageInfo = new PageInfo<BusIncrementFileInfo>(fileInfoList);
        if(pageInfo!=null && pageInfo.getList()!=null){
            return pageInfo.getList();
        }else {
            return null;
        }
    }

    /**
     * 根据文件名更新增量文件信息
     *
     * @param busIncrementFileInfo
     * @Author wanghaiyang
     * @Date 2018/3/2 14:24
     */
    @Override
    public boolean updateByFile(BusIncrementFileInfo busIncrementFileInfo) {
        return fileInfoMapper.updateByFile(busIncrementFileInfo);
    }

    @Override
    public List<String> getIncrFilePutList() {
        Map<String, Object> params = new HashMap<>();
        params.put("wjzt","1"); // 文件状态。1-采集，2-上传文件服务器，3-上传部局，4-入库，5要求重传，6-本地文件丢失，7-数据丢失
        List<BusIncrementFileInfo> incrFileInfos = fileInfoMapper.getUploadList(params);
        List<String> fileNames = new ArrayList<>();
        for(BusIncrementFileInfo fileInfo:incrFileInfos){
            fileNames.add(fileInfo.getWjm());
        }
        return fileNames;
    }
}
