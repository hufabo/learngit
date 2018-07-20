package com.xhw.logcollection.job.service.impl;

import com.qinhuait.agent.AgentClient;
import com.sun.xfile.XFile;
import com.xhw.logcollection.job.entity.ContextBean;
import com.xhw.logcollection.job.service.RunlogServ;
import com.xhw.logcollection.monitor.entity.BusIncrementFileInfo;
import com.xhw.logcollection.monitor.entity.BusMonitorRunlog;
import com.xhw.logcollection.monitor.entity.BusStockDealStatus;
import com.xhw.logcollection.monitor.mapper.BusIncrementFileInfoMapper;
import com.xhw.logcollection.monitor.mapper.BusMonitorRunlogMapper;
import com.xhw.logcollection.monitor.mapper.BusStockDealStatusMapper;
import com.xhw.logcollection.monitor.service.BusMonitorRunlogServ;
import com.xhw.logcollection.system.entity.Agent;
import com.xhw.logcollection.system.entity.DataSource;
import com.xhw.logcollection.system.entity.ShareProtocol;
import com.xhw.logcollection.system.mapper.AgentMapper;
import com.xhw.logcollection.system.mapper.DataSourceMapper;
import com.xhw.logcollection.system.mapper.ShareProtocolMapper;
import com.xhw.logcollection.util.Constant;
import com.xhw.logcollection.util.JdbcUtil;
import com.xhw.logcollection.util.LoadGlobalPropertiesUtil;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author 孔纲
 * @Date 2018/3/27
 */
@Service
public class RunlogServImpl implements RunlogServ{

    @Autowired
    private BusMonitorRunlogMapper runlogMapper;

    @Autowired
    private BusMonitorRunlogServ runlogServ;

    @Autowired
    private DataSourceMapper dataSourceMapper;

    @Autowired
    private ShareProtocolMapper shareProtocolMapper;

    @Autowired
    private AgentMapper agentMapper;

    @Autowired
    private BusStockDealStatusMapper dealStatusMapper;

    @Autowired
    private BusIncrementFileInfoMapper incrementFileInfoMapper;

    @Override
    public void snapshot(String xtlb) {
        BusMonitorRunlog runlog = new BusMonitorRunlog();
        String babh = LoadGlobalPropertiesUtil.getProperty("system.babh");
        runlog.setBabh(babh);
        runlog.setJgxtlb(xtlb);
        // 1 检查目标数据库
        checkDatabase(xtlb, runlog);
        // 2 检查文件服务器
        checkFileServer(runlog);
        // 3 增量方式检查  agent 或者 共享目录
        checkShareServer(xtlb, runlog);
        // 4 存量数据采集任务状态
        checkStockStatus(xtlb, runlog);
        // 5 增量任务采集运行状态
        checkIncrStatus(xtlb, runlog);
        // 6 保存
        runlog.setGxsj(new Date());
        runlogServ.save(runlog);
    }

    private void checkIncrStatus(String xtlb, BusMonitorRunlog runlog) {
        /**
         * 检查该xtlb下所有增量采集文件的状态
         * 有文件就是错误就是异常，否则正常
         */
        Map<String, Object> params = new HashMap<>();
        params.put("jgxtlb", xtlb);
        List<BusIncrementFileInfo> fileInfos = incrementFileInfoMapper.listByCondition(params);
        String flag = "0";
        if(fileInfos.size() > 0){
            flag = "1";
        }
        for(BusIncrementFileInfo fileInfo:fileInfos){
            if("1".equals(fileInfo.getCwzt())){
                flag = "2";
                break;
            }
        }
        runlog.setZlyxzt(flag);
    }

    private void checkStockStatus(String xtlb, BusMonitorRunlog runlog) {
        /**
         * 存量数据采集任务运行状态。0-停止，1-正常，2-异常
         * 检查xtlb下所有任务，有1个启动中则正常，有1个错误信息则异常
         */
        Map<String, Object> params = new HashMap<>();
        params.put("jgxtlb",xtlb);
        List<BusStockDealStatus> statuses = dealStatusMapper.listByCondition(params);
        String flag = "0";
        for(BusStockDealStatus status:statuses){
            if((status.getCwxxms() != null)
                    && !"".equals(status.getCwxxms())){
                flag = "2";
                break;
            }
            if(!"1".equals(status.getCjzt())){
                flag = "1";
            }
        }
        runlog.setClyxzt(flag);
    }

    private void checkShareServer(String xtlb, BusMonitorRunlog runlog){
        HashMap<String, Object> params = new HashMap<>();
        params.put("jgxtlb",xtlb);
        List<ShareProtocol> shareProtocols = shareProtocolMapper.queryShareProtocol(params);
        if(shareProtocols != null && shareProtocols.size() > 0){
            // 增量数据获取方式。1-目录共享，2-Agent
            runlog.setZlhqfs("1");
            // 如果NFS和SMB都配置，只取第一条
            ShareProtocol protocol = shareProtocols.get(0);
            // 协议类型(1=SMB/2=NFS)
            String xylx = protocol.getXylx();
            // 系统类型（1=window/2=Linux）
            String system = protocol.getXtlb();
            String ip = protocol.getMlgxip();
            String username = protocol.getMlgxyhm();
            String password = protocol.getMlgxmm();
            String remote_arc_dir = protocol.getMlgxgdrzmc();
            String remote_arc_path = protocol.getMlgxgdrz();
            String remote_log_dir = protocol.getMlgxzxrzmc();
            String remote_log_path = protocol.getMlgxzxrz();
            if("1".equals(xylx)){
                NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(ip, username, password);
                // 归档日志
                String arc_url = "smb://"+ ip + "/" + remote_arc_dir;
                arc_url = arc_url.replace("\\","/");
                try {
                    SmbFile remoteFile = new SmbFile(arc_url, auth);
                    if(remoteFile.exists()){
                        runlog.setGdrzmlzt("1");
                        runlog.setGdrzcwms("smb url is valid!");
                    }else{
                        runlog.setGdrzmlzt("0");
                        runlog.setGdrzcwms("smb url is invalid!");
                    }
                } catch (Exception e) {
                    runlog.setGdrzmlzt("0");
                    runlog.setGdrzcwms(e.getMessage());
                }
                // 在线日志
                String log_url = "smb://"+ ip + "/" + remote_log_dir;
                log_url = log_url.replace("\\","/");
                try {
                    SmbFile remoteFile = new SmbFile(log_url, auth);
                    if(remoteFile.exists()){
                        runlog.setZxrzmlzt("1");
                        runlog.setZxrzcwms("smb url is valid!");
                    }else{
                        runlog.setZxrzmlzt("0");
                        runlog.setZxrzcwms("smb url is invalid!");
                    }
                } catch (Exception e) {
                    runlog.setZxrzmlzt("0");
                    runlog.setZxrzcwms(e.getMessage());
                }
            }else if("2".equals(xylx)){
                // 获取归档日志
                String arc_url = "nfs://"+ ip + "/" + remote_arc_dir;
                arc_url = arc_url.replace("\\","/");
                try {
                    XFile xf = new XFile(arc_url);
                    if (xf.exists()) {
                        runlog.setGdrzmlzt("1");
                        runlog.setGdrzcwms("nfs url is valid");
                    }else{
                        runlog.setGdrzmlzt("0");
                        runlog.setGdrzcwms("nfs url is invalid");
                    }
                } catch (Exception e) {
                    runlog.setGdrzmlzt("0");
                    runlog.setGdrzcwms(e.getMessage());
                }
                // 获取在线日志
                String log_url = "smb://"+ ip + "/" + remote_log_dir;
                log_url = log_url.replace("\\","/");
                try {
                    XFile xf = new XFile(log_url);
                    if (xf.exists()) {
                        runlog.setZxrzmlzt("1");
                        runlog.setZxrzcwms("nfs url is valid");
                    }else{
                        runlog.setZxrzmlzt("0");
                        runlog.setZxrzcwms("nfs url is invalid");
                    }
                } catch (Exception e) {
                    runlog.setZxrzmlzt("0");
                    runlog.setZxrzcwms(e.getMessage());
                }
            }
        }else{
            // 查询agent配置，如果有则利用agent获取文件
            HashMap<String, Object> p = new HashMap<>();
            p.put("jgxtlb",xtlb);
            p.put("zt","1"); // 状态。1=启用，0=未启用
            List<Agent> agents = agentMapper.queryAgent(p);
            if(agents != null && agents.size() > 0){
                Agent agent = agents.get(0);
                // 增量数据获取方式。1-目录共享，2-Agent
                runlog.setZlhqfs("2");
                String host = agent.getDlip();
                BigDecimal port = agent.getDldkh();
                // agent 获取文件
                try {
                    AgentClient client = new AgentClient(host, port.intValue());
                    runlog.setKhdzt("1");
                    runlog.setKhdcwms("agent is ok!");
                }catch (Exception e){
                    runlog.setKhdzt("1");
                    runlog.setKhdcwms(e.getMessage());
                }
            }
        }
    }

    private void checkFileServer(BusMonitorRunlog runlog) {
        String type = LoadGlobalPropertiesUtil.getProperty(Constant.FILE_PUT_TYPE);
        if("ftp".equals(type)){
            String host = LoadGlobalPropertiesUtil.getProperty(Constant.FILE_PUT_FTP_HOST);
            String port = LoadGlobalPropertiesUtil.getProperty(Constant.FILE_PUT_FTP_PORT);
            String username = LoadGlobalPropertiesUtil.getProperty(Constant.FILE_PUT_FTP_USERNAME);
            String password2 = LoadGlobalPropertiesUtil.getProperty(Constant.FILE_PUT_FTP_PASSWORD);
            try {
                FTPClient ftp = new FTPClient();
                ftp.connect(host, Integer.parseInt(port)); // 连接FTP服务器
                ftp.login(username, password2); // 登录
                int reply = ftp.getReplyCode();
                if (FTPReply.isPositiveCompletion(reply)) {
                    runlog.setFwqljzt("1");
                    runlog.setFwqcwms("ftp server is valid!");
                }else{
                    ftp.disconnect();
                    runlog.setFwqljzt("0");
                    runlog.setFwqcwms("ftp replycode is :" + reply);
                }
            } catch (IOException e) {
                runlog.setFwqljzt("0");
                runlog.setFwqcwms(e.getMessage());
            }
        }else{
            try {
                String NFSUrl = LoadGlobalPropertiesUtil.getProperty(Constant.FILE_PUT_NFS_URL);
                XFile xf = new XFile(NFSUrl);
                if (xf.exists()) {
                    runlog.setFwqljzt("1");
                    runlog.setFwqcwms("nfs url is valid!");
                }else{
                    runlog.setFwqljzt("0");
                    runlog.setFwqcwms("nfs url is invalid");
                }
            } catch (Exception e) {
                runlog.setFwqljzt("0");
                runlog.setFwqcwms(e.getMessage());
            }
        }
    }

    private void checkDatabase(String xtlb, BusMonitorRunlog runlog) {
        try {
            DataSource dataSource = new DataSource();
            dataSource.setJgxtlb(xtlb);
            // 1 正常 2 删除
            dataSource.setJlzt("1");
            dataSource = dataSourceMapper.selectOne(dataSource);
            String drivername = "oracle.jdbc.driver.OracleDriver";
//            String url = "jdbc:oracle:thin:@"+ dataSource.getIp() +":"+ dataSource.getPort() +":"+ dataSource.getSid();
            String url;
            if("0".equals(dataSource.getRac())){
                url = "jdbc:oracle:thin:@"+ dataSource.getIp() +":"+ dataSource.getPort() +":"+ dataSource.getSid();
            }else{
                url = "jdbc:oracle:thin:@(DESCRIPTION =(ADDRESS = (PROTOCOL = TCP)(HOST = "+ dataSource.getIp()
                        +")(PORT = "+ dataSource.getPort() +"))(CONNECT_DATA = (SERVER = DEDICATED)(SERVICE_NAME = "+ dataSource.getSid() +")))";
            }
            String user = dataSource.getOraUser();
            String password = dataSource.getOraPassword();
            JdbcUtil.getConnection(drivername, url, user, password);
            runlog.setSjkljzt("1");
            runlog.setSjkcwms("database is ok!");
        } catch (Exception e) {
            runlog.setSjkljzt("0");
            runlog.setSjkcwms(e.getMessage());
        }
    }

}
