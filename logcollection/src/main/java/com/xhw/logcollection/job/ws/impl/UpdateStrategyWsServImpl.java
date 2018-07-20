package com.xhw.logcollection.job.ws.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xhw.logcollection.job.entity.BusCfgGlobal;
import com.xhw.logcollection.job.entity.BusCfgTask;
import com.xhw.logcollection.job.entity.ContextBean;
import com.xhw.logcollection.job.mapper.BusCfgGlobalMapper;
import com.xhw.logcollection.job.mapper.BusCfgTaskMapper;
import com.xhw.logcollection.job.ws.UpdateStrategyWsServ;
import com.xhw.logcollection.monitor.entity.BusStockDealStatus;
import com.xhw.logcollection.monitor.mapper.BusStockDealStatusMapper;
import com.xhw.logcollection.util.FileUtil;
import com.xhw.logcollection.util.LoadGlobalPropertiesUtil;
import com.xhw.logcollection.util.WSUtil;
import com.xhw.logcollection.util.XmlUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 日志采集全局参数webservice服务接口实现类
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-27
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
@Service
public class UpdateStrategyWsServImpl implements UpdateStrategyWsServ {

    private WSUtil wsUtil = new WSUtil();

    @Autowired
    private BusCfgGlobalMapper cfgGlobalMapper;

    @Autowired
    private BusCfgTaskMapper cfgTaskMapper;

    @Autowired
    private BusStockDealStatusMapper stockDealStatusMapper;

    /**
     * 通过webservice接口获取策略数据
     *
     * @return
     */
    @Override
    public String resultXML() throws Exception {
        //模拟返回的结果，结果格式参考“20171211公安交通管理信息安全监管系统外挂接口手册.doc”
        //      ->“2.1.1.采集配置策略查询接口”
        Gson gson = new GsonBuilder().serializeNulls().create();
        // 从文件中获取模拟报文数据
        String resultXml = FileUtil.readResourceFile("/datas/update_strategy.xml");
        String babh = LoadGlobalPropertiesUtil.getProperty("system.babh");
        String wkmac = LoadGlobalPropertiesUtil.getProperty("system.mac");
        StringBuilder xmlDoc = new StringBuilder("<?xml version='1.0' encoding='GBK'?>");
        xmlDoc.append("<root>");
        xmlDoc.append("<babh>").append(babh).append("</babh>");
        xmlDoc.append("<wkmac>").append(wkmac).append("</wkmac>");
        xmlDoc.append("</root>");

        // TODO 待调试接口
//        String rstXml = wsUtil.commonXml(ContextBean.InterfaceID.GETSTRATEGY.toString(),xmlDoc.toString(),"queryObjectRds");
//        Document document = DocumentHelper.parseText(resultXml);
//        Map<String,Object> rstMap = XmlUtil.Dom2Map(document);
//        JSONObject obj = JSONObject.fromObject(rstMap);
//
//        JSONObject globalConfig = obj.getJSONObject("body").getJSONObject("drvpara");//获取全局采集参数
//        /**
//         * 封装全局采集参数为对象,病保存至数据库
//         */
//        Iterator iterator = globalConfig.keys();
//        while (iterator.hasNext()){
//            BusCfgGlobal busCfgGlobal = new BusCfgGlobal();
//            Object key = iterator.next();
//            if(key!=null && !key.toString().equals("") && !key.toString().equals("tablist")){
//                busCfgGlobal.setCsmc(key.toString());
//                busCfgGlobal.setGjz(key.toString());
//                busCfgGlobal.setMrz(globalConfig.getString(key.toString()));
//                busCfgGlobal.setQybj("1");
//                busCfgGlobal.setGxsj(new Date());
//
//                BusCfgGlobal cfgGlobal = cfgGlobalMapper.selectByPrimaryKey(key.toString());
//                if(cfgGlobal==null){
//                    cfgGlobalMapper.insertSelective(busCfgGlobal);
//                }else{
//                    cfgGlobalMapper.updateByPrimaryKeySelective(busCfgGlobal);
//                }
//            }
//        }
//
//        JSONArray tbConfigArr = obj.getJSONObject("body").getJSONObject("drvpara").getJSONObject("tablist").getJSONArray("tab");//获取单表采集策略
//        /**
//         * 封装单表采集参数为对象集合
//         */
//        List<BusCfgTask> busCfgTasks = new ArrayList<>();
//        for (int i=0;i<tbConfigArr.size();i++){
//            BusCfgTask busCfgTask = gson.fromJson(tbConfigArr.getString(i).toString(),BusCfgTask.class);
//            busCfgTasks.add(busCfgTask);
//            Map<String,Object> paraMap = new HashMap<>();
//            paraMap.put("jgxtlb",busCfgTask.getJgxtlb());
//            paraMap.put("bm",busCfgTask.getBm());
//            List<BusCfgTask> cfgTasks = cfgTaskMapper.listByCondition(paraMap);
//            if(cfgTasks!=null && cfgTasks.size()>0){
//                cfgTaskMapper.updateByPrimaryKeySelective(busCfgTask);
//            }else {
//                cfgTaskMapper.insertSelective(busCfgTask);
//            }
//        }
        return resultXml;
    }
}
