package com.xhw.logcollection.job.service.impl;

import com.xhw.logcollection.job.entity.BusCfgGlobal;
import com.xhw.logcollection.job.entity.BusCfgTask;
import com.xhw.logcollection.job.entity.ContextBean;
import com.xhw.logcollection.job.service.BusCfgGlobalServ;
import com.xhw.logcollection.job.service.BusCfgTaskServ;
import com.xhw.logcollection.job.service.UpdateStrategyServ;
import com.xhw.logcollection.job.ws.UpdateStrategyWsServ;
import com.xhw.logcollection.util.Constant;
import com.xhw.logcollection.util.FileUtil;
import com.xhw.logcollection.util.SpringUtil;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 策略更新接口实现类
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-27
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
@Service
public class UpdateStrategyServImpl implements UpdateStrategyServ {

    @Autowired
    private UpdateStrategyWsServ updateStrategyWsServ;

    @Autowired
    private BusCfgGlobalServ cfgGlobalServ;
    @Autowired
    private BusCfgTaskServ cfgTaskServ;

    /**
     * 更新策略
     *
     * @throws Exception
     */
    @Override
    public void updateStrategy() throws Exception {
        // 获取更新的数据
        String resultXml = updateStrategyWsServ.resultXML();

        //解析xml
        SAXReader reader = new SAXReader();
        //Document document = reader.read(resultXml);
        Document document = DocumentHelper.parseText(resultXml);

        //判断获取的结果是否正确
        Node codeNode = document.selectSingleNode("/root/head/code");
        if(codeNode!=null && codeNode.getText()!=null
                && codeNode.getText().trim().equals("1")){ //接口成功返回
            //解析 日志采集全局参数表 参数
            this.parse4BusCfgGlobal(document);

            //更新 缓存的日志采集全局参数表
            ContextBean.setBusCfgGlobalBean(cfgGlobalServ.getBusCfgGlobalBean());

            //解析 单表采集任务
            this.parse4BusCfgTask(document);
        } else {
            //接口有错误
            String msg = "接口更新错误，原因未知！";
            Node msgNode = document.selectSingleNode("/root/head/message");
            if(msgNode != null && msgNode.getText() != null){
                msg = msgNode.getText();
            }
            throw new Exception(msg);
        }
    }

    //解析 日志采集全局参数表 参数
    private void parse4BusCfgGlobal(Document document) throws Exception{
        Element drvparaEle = (Element)document.selectSingleNode("/root/body/drvpara");

        //根据定义的 日志采集全局参数表 关键字 解析配置数据
        Map<String ,String> map = Constant.BUSCFGGLOBAL_MAP;
        List<BusCfgGlobal> lst = new ArrayList<>(map.size());
        for (Map.Entry<String ,String> entry : map.entrySet()) {
            //System.out.println(entry.getKey()+","+entry.getValue()
            //        +","+drvparaEle.element(entry.getKey().toString()).getTextTrim());

            BusCfgGlobal cfgGlobal = new BusCfgGlobal();
            cfgGlobal.setGjz(entry.getKey());    //关键字
            cfgGlobal.setCsmc(entry.getValue()); //参数名称
            cfgGlobal.setMrz(drvparaEle.element(entry.getKey()).getTextTrim()); //默认值
            cfgGlobal.setQybj("1"); //启用标记，0-否，1-是
            cfgGlobal.setGxsj(new Date()); //初始化更新时间

            lst.add(cfgGlobal);
        }

        //更新参数到 日志采集全局参数表
        cfgGlobalServ.batchDelAndSave(lst);
    }

    //解析 单表日志采集任务
    private void parse4BusCfgTask(Document document) throws Exception{
        Element tablistEle = (Element)document.selectSingleNode("/root/body/drvpara/tablist");

        List<Element> lstSource = tablistEle.selectNodes("tab");
        List<BusCfgTask> lst = new ArrayList<>(lstSource.size());
        for (Element ele : lstSource) {
            BusCfgTask cfgTask = new BusCfgTask();
            cfgTask.setJgxtlb(ele.elementTextTrim(Constant.BUSCFGTASK_JGXTLB)); //系统类别代码
            cfgTask.setBm(ele.elementTextTrim(Constant.BUSCFGTASK_BM));	        //表名
            cfgTask.setBmms(ele.elementTextTrim(Constant.BUSCFGTASK_BMMS));	    //表名描述
            cfgTask.setClcjbj(ele.elementTextTrim(Constant.BUSCFGTASK_CLCJBJ));	//存量数据采集标记，0-不采集，1-采集
            cfgTask.setSjczd(ele.elementTextTrim(Constant.BUSCFGTASK_SJCZD));    //时间戳字段
            cfgTask.setClqsrq(this.parseStrDate(ele.elementTextTrim(Constant.BUSCFGTASK_CLQSRQ)));  //存量数据起始日期
            cfgTask.setClgltj(ele.elementTextTrim(Constant.BUSCFGTASK_CLGLTJ));  //存量数据过滤条件
            cfgTask.setClwcbj(ele.elementTextTrim(Constant.BUSCFGTASK_CLWCBJ));  //存量数据采集完成标记,0-未启动，1-正在执行，2-已完成
            cfgTask.setZlkhdgllx(ele.elementTextTrim(Constant.BUSCFGTASK_ZLKHDGLLX));    //增量数据客户端过滤类型
            cfgTask.setZlinsert(ele.elementTextTrim(Constant.BUSCFGTASK_ZLINSERT));      //是否采集“insert”增量数据，0-否，1-是
            cfgTask.setZlupdate(ele.elementTextTrim(Constant.BUSCFGTASK_ZLUPDATE));      //是否采集“update”增量数据
            cfgTask.setZldelete(ele.elementTextTrim(Constant.BUSCFGTASK_ZLDELETE));      //是否采集“delete”增量数据
            cfgTask.setCjsj(this.parseStrDate(ele.elementTextTrim(Constant.BUSCFGTASK_CJSJ)));  //策略创建时间
            cfgTask.setGxsj(this.parseStrDate(ele.elementTextTrim(Constant.BUSCFGTASK_GXSJ)));  //策略更新时间

            lst.add(cfgTask);
        }

        //更新参数到 单表日志采集参数表
        cfgTaskServ.batchDelAndSave(lst);
    }

    /**
     * 转换日期格式
     */
    private Date parseStrDate(String strDate) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(strDate);
    }
}
