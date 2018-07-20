package com.xhw.logcollection.job.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xhw.logcollection.job.entity.BusCfgGlobal;
import com.xhw.logcollection.job.entity.BusCfgGlobalBean;
import com.xhw.logcollection.job.entity.BusCfgTask;
import com.xhw.logcollection.job.mapper.BusCfgGlobalMapper;
import com.xhw.logcollection.job.service.BusCfgGlobalServ;
import com.xhw.logcollection.util.Constant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日志采集全局参数服务接口实现类
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-27
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
@Service
public class BusCfgGlobalServImpl implements BusCfgGlobalServ {
    private static Log log = LogFactory.getLog(BusCfgGlobalServImpl.class);

    @Autowired
    private BusCfgGlobalMapper mapper;

    public List<BusCfgGlobal> selectAll(){
        return mapper.selectAll();
    }

    public void batchDelAndSave(List<BusCfgGlobal> lst) throws Exception{
        mapper.deleteALL();
        for(BusCfgGlobal global:lst){
            mapper.insert(global);
        }
    }

    /**
     *  更新全局参数
     *  实现思路描述：用传入的参数比较表中中参数，有变化则更新，表中不存在则新增
     * @param lst 所有参数
     * @throws Exception
     */
    public void batchSave(List<BusCfgGlobal> lst) throws Exception{
        if(lst==null){
            log.info("没有待更新的策略！");
            return ;
        }

        //查询已有数据
        List<BusCfgGlobal> lstOld = mapper.selectAll();

        if(lstOld!=null) {
            Map<String, BusCfgGlobal> map = new HashMap<>();
            for (BusCfgGlobal beanOld : lstOld) {
                map.put(beanOld.getGjz(), beanOld);
            }

            for (BusCfgGlobal bean : lst) {
                if(map.containsKey(bean.getGjz())){
                    //判断是否有更新的数据
                    if(map.get(bean.getGjz()).getCompareContent().equals(bean.getCompareContent())){
                        log.debug("不需要更新策略："+bean);
                    } else {
                        mapper.updateByPrimaryKey(bean); //更新策略
                    }
                } else {
                    log.debug("新增策略："+bean);
                    mapper.insertSelective(bean);       //新增策略
                }
            }
        } else {
            //新增
            for (BusCfgGlobal bean : lst) {
                log.debug("新增策略："+bean);
                mapper.insertSelective(bean);
            }
        }

    }

    /**
     * 根据条件获取日志采集全局参数列表
     *
     * @param paraMap
     * @Author:wanghaiyang
     * @Date: 2018/2/28 15:23
     * @params paraMap
     * @Modified by:
     */
    @Override
    public PageInfo<BusCfgGlobal> listByCondition(Map<String, Object> paraMap) {
        if(paraMap.get("curentPage")!=null && !paraMap.get("curentPage").toString().equals("")){
            PageHelper.startPage(Integer.valueOf(paraMap.get("curentPage").toString()),Integer.valueOf(paraMap.get("pageSize").toString()));
        }
        List<BusCfgGlobal> busCfgTasks = mapper.listByCondition(paraMap);
        PageInfo<BusCfgGlobal> pageInfo = new PageInfo<BusCfgGlobal>(busCfgTasks);
        return pageInfo;
    }

    /**
     * 获取全局参数bean类
     *
     * @return
     * @throws Exception
     */
    @Override
    public BusCfgGlobalBean getBusCfgGlobalBean() throws Exception {
        List<BusCfgGlobal> lst = mapper.selectAll();
        if(lst==null){
            throw new Exception("未获取到全局参数");
        }

        //将全局参数表的参数转换为bean类
        BusCfgGlobalBean bean = new BusCfgGlobalBean();

        //将list结果转换为map结果，便于转换处理
        Map<String, BusCfgGlobal> map = new HashMap<>();
        for (BusCfgGlobal busCfgGlobal : lst) {
            map.put(busCfgGlobal.getGjz(), busCfgGlobal);
        }
        // 为防止初始化策略之前报错，需要判断一下
        if(map.containsKey(Constant.BUSCFGGLOBAL_AZDM)){
            //安装代码
            bean.setAzdm(map.get(Constant.BUSCFGGLOBAL_AZDM).getMrz());
        }
        if(map.containsKey(Constant.BUSCFGGLOBAL_CLSJKZDZ)){
            //存量数据单个数据块最大数据量,表记录数限制
            bean.setClsjkzdz(Long.parseLong(map.get(Constant.BUSCFGGLOBAL_CLSJKZDZ).getMrz()));
        }
        if(map.containsKey(Constant.BUSCFGGLOBAL_CLSJKLJZDZ)){
            //存量数据采集任务最大数据库连接数
            bean.setClsjkljzdz(Integer.parseInt(map.get(Constant.BUSCFGGLOBAL_CLSJKLJZDZ).getMrz()));
        }
        if(map.containsKey(Constant.BUSCFGGLOBAL_CLRWQDSJ)){
            //存量数据采集任务启动时间，单位小时（HH24）
            bean.setClrwqdsj(Integer.parseInt(map.get(Constant.BUSCFGGLOBAL_CLRWQDSJ).getMrz()));
        }
        if(map.containsKey(Constant.BUSCFGGLOBAL_CLRWJSSJ)){
            //存量数据采集任务结束时间，单位小时（HH24）
            bean.setClrwjssj(Integer.parseInt(map.get(Constant.BUSCFGGLOBAL_CLRWJSSJ).getMrz()));
        }
        if(map.containsKey(Constant.BUSCFGGLOBAL_ZLCJZQ)){
            //增量数据采集周期，单位分钟
            bean.setZlcjzq(Integer.parseInt(map.get(Constant.BUSCFGGLOBAL_ZLCJZQ).getMrz()));
        }
        if(map.containsKey(Constant.BUSCFGGLOBAL_RZJXWJZDZ)){
            //日志解析文件最大值，单位MB
            bean.setRzjxwjzdz(Integer.parseInt(map.get(Constant.BUSCFGGLOBAL_RZJXWJZDZ).getMrz()));
        }
        if(map.containsKey(Constant.BUSCFGGLOBAL_CLSCML)){
            //存量数据文件上传目录
            bean.setClscml(map.get(Constant.BUSCFGGLOBAL_CLSCML).getMrz());
        }
        if(map.containsKey(Constant.BUSCFGGLOBAL_ZLSCML)){
            //增量数据文件上传目录
            bean.setZlscml(map.get(Constant.BUSCFGGLOBAL_ZLSCML).getMrz());
        }
        if(map.containsKey(Constant.BUSCFGGLOBAL_JGXTLB)){
            //交管信息系统类别 eg:10#综合应用平台,60#集成指挥平台
            bean.setJgxtlb(map.get(Constant.BUSCFGGLOBAL_JGXTLB).getMrz());
        }
        return bean;
    }

}
