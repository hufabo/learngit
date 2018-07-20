package com.xhw.logcollection.monitor.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xhw.logcollection.monitor.entity.BusMonitorRunlog;
import com.xhw.logcollection.monitor.mapper.BusMonitorRunlogMapper;
import com.xhw.logcollection.monitor.service.BusMonitorRunlogServ;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 类的说明
 *
 * @author 孔纲
 * @date 2018/3/2
 */
@Service
public class BusMonitorRunlogServImpl implements BusMonitorRunlogServ{

    @Autowired
    private BusMonitorRunlogMapper mapper;

    /**
     * 根据条件获取列表
     * @param paraMap
     * @return
     */
    @Override
    public PageInfo<BusMonitorRunlog> listByCondition(Map<String, Object> paraMap) {
        if(paraMap.get("curentPage")!=null && !paraMap.get("curentPage").toString().equals("")){
            PageHelper.startPage(Integer.valueOf(paraMap.get("curentPage").toString()),Integer.valueOf(paraMap.get("pageSize").toString()));
        }
        List<BusMonitorRunlog> list = mapper.listByCondition(paraMap);
        PageInfo<BusMonitorRunlog> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public List<BusMonitorRunlog> queryErrorLogs(Map<String, Object> paraMap) {
        List<BusMonitorRunlog> logs = mapper.queryErrorLogs(paraMap);
        // 循环，拼接错误显示tile
        for(BusMonitorRunlog log:logs){
            List<String> errors = new ArrayList<>();
            if("0".equals(log.getFwqljzt())){
                errors.add(log.getFwqcwms());
            }
            if("0".equals(log.getSjkljzt())){
                errors.add(log.getSjkcwms());
            }
            if("0".equals(log.getKhdzt())){
                errors.add(log.getKhdcwms());
            }
            if("0".equals(log.getZxrzmlzt())){
                errors.add(log.getZxrzcwms());
            }
            if("0".equals(log.getGdrzmlzt())){
                errors.add(log.getGdrzcwms());
            }
            if("2".equals(log.getClyxzt())){
                errors.add("存量采集异常！");
            }
            if("2".equals(log.getZlyxzt())){
                errors.add("增量采集异常！");
            }
            String cwtitle = StringUtils.join(errors, ",");
            log.setCwtitle(cwtitle);
        }
        return logs;
    }

    @Override
    public void save(BusMonitorRunlog log) {
        int i = mapper.updateByPrimaryKey(log);
        if(i == 0){
            mapper.insert(log);
        }
    }

}
