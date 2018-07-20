package com.xhw.logcollection.job.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xhw.logcollection.job.entity.BusCfgGlobal;
import com.xhw.logcollection.job.entity.BusCfgTask;
import com.xhw.logcollection.job.mapper.BusCfgTaskMapper;
import com.xhw.logcollection.job.service.BusCfgTaskServ;
import com.xhw.logcollection.monitor.entity.BusStockDealStatus;
import com.xhw.logcollection.monitor.mapper.BusStockDealStatusMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 单表日志采集参数服务接口实现类
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-27
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
@Service
public class BusCfgTaskServImpl implements BusCfgTaskServ {
    private static Log log = LogFactory.getLog(BusCfgTaskServImpl.class);

    @Autowired
    private BusCfgTaskMapper mapper;

    @Autowired
    private BusStockDealStatusMapper stockDealStatusMapper;

    public List<BusCfgTask> selectAll(){
        return mapper.selectAll();
    }

    public void batchDelAndSave(List<BusCfgTask> lst) throws Exception{
        mapper.deleteALL();
        stockDealStatusMapper.deleteALL();
        for(BusCfgTask cfgTask:lst){
            mapper.insert(cfgTask);

            // 新增存量配置的同时，新增 存量数据处理状态表 记录
            BusStockDealStatus stockDealStatus = new BusStockDealStatus();
            stockDealStatus.setJgxtlb(cfgTask.getJgxtlb());
            stockDealStatus.setBm(cfgTask.getBm());
            stockDealStatus.setCjzt("0");  // 采集状态，0-未启动， 1-正在执行，2-已完成
            stockDealStatus.setRkzt("0"); // 入库状态，0-未完成，1-已完成
            stockDealStatusMapper.insertSelective(stockDealStatus);
        }
    }

    /**
     *  更新采集任务
     *
     *  实现思路描述：用传入的参数比较表中中参数，有变化则更新，表中不存在则新增
     * @param lst 所有参数
     * @throws Exception
     */
    public void batchSave(List<BusCfgTask> lst) throws Exception{
        if(lst==null){
            log.info("没有待更新的采集任务！");
            return ;
        }

        //查询已有数据
        List<BusCfgTask> lstOld = mapper.selectAll();

        if(lstOld!=null) {
            Map<String, BusCfgTask> map = new HashMap<>();
            for (BusCfgTask beanOld : lstOld) {
                map.put(beanOld.getJgxtlb()+beanOld.getBm(), beanOld);
            }

            for (BusCfgTask bean : lst) {
                String key = bean.getJgxtlb()+bean.getBm();
                if(map.containsKey(key)){
                    //判断是否有更新的数据
                    if(map.get(key).getCompareContent().equals(bean.getCompareContent())){
                        log.debug("不需要更新采集任务："+bean);
                    } else {
                        mapper.updateByPrimaryKey(bean); //更新策略
                    }
                } else {
                    log.debug("新增采集任务："+bean);
                    mapper.insertSelective(bean);       //新增策略
                    // 新增存量配置的同时，新增 存量数据处理状态表 记录
                    BusStockDealStatus stockDealStatus = new BusStockDealStatus();
                    stockDealStatus.setJgxtlb(bean.getJgxtlb());
                    stockDealStatus.setBm(bean.getBm());
                    stockDealStatus.setCjzt("0");  // 采集状态，0-未启动， 1-正在执行，2-已完成
                    stockDealStatus.setRkzt("0"); // 入库状态，0-未完成，1-已完成
                    stockDealStatusMapper.insertSelective(stockDealStatus);
                }
            }
        } else {
            //新增
            for (BusCfgTask bean : lst) {
                log.debug("新增采集任务："+bean);
                mapper.insertSelective(bean);
            }
        }

    }

    /**
     * 根据条件查询单表日志采集参数列表
     *
     * @param paraMap
     * @Author:wanghaiyang
     * @Date: 2018/2/28 15:06
     * @params paraMap 参数
     * @Modified by:
     */
    @Override
    public PageInfo<BusCfgTask> listByCondition(Map<String, Object> paraMap) {
        if(paraMap.get("curentPage")!=null && !paraMap.get("curentPage").toString().equals("")){
            PageHelper.startPage(Integer.valueOf(paraMap.get("curentPage").toString()),Integer.valueOf(paraMap.get("pageSize").toString()));
        }
        List<BusCfgTask> busCfgTasks = mapper.listByCondition(paraMap);
        PageInfo<BusCfgTask> pageInfo = new PageInfo<BusCfgTask>(busCfgTasks);
        return pageInfo;
    }

    /**
     * 获取所有系统类别代码
     *
     * @Author wanghaiyang
     * @Date 2018/3/7 15:42
     */
    @Override
    public List<String> listJgxtlb() {
        List<String> jgxtlbList = new ArrayList<>();
        List<BusCfgTask> busCfgTaskList = mapper.selectAll();
        for (BusCfgTask buscfgtask:busCfgTaskList) {
            if(!jgxtlbList.contains(buscfgtask.getJgxtlb())){
                jgxtlbList.add(buscfgtask.getJgxtlb());
            }
        }
        return jgxtlbList;
    }

}
