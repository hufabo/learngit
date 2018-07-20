package com.xhw.logcollection.job.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xhw.logcollection.job.entity.MonitorTask;
import com.xhw.logcollection.job.entity.MonitorWarn;
import com.xhw.logcollection.job.mapper.MonitorWarnMapper;
import com.xhw.logcollection.job.service.MonitorWarnServ;
import com.xhw.logcollection.model.dto.MonitorWarnDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 监控任务业务层实现类
 * @author 孔纲
 * @date 2018/3/5
 */
@Service
public class MonitorWarnServImpl implements MonitorWarnServ {

    @Autowired
    private MonitorWarnMapper mapper;

    @Override
    public void save(MonitorWarn monitorWarn) throws Exception{
        long runid = monitorWarn.getRunid();
        long warnid = mapper.selectNextWarnId(null);
        monitorWarn.setWarnid(warnid);
        mapper.insert(monitorWarn);
    }

    /**
     * 根据条件获取列表
     * @param paraMap
     * @return
     */
    @Override
    public PageInfo<MonitorWarnDto> listByCondition(Map<String, Object> paraMap) {
        if(paraMap.get("curentPage")!=null && !paraMap.get("curentPage").toString().equals("")){
            PageHelper.startPage(Integer.valueOf(paraMap.get("curentPage").toString()),Integer.valueOf(paraMap.get("pageSize").toString()));
        }
        List<MonitorWarnDto> list = mapper.listByCondition(paraMap);
        PageInfo<MonitorWarnDto> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public void handle(long runid, long warnid) {
        MonitorWarn warn = new MonitorWarn();
        warn.setRunid(runid);
        warn.setWarnid(warnid);
        warn.setSfycl("1");
        mapper.updateByPrimaryKeySelective(warn);
    }

}
