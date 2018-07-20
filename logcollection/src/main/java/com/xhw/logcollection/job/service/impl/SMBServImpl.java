package com.xhw.logcollection.job.service.impl;

import com.xhw.logcollection.job.service.SMBServ;
import jcifs.UniAddress;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * SMB协议接口实现
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-08
 * @note
 * @update
 * 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
@Service
@Deprecated
public class SMBServImpl implements SMBServ {
    private static Logger logger = LoggerFactory.getLogger(SMBServImpl.class);

    /**
     * 登录SMB指定的目录
     *
     * @param ip IP地址
     * @param user 用户名
     * @param password 访问密码
     * @param fileSource 访问目录
     * @note
     */
    @Override
    public NtlmPasswordAuthentication login(String ip, String user, String password, String fileSource) throws Exception {
        String remoteUrl = getSmbUrl(ip, user, password, fileSource);
        if(!StringUtils.isEmpty(user)
                && !StringUtils.isEmpty(password)) {
            String domainName = ip;
            UniAddress dc = UniAddress.getByName(ip);
            NtlmPasswordAuthentication auth =
                    new NtlmPasswordAuthentication(domainName, user, password);
            SmbSession.logon(dc, auth);
            logger.debug("login SMB successed!");

            //smbFile = new SmbFile(remoteUrl, auth);
            return auth;
        } else {
            //smbFile = new SmbFile(remoteUrl);
        }

        return null;
    }

    /**
     *  获取SmbFile
     *
     * @param auth SMB授权
     * @param remoteSmbUrl smb URL地址
     * @throws Exception
     */
    @Override
    public SmbFile getSmbFile(NtlmPasswordAuthentication auth, String remoteSmbUrl ) throws Exception{
        SmbFile smbFile= null;
        if(auth!=null){
            smbFile = new SmbFile(remoteSmbUrl, auth);
        }else{
            smbFile = new SmbFile(remoteSmbUrl);
        }

        if(!smbFile.exists()){
            throw new Exception("the URL is not exist! the URL is "+ remoteSmbUrl);
        }

        return smbFile;
    }

    /**
     * 下载指定文件
     *
     * @param smbFile SmbFile文件对象
     * @param dirTarget 输出文件夹
     * @throws Exception
     */
    @Override
    public void downloadFile(SmbFile smbFile, String dirTarget) throws Exception{
        if(!smbFile.isFile()){
            throw new Exception("the URL is not file! the URL is "+ smbFile.getPath());
        }

        String fileTarget=null;
        String fileSource = smbFile.getName();
        if(dirTarget.endsWith("/") || dirTarget.endsWith("\\")){
            fileTarget =dirTarget + fileSource;
        } else {
            fileTarget =dirTarget + File.separator + fileSource;
        }


        long startTime = System.currentTimeMillis();
        int filesz = 0, c;
        try(
            InputStream in = smbFile.getInputStream();
            OutputStream out = new FileOutputStream(fileTarget);
        ){
            byte[] buf = new byte[8196];
            while ((c = in.read(buf)) > 0) {
                filesz += c;
                out.write(buf, 0, c);
            }
        }

        long endTime = System.currentTimeMillis();
        long timeDiff = endTime - startTime;
        int rate = (int) ((filesz / 1000) / (timeDiff / 1000.0));
        System.out.println("File is downloaded!rate =" + rate);
        logger.debug("File["+fileSource+"] is downloaded! rate is " + rate);
    }

    /**
     * 下载共享目录中指定目录的所有文件
     *
     * @param smbFile SmbFile目录对象
     * @param dirTarget 输出文件夹
     * @throws Exception
     */
    @Override
    @Deprecated
    public void downloadFiles(SmbFile smbFile, String dirTarget) throws Exception {
        if(smbFile==null){
            return ;
        }
        SmbFile[] smbFiles = smbFile.listFiles();
        for (int i=0; i<smbFiles.length ; i++) {
            downloadFile(smbFiles[i], dirTarget);
        }
    }

    /**
     * 获取SMB URL地址
     *
     * @param ip IP地址
     * @param user 用户名
     * @param password 访问密码
     * @param fileSource 访问目录
     * @note
     */
    @Override
    public String getSmbUrl(String ip, String user, String password, String fileSource) {
        if(!StringUtils.isEmpty(user)
                && !StringUtils.isEmpty(password)){
            // smb://username:password@192.168.0.77/test
            return "smb://"+user+":"+password+"@"+ip+"/"+fileSource;
        }

        // smb://192.168.75.204/test/新建 文本文档.txt
        return "smb://"+ip+"/"+fileSource;
    }

    public static void main(String[] args) throws Exception {
        //测试（window环境，给共享目录设置权限后登录会报错）
//        String smbUrl = "smb://YANGCH-PC/smbout/smbout1.txt"; //YANGCH-PC  192.168.0.143
//
//        String ip ="YANGCH-PC"; //YANGCH-PC  192.168.0.143
//        String user = "";
//        String password = "";
//        String dir = "smbout";  //根目录
//
//
//        SMBServImpl smb = new SMBServImpl();
//        NtlmPasswordAuthentication auth= smb.login(ip, user, password, dir);
//
//        //获取指定的文件
//        String fileName = "smbout/smbout1.txt"; //访问指定文件
//        String dirTarget = "E:\\tmps\\smb\\outdir\\";   //输出目录
//        String remoteUrl = smb.getSmbUrl(ip, user, password, fileName); //获取SMB URL
//        SmbFile smbFile = smb.getSmbFile(auth, remoteUrl);   //获得 SmbFile 对象
//        smb.downloadFile(smbFile, dirTarget);   //下载指定文件

        /*//下载指定的目录
        String dirSource2 = "/smbout/subdir/";   //目标必须以“/”结尾
        String dirTarget2 = "E:\\tmps\\smb\\outdir\\subdir";
        String remoteUrl2 = smb.getSmbUrl(ip, user, password, dirSource2);
        SmbFile smbFile2 = smb.getSmbFile(auth, remoteUrl2);
        smb.downloadFiles(smbFile2, dirTarget2);*/

    }
}
