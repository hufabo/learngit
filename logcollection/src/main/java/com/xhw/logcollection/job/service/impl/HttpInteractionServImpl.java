package com.xhw.logcollection.job.service.impl;

import com.xhw.logcollection.job.service.HttpInteractionServ;
import com.xhw.logcollection.job.ws.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *  http信息交互服务接口实现类
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-03-06
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
@Service
public class HttpInteractionServImpl implements HttpInteractionServ {

    @Autowired
    private RunningStatusWsServ runningStatusWsServ;    //采集软件运行状态
    @Autowired
    private StockFileStatusWsServ stockFileStatusWsServ;    //存量数据处理状态
    @Autowired
    private StockBreakPointInfoWsServ stockBreakPointInfoWsServ;    //存量数据块断点信息
    @Autowired
    private IncrBreakPointInfoWsServ incrBreakPointInfoWsServ;  //增量数据断点信息
    @Autowired
    private StockFileInfoWsServ stockFileInfoWsServ;    //存量数据文件信息
    @Autowired
    private IncrFileInfoWsServ incrFileInfoWsServ;  //增量数据文件信息
    @Autowired
    private AuditDdlWsServ auditDdlWsServ;  //  DDL 数据审计

    /**
     * http信息交互接口
     *
     * @throws Exception
     */
    @Override
    public void httpInteraction() throws Exception {
        //调用webservice接口上传 包括存量数据处理状态表、存量数据块断点表、增量数据断点表、存量数据文件表、
        //    增量数据文件表、DDL 数据审计表、采集软件运行状态监控表、数据采集情况统计表
        // TODO 待调试，根据需要进行调整

        //RunningStatusWsServ  采集软件运行状态
        runningStatusWsServ.reportRunningStatus();

        //StockFileStatusWsServ 存量数据处理状态
        stockFileStatusWsServ.reportStockFileStatus();

        //StockBreakPointInfoWsServ 存量数据块断点信息
        stockBreakPointInfoWsServ.reportStockBreakPointInfo();

        //IncrBreakPointInfoWsServ 增量数据断点信息
        incrBreakPointInfoWsServ.reportIncrBreakPointInfo();

        //StockFileInfoWsServ 存量数据文件信息
        stockFileInfoWsServ.reportStockFileInfo();

        //IncrFileInfoWsServ  增量数据文件信息
        incrFileInfoWsServ.reportIncrFileInfo();

        //AuditDdlWsServ  DDL 数据审计
        auditDdlWsServ.reportAuditDdl();
    }
}
