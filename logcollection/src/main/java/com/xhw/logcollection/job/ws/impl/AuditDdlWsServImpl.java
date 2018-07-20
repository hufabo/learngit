package com.xhw.logcollection.job.ws.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xhw.logcollection.job.entity.BusSyncTb;
import com.xhw.logcollection.job.entity.ContextBean;
import com.xhw.logcollection.job.mapper.BusSyncTbMapper;
import com.xhw.logcollection.job.ws.AuditDdlWsServ;
import com.xhw.logcollection.model.dto.InfomationWriterDto;
import com.xhw.logcollection.monitor.entity.BusAuditDdl;
import com.xhw.logcollection.monitor.mapper.BusAuditDdlMapper;
import com.xhw.logcollection.util.UUIDGenerator;
import com.xhw.logcollection.util.WSUtil;
import com.xhw.logcollection.util.XmlUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 *  DDL 数据审计webservice服务接口实现类
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-03-06
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
@Service
public class AuditDdlWsServImpl implements AuditDdlWsServ {

    @Autowired
    private BusAuditDdlMapper busAuditDdlMapper;

    @Autowired
    private BusSyncTbMapper syncTbMapper;

    private WSUtil wsUtil = new WSUtil();

    /**
     * DDL 数据审计信息上报接口
     *
     * @throws Exception
     */
    @Override
    public boolean reportAuditDdl() throws Exception {
        //接口参考 “20171211公安交通管理信息安全监管系统外挂接口手册.doc”
        //      ->“2.3.8. DDL数据审计写入接口”
        System.out.println("2.3.8. DDL数据审计写入接口");
        BusSyncTb syncTb = new BusSyncTb();
        List<BusAuditDdl> auditDdlList = null;
        InfomationWriterDto dto = new InfomationWriterDto();
        Map<String,String> rstMap = null;//初始化同步结果
        boolean flag = false;//初始化同步标识

        //获取数据审计最新一条同步记录
        Map<String,Object> paraMap = new HashMap<>();
        paraMap.put("tblx","1");
        BusSyncTb recentSyncTb = syncTbMapper.getRecent(paraMap);
        if(recentSyncTb==null){//获取所有数据审计的记录
            auditDdlList = busAuditDdlMapper.listSync(null);
        }else {//获取最新的数据审计记录
            paraMap.put("gxsj",recentSyncTb.getGxsj());
            paraMap.put("id",recentSyncTb.getTbbzw());
            auditDdlList = busAuditDdlMapper.listSync(paraMap);
        }

        if(auditDdlList==null ||auditDdlList.size()==0){
            return false;
        }

        String tbpch = UUIDGenerator.getUUID();//数据同步批次号
        syncTb.setTbpch(tbpch);
        syncTb.setTbbzw(new BigDecimal(auditDdlList.get(0).getId()));
        syncTb.setTblx("1");
        syncTb.setGxsj(auditDdlList.get(0).getGxsj());
        syncTb.setTbzl(auditDdlList.size());
        syncTb.setTbsjl(0);
        syncTbMapper.insertSelective(syncTb);//初始化同步信息

        if(auditDdlList.size()>1000){//总数据量大于一千分批次上传
            int count = auditDdlList.size()/1000 + (auditDdlList.size()%1000>0?1:0);//获取页数
            for(int i = 1;i <= count;i++){
                PageHelper.startPage(count,1000);
                PageInfo<BusAuditDdl> pageInfo = new PageInfo<BusAuditDdl>(auditDdlList);
                dto.setAuditDdls(pageInfo.getList());
                String xmlDoc = XmlUtil.beanToXml(dto,InfomationWriterDto.class);
                rstMap = wsUtil.sync(ContextBean.InterfaceID.AUDIDDL.toString(),"writeObjectRds",xmlDoc);
                flag = rstMap.get("code").toString().equals("1")?true:false;
                syncTb.setRstCode(rstMap.get("code").toString());
                syncTb.setRstMsg(rstMap.get("message").toString());
                syncTb.setRstKeyStr(rstMap.get("keystr").toString());
                if(flag && (count*1000+pageInfo.getList().size())==auditDdlList.size()){//最后一页数据同步完成
                    int size = pageInfo.getList().size();
                    syncTb.setTbsjl(auditDdlList.size());
                    syncTb.setTbbzw(new BigDecimal(pageInfo.getList().get(size-1).getId()));
                    syncTb.setGxsj(new Date());
                    syncTbMapper.updateByPrimaryKeySelective(syncTb);
                }else if(flag){//第count-1页数据同步完成
                    int listCnt = pageInfo.getList().size();
                    syncTb.setTbbzw(new BigDecimal(pageInfo.getList().get(999).getId()));
                    syncTb.setTbsjl(count*1000+pageInfo.getList().size());
                    syncTb.setGxsj(pageInfo.getList().get(listCnt-1).getGxsj());
                    syncTbMapper.updateByPrimaryKeySelective(syncTb);
                }else {//同步失败
                    syncTbMapper.updateByPrimaryKeySelective(syncTb);
                    return false;
                }
            }

        }else {//总数据量小于等于1000一次性上传
            dto.setAuditDdls(auditDdlList);
            String xmlDoc = XmlUtil.beanToXml(dto,InfomationWriterDto.class);
            rstMap = wsUtil.sync(ContextBean.InterfaceID.AUDIDDL.toString(),"writeObjectRds",xmlDoc);
            flag = rstMap.get("code").toString().equals("1")?true:false;
            syncTb.setRstCode(rstMap.get("code").toString());
            syncTb.setRstMsg(rstMap.get("message").toString());
            syncTb.setRstKeyStr(rstMap.get("keystr").toString());
            if(flag){//同步成功
                int size = auditDdlList.size();
                syncTb.setTbbzw(new BigDecimal(auditDdlList.get(size-1).getId()));
                syncTb.setTbsjl(auditDdlList.size());
                syncTb.setGxsj(new Date());
                syncTbMapper.updateByPrimaryKeySelective(syncTb);
            }else {//同步失败
                syncTbMapper.updateByPrimaryKeySelective(syncTb);
                return false;
            }
        }

        return flag;
    }


}
