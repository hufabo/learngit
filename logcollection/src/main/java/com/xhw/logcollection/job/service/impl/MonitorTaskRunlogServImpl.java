package com.xhw.logcollection.job.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xhw.logcollection.job.entity.MonitorTaskRunlog;
import com.xhw.logcollection.job.mapper.MonitorTaskRunlogMapper;
import com.xhw.logcollection.job.service.MonitorTaskRunlogServ;
import com.xhw.logcollection.model.dto.MonitorWarnDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 任务运行记录服务接口实现类
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-26
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
@Service
public class MonitorTaskRunlogServImpl implements MonitorTaskRunlogServ {

    @Autowired
    private MonitorTaskRunlogMapper monitorTaskRunlogMapper;


    @Override
    public void save(MonitorTaskRunlog bean) throws Exception {
        bean.setRunid(BigDecimal.valueOf( monitorTaskRunlogMapper.selectNextKey() )); //设置主键
        monitorTaskRunlogMapper.insert(bean);
    }

    @Override
    public void update(MonitorTaskRunlog bean) throws Exception {
        monitorTaskRunlogMapper.updateByPrimaryKey(bean);
    }

    @Override
    public List<MonitorWarnDto> getTaskRunLogList(Map<String, Object> paraMap) {
        return monitorTaskRunlogMapper.getTaskRunLogList(paraMap);
    }

    @Override
    public PageInfo<MonitorTaskRunlog> listByCondition(Map<String, Object> paraMap) {
        if(paraMap.get("curentPage")!=null && !paraMap.get("curentPage").toString().equals("")){
            PageHelper.startPage(Integer.valueOf(paraMap.get("curentPage").toString()),Integer.valueOf(paraMap.get("pageSize").toString()));
        }
        List<MonitorTaskRunlog> list = monitorTaskRunlogMapper.listByCondition(paraMap);
        PageInfo<MonitorTaskRunlog> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
