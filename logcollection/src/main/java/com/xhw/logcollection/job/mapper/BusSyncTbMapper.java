package com.xhw.logcollection.job.mapper;

import com.xhw.logcollection.job.entity.BusSyncTb;
import tk.mybatis.mapper.common.Mapper;

import java.util.Map;

public interface BusSyncTbMapper extends Mapper<BusSyncTb> {

    /**
     * 根据条件获取最新一提哦啊同步记录
     * @Author wanghaiyang
     * @param  paraMap
     * @Date 2018/3/8 16:01
     */
    BusSyncTb getRecent(Map<String,Object> paraMap);

}