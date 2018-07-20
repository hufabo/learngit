package com.xhw.logcollection.job.service.impl;

import com.xhw.logcollection.job.entity.BusCfgTask;
import com.xhw.logcollection.job.mapper.BusCfgTaskMapper;
import com.xhw.logcollection.job.service.StockExecuteTaskServ;
import com.xhw.logcollection.model.dto.LogParser4StockDto;
import com.xhw.logcollection.monitor.entity.BusStockBreakPointInfo;
import com.xhw.logcollection.monitor.entity.BusStockFileInfo;
import com.xhw.logcollection.monitor.mapper.BusStockBreakPointInfoMapper;
import com.xhw.logcollection.monitor.mapper.BusStockDealStatusMapper;
import com.xhw.logcollection.monitor.mapper.BusStockFileInfoMapper;
import com.xhw.logcollection.system.entity.DataSource;
import com.xhw.logcollection.system.mapper.DataSourceMapper;
import com.xhw.logcollection.util.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *  存量数据采集断点任务服务接口实现类
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-03-07
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
@Service
public class StockExecuteTaskServImpl implements StockExecuteTaskServ {

    private Logger logger = LoggerFactory.getLogger(StockExecuteTaskServImpl.class);

    @Autowired
    private BusCfgTaskMapper cfgTaskMapper;

    @Autowired
    private DataSourceMapper dataSourceMapper;

    @Autowired
    private BusStockFileInfoMapper stockFileInfoMapper;

    @Autowired
    private BusStockBreakPointInfoMapper breakPointInfoMapper;

    @Autowired
    private BusStockDealStatusMapper stockDealStatusMapper;

    /**
     * 存量采集任务
     *
     * @param beanTask 断点任务
     * @throws Exception
     */
    @Override
    public void executeTask(BusStockBreakPointInfo beanTask) throws Exception {
        System.out.println("存量数据采集断点任务服务接口实现类");
        String jgxtlb = beanTask.getJgxtlb();
        //1、根据断点表信息 和 单表日志采集参数表 的信息 组装为采集的SQL语句
        BusCfgTask cfgTask = new BusCfgTask();
        cfgTask.setJgxtlb(jgxtlb);
        cfgTask.setBm(beanTask.getBm());
        cfgTask = cfgTaskMapper.selectByPrimaryKey(cfgTask);
        //2、从“目标数据库连接配置表”获取数据库连接信息
        DataSource dataSource = getDataSource(jgxtlb);
        //3、连接目标数据库
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
        cfgTask.setSchema(oraSchema);
        //4、执行SQL语句，获取表字段名称和数据（注意：日期输出的格式，二进制格式、clob等字段的支持）
        String breakDataSql = getBreakDataSql(cfgTask, beanTask.getSjcq(), beanTask.getSjcz());
        PreparedStatement pstmt = connection.prepareStatement(breakDataSql);
        ResultSet rs = pstmt.executeQuery();
        List<Map<String, String>> datas = new ArrayList<>();
        while(rs.next()) {
            Map<String, String> data = JdbcUtil.getResultMap(rs);
            datas.add(data);
        }
        //5、按约定格式生成 日志解析文件（注意：文件大小的限制）
        LogParser4StockDto stockDto = new LogParser4StockDto();
        // 文件类型， 1 表示存量数据日志解析文件1
        stockDto.setOraType("1");
        stockDto.setOraXtlb(jgxtlb);
        stockDto.setOraUser(user);
        // 抽取客户端， 存量数据为 jdbc
        stockDto.setOraClient("jdbc");
        stockDto.setOraTime(DateUtil.dateNow2str("yyyy-MM-dd HH:mm:ss"));
        // schema 对象名,对于oracle应该就是用户名
        stockDto.setOraSchema(dataSource.getOraSchema());
        stockDto.setTabName(beanTask.getBm());
        stockDto.setDatas(datas);

        while(!stockDto.isHasFinished()){
            String timeField = cfgTask.getSjczd();
            stockDto.write2File(timeField);
        }
        List<Map<String, Object>> segments = stockDto.getSegments();
        for(Map<String, Object> seg:segments){
            File file = (File) seg.get("file");
            int count = (int) seg.get("count");
            String beginTime = (String) seg.get("beginTime");
            String endTime = (String) seg.get("endTime");
            //6、记录入库：存量数据文件表
            BusStockFileInfo fileInfo = new BusStockFileInfo();
            fileInfo.setJgxtlb(jgxtlb);
            fileInfo.setBm(beanTask.getBm());
            fileInfo.setWjm(file.getName());
            fileInfo.setSjkbh(beanTask.getSjkbh());
            // 文件生成MD5值
            FileInputStream fis = new FileInputStream(file);
            String md5Hex = DigestUtils.md5Hex(fis);
            fileInfo.setMd(md5Hex);
            fis.close();
            // 文件大小，单位：KB
            fileInfo.setWjdx(BigDecimal.valueOf(file.length()/1024));
            fileInfo.setSlj(BigDecimal.valueOf(count));
            fileInfo.setSjcq(DateUtil.str2Date(beginTime,"yyyy-MM-dd HH:mm:ss"));
            fileInfo.setSjcz(DateUtil.str2Date(endTime,"yyyy-MM-dd HH:mm:ss"));
            fileInfo.setWjzt(Constant.STOCK_FILE_STUS_COLLECTED);
            fileInfo.setScsj(new Date());
            fileInfo.setGxsj(new Date());
            // 错误状态。0-无错误，1-出错
            fileInfo.setCwzt("0");
            stockFileInfoMapper.insertSelective(fileInfo);
        }

        //7、状态变更：存量数据块断点表、存量数据处理状态表
        beanTask.setDqsjc(beanTask.getSjcz());
        beanTask.setGxsj(new Date());
        beanTask.setWcbj("1"); // 完成标记，0-未完成，1-已完成
        breakPointInfoMapper.updateByPrimaryKeySelective(beanTask);
    }

    @Override
    public void executeByFile(String fileName) throws Exception {
        // 1、根据文件名获取采集相关断点信息
        // 2、重新采集相关数据
        // 3、采集完成更新状态
        BusStockFileInfo fileInfo = new BusStockFileInfo();
        fileInfo.setWjm(fileName);
        fileInfo = stockFileInfoMapper.selectOne(fileInfo);
        String jgxtlb = fileInfo.getJgxtlb();
        // 根据文件信息找到对应的单表配置信息
        BusCfgTask cfgTask = new BusCfgTask();
        cfgTask.setJgxtlb(jgxtlb);
        cfgTask.setBm(fileInfo.getBm());
        cfgTask = cfgTaskMapper.selectByPrimaryKey(cfgTask);
        DataSource dataSource = getDataSource(jgxtlb);
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
        cfgTask.setSchema(oraSchema);
        String breakDataSql = getBreakDataSql(cfgTask, fileInfo.getSjcq(), fileInfo.getSjcz());
        BusStockFileInfo file = new BusStockFileInfo();
        file.setWjm(fileName);
        try {
            PreparedStatement pstmt = connection.prepareStatement(breakDataSql);
            ResultSet rs = pstmt.executeQuery();
            List<Map<String, String>> datas = new ArrayList<>();
            while(rs.next()) {
                Map<String, String> data = JdbcUtil.getResultMap(rs);
                datas.add(data);
            }
            //5、按约定格式生成 日志解析文件（注意：文件大小的限制）
            LogParser4StockDto stockDto = new LogParser4StockDto();
            // 文件类型， 1 表示存量数据日志解析文件1
            stockDto.setOraType("1");
            stockDto.setOraXtlb(jgxtlb);
            stockDto.setOraUser(user);
            // 抽取客户端， 存量数据为 jdbc
            stockDto.setOraClient("jdbc");
            stockDto.setOraTime(DateUtil.dateNow2str("yyyy-MM-dd HH:mm:ss"));
            // schema 对象名,对于oracle应该就是用户名
            stockDto.setOraSchema(dataSource.getOraSchema());
            stockDto.setTabName(fileInfo.getBm());
            stockDto.setDatas(datas);
            // 根据文件来的采集不需要考虑文件大小限制了
            String stockXml = XmlUtil.beanToXml(stockDto, LogParser4StockDto.class);
            String fileDir = LoadGlobalPropertiesUtil.getProperty(Constant.FILE_PUT_STOCK_DIR_AWAIT);
            FileUtil.writeFile(fileDir, fileName, stockXml);
            /*
            重新采集成功，“文件状态” 更新为“ 1-采集”
             */
            file.setWjzt("1");
            file.setGxsj(new Date());
        } catch (IOException e) {
            e.printStackTrace();
            /*
            TODO 暂时简单处理出错情况，没有区分什么错误
            1、因数据库表未授权、数据库日志文件共享目录不可访问等原因导致数据重新采集失败，但通过人工干预可以排除故障的，
            不更新“文件状态”， 只更新“错误状态”、“错误信息描述”、“出错时间”信息。
            2、因数据不存在、数据库日志文件不存在等原因导致数据重新采集失败，无法恢复的，“文件状态”更新为“7-数据丢失”，
            并更新“错误状态”、“错误信息描述”、“出错时间”信息。
             */
            file.setWjzt(Constant.STOCK_FILE_STUS_DATA_LOST);
            file.setGxsj(new Date());
            file.setCwzt("1"); //错误状态。0-无错误，1-出错
            file.setCcsj(new Date());
            file.setCwxxms(e.getMessage());
        }finally {
            stockFileInfoMapper.updateFileInfo(file);
        }
    }

    private DataSource getDataSource(String jgxtlb) {
        DataSource dataSource = null;
        try {
            dataSource = new DataSource();
            dataSource.setJgxtlb(jgxtlb);
            dataSource.setJlzt("1");
            dataSource = dataSourceMapper.selectOne(dataSource);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("目标数据源配置异常，存量采集任务中止！系统类别（"+ jgxtlb +"）");
        }
        if(dataSource == null){
            throw new RuntimeException("目标数据源未配置，存量采集任务中止！系统类别（"+ jgxtlb +"）");
        }
        return dataSource;
    }

    private String getBreakDataSql(BusCfgTask cfgTask, Date sjcq2, Date sjcz2) {
        String tableName = cfgTask.getSchema().concat(".").concat(cfgTask.getBm());
        String timeField = cfgTask.getSjczd();
        Date clqsrq = cfgTask.getClqsrq();
        String queryString = cfgTask.getClgltj();
        Date sjcq = sjcq2;
        Date sjcz = sjcz2;
        return getBreakDataSql(tableName, timeField, clqsrq, queryString, sjcq, sjcz);
    }

    private String getBreakDataSql(String tableName, String timeField, Date startDate, String queryString, Date beginTime, Date endTime) {
        /*
        select * from test_collect_table
        where 1=1
        and time > (cast(to_date('2017-12-30 00:00:00','yyyy-mm-dd,hh24:mi:ss') as timestamp))
        and log is null
        and time >= min_time
        and time <= max_time
         */
        String beginDate = null;
        String min_time = null;
        String max_time = null;
        if(startDate != null){
            beginDate = DateUtil.date2Str(startDate, "yyyy-MM-dd");
        }
        if(beginTime != null){
            min_time = DateUtil.date2Str(beginTime, "yyyy-MM-dd HH:mm:ss");
        }
        if(endTime != null){
            max_time = DateUtil.date2Str(endTime, "yyyy-MM-dd HH:mm:ss");
        }
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("select * from ").append(tableName);
        sqlBuilder.append(" where 1=1 ");
        if(StringUtils.isNotEmpty(beginDate)){
            sqlBuilder.append(" and ").append(timeField).append(" > (cast(to_date('").append(beginDate).append(" 00:00:00','yyyy-mm-dd,hh24:mi:ss') as timestamp))");
        }
        if(StringUtils.isNotEmpty(queryString)){
            sqlBuilder.append(" and ").append(queryString);
        }
        sqlBuilder.append(" and ").append(timeField).append(" >= (cast(to_date('").append(min_time).append("','yyyy-mm-dd,hh24:mi:ss') as timestamp))");
        sqlBuilder.append(" and ").append(timeField).append(" <= (cast(to_date('").append(max_time).append("','yyyy-mm-dd,hh24:mi:ss') as timestamp))");
        return sqlBuilder.toString();
    }
}
