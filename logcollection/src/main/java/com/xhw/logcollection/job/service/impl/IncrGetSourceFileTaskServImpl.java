package com.xhw.logcollection.job.service.impl;

import com.xhw.logcollection.job.service.IncrGetSourceFileTaskServ;
import com.xhw.logcollection.system.entity.Agent;
import com.xhw.logcollection.system.entity.ShareProtocol;
import com.xhw.logcollection.system.mapper.AgentMapper;
import com.xhw.logcollection.system.mapper.ShareProtocolMapper;
import com.xhw.logcollection.util.Constant;
import com.xhw.logcollection.util.LoadGlobalPropertiesUtil;
import com.xhw.logcollection.util.NFSUtils;
import com.xhw.logcollection.util.SMBUtils;
import jcifs.smb.NtlmPasswordAuthentication;
import net.sf.jftp.gui.base.LocalDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 *  采集指定系统的 数据库日志文件 服务接口实现类
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-03-08
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
@Service
@Deprecated
public class IncrGetSourceFileTaskServImpl implements IncrGetSourceFileTaskServ {

    @Autowired
    private ShareProtocolMapper shareProtocolMapper;

    @Autowired
    private AgentMapper agentMapper;

    /**
     * 采集指定系统的 数据库日志文件
     *
     * @param jgxtlb 数据源类别名称
     * @throws Exception
     */
    @Override
    public void getSourceFiles(String jgxtlb) throws Exception {
        // 1、根据 “1-NFS/SMB 2-agent 3-无” 的顺序依次尝试采集增量日志文件，采集到返回，则不重复采集
        HashMap<String, Object> params = new HashMap<>();
        params.put("jgxtlb",jgxtlb);
        List<ShareProtocol> shareProtocols = shareProtocolMapper.queryShareProtocol(params);
        if(shareProtocols!= null && shareProtocols.size() > 0){
            // 如果NFS和SMB都配置，只取第一条
            ShareProtocol protocol = shareProtocols.get(0);
            // 协议类型(1=SMB/2=NFS)
            String xylx = protocol.getXylx();
            // 系统类型（1=window/2=Linux）
            String xtlb = protocol.getXtlb();
            String ip = protocol.getMlgxip();
            String username = protocol.getMlgxyhm();
            String password = protocol.getMlgxmm();
            String remote_arc_dir = protocol.getMlgxgdrz();
            String remote_log_dir = protocol.getMlgxzxrz();
            if("1".equals(xylx)){
                // SMB协议需要账户和密码，NFS协议不需要
                NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(ip, username, password);
                // 获取归档日志
                String arc_url = "smb://"+ ip + "/" + remote_arc_dir;
                String arcLogDir = LoadGlobalPropertiesUtil.getProperty(Constant.FILE_PUT_INCR_DIR_ARC_LOG);
                // 获取归档日志 本地存在在 归档目录/系统类别/ 下面
                SMBUtils.smbGetFile(arc_url, arcLogDir.concat(File.separator).concat(jgxtlb), auth);
                // 获取在线日志
                String log_url = "smb://"+ ip + "/" + remote_log_dir;
                String logDir = LoadGlobalPropertiesUtil.getProperty(Constant.FILE_PUT_INCR_DIR_LOG);
                // 获取归档日志 本地存在在 归档目录/系统类别/ 下面
                SMBUtils.smbGetFiles(log_url, logDir.concat(File.separator).concat(jgxtlb), auth);
            }else if("2".equals(xylx)){
                // 获取归档日志
                String arc_url = "nfs://"+ ip + "/" + remote_arc_dir;
                String arcLogDir = LoadGlobalPropertiesUtil.getProperty(Constant.FILE_PUT_INCR_DIR_ARC_LOG);
                // 获取归档日志 本地存在在 归档目录/系统类别/ 下面
                NFSUtils.nfsGetFile(arc_url, null, arcLogDir.concat(File.separator).concat(jgxtlb));
                // 获取在线日志
                String log_url = "smb://"+ ip + "/" + remote_log_dir;
                String logDir = LoadGlobalPropertiesUtil.getProperty(Constant.FILE_PUT_INCR_DIR_LOG);
                // 获取归档日志 本地存在在 归档目录/系统类别/ 下面
                NFSUtils.nfsGetFile(log_url, null,logDir.concat(File.separator).concat(jgxtlb));
            }
        }else{
            // 查询agent配置，如果有则利用agent获取文件
            HashMap<String, Object> p = new HashMap<>();
            p.put("jgxtlb",jgxtlb);
            p.put("zt","1"); // 状态。1=启用，0=未启用
            List<Agent> agents = agentMapper.queryAgent(p);
            if(agents!=null && agents.size() > 0){
                // 重复配置只取第一个
                Agent agent = agents.get(0);
                // TODO 待完善agent部分逻辑
            }
        }
    }
}
