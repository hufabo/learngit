package com.xhw.logcollection.job.service.impl;

import com.xhw.logcollection.job.entity.BusCfgTask;
import com.xhw.logcollection.job.entity.ContextBean;
import com.xhw.logcollection.job.mapper.BusCfgTaskMapper;
import com.xhw.logcollection.job.service.StockSplitTaskServ;
import com.xhw.logcollection.monitor.entity.BusStockBreakPointInfo;
import com.xhw.logcollection.monitor.entity.BusStockDealStatus;
import com.xhw.logcollection.monitor.mapper.BusStockBreakPointInfoMapper;
import com.xhw.logcollection.monitor.mapper.BusStockDealStatusMapper;
import com.xhw.logcollection.system.entity.DataSource;
import com.xhw.logcollection.system.mapper.DataSourceMapper;
import com.xhw.logcollection.util.DateUtil;
import com.xhw.logcollection.util.JdbcUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  存量数据采集任务拆分服务接口实现类
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-03-07
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
@Service
public class StockSplitTaskServImpl implements StockSplitTaskServ {

    @Autowired
    private BusCfgTaskMapper cfgTaskMapper;

    @Autowired
    private DataSourceMapper dataSourceMapper;

    @Autowired
    private BusStockBreakPointInfoMapper breakPointInfoMapper;

    @Autowired
    private BusStockDealStatusMapper stockDealStatusMapper;

    /**
     * 拆分存量采集任务
     *
     * @param beanTask 存量拆分任务
     * @throws Exception
     */
    @Override
    public void splitTask(BusCfgTask beanTask) throws Exception {
        System.out.println("存量数据采集任务拆分服务接口实现类");
        //、拆分采集任务（根据数据量拆分，但子任务为时间戳？）
        // 拆分结果 存放到 bus_stock_break_point_info（存量数据块断点表）
        // 更新bus_cfg_task（单表日志采集参数表）的 CLWCBJ （存量数据采集完成标记。0-未启动，1-正在执行，2-已完成）=1

        // 1、 获取采集任务(采集任务有上层框架负责获取)
        // 2、 获取数据库信息和表信息
        DataSource dataSource = null;
        try {
            dataSource = new DataSource();
            dataSource.setJgxtlb(beanTask.getJgxtlb());
            dataSource.setJlzt("1");
            dataSource = dataSourceMapper.selectOne(dataSource);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("目标数据源配置异常，存量采集任务中止！系统类别（"+ beanTask.getJgxtlb() +"）");
        }
        if(dataSource == null){
            throw new RuntimeException("目标数据源未配置，存量采集任务中止！系统类别（"+ beanTask.getJgxtlb() +"）");
        }
        // 3、 连接目标数据库，查询分块信息
        // 4、 入库
        String drivername = "oracle.jdbc.driver.OracleDriver";
//        String url = "jdbc:oracle:thin:@"+ dataSource.getIp() +":"+ dataSource.getPort() +":"+ dataSource.getSid();
        String url;
        if("0".equals(dataSource.getRac())){
            url = "jdbc:oracle:thin:@"+ dataSource.getIp() +":"+ dataSource.getPort() +":"+ dataSource.getSid();
        }else{
            url = "jdbc:oracle:thin:@(DESCRIPTION =(ADDRESS = (PROTOCOL = TCP)(HOST = "+ dataSource.getIp()
                    +")(PORT = "+ dataSource.getPort() +"))(CONNECT_DATA = (SERVER = DEDICATED)(SERVICE_NAME = "+ dataSource.getSid() +")))";
        }
        String user = dataSource.getOraUser();
        String password = dataSource.getOraPassword();
        Connection connection = JdbcUtil.getConnection(drivername, url, user, password);
        String oraSchema = dataSource.getOraSchema();
        beanTask.setSchema(oraSchema);
        String sql = getBreakpointSql(beanTask);
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()) {
            // ①存量数据块断点表。写入系统类别、表名、数据块编号、时间戳起、时间戳止，当前时间戳（空）、完成标记（ 0-未完成）、更新时间
            BusStockBreakPointInfo breakPointInfo = new BusStockBreakPointInfo();
            breakPointInfo.setJgxtlb(beanTask.getJgxtlb());
            breakPointInfo.setBm(beanTask.getBm());
            BigDecimal sn = rs.getBigDecimal("sn");
            breakPointInfo.setSjkbh(sn);
            Timestamp min_time = rs.getTimestamp("min_time");
            breakPointInfo.setSjcq(min_time);
            Timestamp max_time = rs.getTimestamp("max_time");
            breakPointInfo.setSjcz(max_time);
            // 0-未完成，1-已完成
            breakPointInfo.setWcbj("0");
            breakPointInfo.setGxsj(new Date());
            breakPointInfoMapper.insertSelective(breakPointInfo);
        }
        JdbcUtil.closeResource(rs,pstmt,null);
        // 查询采集数据总量
        String sql2 = getTotalCountSql(beanTask);
        pstmt = connection.prepareStatement(sql2);
        rs = pstmt.executeQuery();
        rs.next();
        BigDecimal total = rs.getBigDecimal(1);
        JdbcUtil.closeResource(rs,pstmt,connection);
        // ②单表日志采集参数表。更新“存量数据采集完成标记”为“1-正在执行”
        beanTask.setClwcbj("1");
        cfgTaskMapper.updateByPrimaryKey(beanTask);
        // ③存量数据处理状态表。更新“采集状态”为“1-正在执行”，“初次启动时间”、“最近启动时间”、“ 更新时间”为当前时间，“数据总量”为该表查询到的存量数据总量。
        BusStockDealStatus stockDealStatus = new BusStockDealStatus();
        stockDealStatus.setJgxtlb(beanTask.getJgxtlb());
        stockDealStatus.setBm(beanTask.getBm());
        stockDealStatus.setCjzt("1");
        stockDealStatus.setCcqdsj(new Date());
        stockDealStatus.setZjqdsj(new Date());
        stockDealStatus.setGxsj(new Date());
        stockDealStatus.setSjzl(total);
        // 如果数据总量等于0，那么采集任务直接完成即可
        if(stockDealStatus.getSjzl().longValue() == 0){
            stockDealStatus.setCjzt("2");
            stockDealStatus.setCjwcsj(new Date());
        }
        stockDealStatusMapper.updateByPrimaryKeySelective(stockDealStatus);
    }

    /**
     * 根据任务参数里面表名等信息得到查询数据总量的SQL
     * @author konggang
     * @date 2018/3/13 10:28
     */
    private String getTotalCountSql(BusCfgTask task){
        /*
        select count(0) total from test_collect_table
        where 1=1
        and time > (cast(to_date('2017-12-30 00:00:00','yyyy-mm-dd hh24:mi:ss') as timestamp))
        and log is null
         */
        String tableName = task.getSchema().concat(".").concat(task.getBm());
        String timeField = task.getSjczd();
        String beginDate = null;
        Date clqsrq = task.getClqsrq();
        if(clqsrq != null){
            beginDate = DateUtil.date2Str(clqsrq, "yyyy-MM-dd");
        }
        String queryString = task.getClgltj();
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("select count(0) total from ").append(tableName);
        sqlBuilder.append(" where 1=1 ");
        if(StringUtils.isNotEmpty(beginDate)){
            sqlBuilder.append(" and ").append(timeField).append(" > (cast(to_date('").append(beginDate).append(" 00:00:00','yyyy-mm-dd hh24:mi:ss') as timestamp))");
        }
        if(StringUtils.isNotEmpty(queryString)){
            sqlBuilder.append(" and ").append(queryString);
        }
        return sqlBuilder.toString();
    }

    /**
     * 根据任务参数里面表名等信息得到查询存量采集数据块的SQL
     * @author konggang
     * @date 2018/3/12 17:25
     */
    private String getBreakpointSql(BusCfgTask task){
        /*
        select rn2 sn,min(time) min_time,max(time) max_time from (
          select t.time,ceil(rownum / 10)as rn2 from (
            select time as time from test_collect_table
            where 1=1
            -- and time > (cast(to_date('2017-12-30 00:00:00','yyyy-mm-dd hh24:mi:ss') as timestamp))
            -- and log is null
            order by time asc
          ) t
        ) t2
        group by rn2
        order by rn2
         */
        long maxSize = ContextBean.getBusCfgGlobalBean().getClsjkzdz();
        String tableName = task.getSchema().concat(".").concat(task.getBm());
        String timeField = task.getSjczd();
        String beginDate = null;
        Date clqsrq = task.getClqsrq();
        if(clqsrq != null){
            beginDate = DateUtil.date2Str(clqsrq, "yyyy-MM-dd");
        }
        String queryString = task.getClgltj();
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(" select rn2 sn,min(time) min_time,max(time) max_time from ( ");
        sqlBuilder.append(" select t.time,ceil(rownum/").append(maxSize).append(")as rn2 from ( ");
        sqlBuilder.append(" select ").append(timeField).append(" as time").append(" from ").append(tableName);
        sqlBuilder.append(" where 1=1 ");
        if(StringUtils.isNotEmpty(beginDate)){
            sqlBuilder.append(" and ").append(timeField).append(" > (cast(to_date('").append(beginDate).append(" 00:00:00','yyyy-mm-dd hh24:mi:ss') as timestamp))");
        }
        if(StringUtils.isNotEmpty(queryString)){
            sqlBuilder.append(" and ").append(queryString);
        }
        sqlBuilder.append(" order by ").append(timeField).append(" asc");
        sqlBuilder.append(" ) t ) t2 group by rn2 order by rn2 ");
        return sqlBuilder.toString();
    }
}
