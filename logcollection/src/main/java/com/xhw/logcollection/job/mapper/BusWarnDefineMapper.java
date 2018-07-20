package com.xhw.logcollection.job.mapper;

import com.xhw.logcollection.job.entity.BusWarnDefine;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author 孔纲
 * @Date 2018/5/2
 */
public interface BusWarnDefineMapper extends Mapper<BusWarnDefine> {

    List<String> codeList();
}
