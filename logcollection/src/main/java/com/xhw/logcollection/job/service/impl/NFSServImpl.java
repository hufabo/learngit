package com.xhw.logcollection.job.service.impl;

import com.sun.nfs.XFileExtensionAccessor;
import com.sun.xfile.XFile;
import com.sun.xfile.XFileInputStream;
import com.sun.xfile.XFileOutputStream;
import com.xhw.logcollection.Application;
import com.xhw.logcollection.job.service.NFSServ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;

/**
 * NFS协议接口实现
 *
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
public class NFSServImpl implements NFSServ {
    private static Logger logger = LoggerFactory.getLogger(NFSServImpl.class);

    /**
     * 登录NFS指定的目录
     *
     * @param ip IP地址
     * @param user 用户名
     * @param password 访问密码
     * @param dir 访问目录
     * @note
     */
    @Override
    public XFile login(String ip, String user, String password, String dir) throws Exception{
        String url = "nfs://" + ip + "/" + dir; //创建连接

        XFile xfile = new XFile(url);
        //调用exists()判断是否连接成功
        if (xfile.exists()) {
            System.out.println("URL is OK!");
            logger.debug("URL is OK! the URL is "+url);
        } else {
            System.out.println("URL is Bad!");
            logger.error("URL is Bad! URL is "+url);
            throw new Exception("URL is Bad! the URL is "+url);
        }

        if(!StringUtils.isEmpty(user)) {
            XFileExtensionAccessor nfsx = (XFileExtensionAccessor) xfile.getExtensionAccessor();
            if (!nfsx.loginPCNFSD(ip, user, password)) {
                System.out.println("login NFS failed!");
                logger.error("login NFS failed!");
                throw new Exception("login NFS failed!");
            } else {
                System.out.println("login NFS successed!");
                logger.debug("login NFS successed!");
            }
        }

        return xfile;
    }

    /**
     * 下载指定文件
     *
     * @param xFileDir nfs根目录
     * @param fileSource 文件名
     * @param dirTarget 输出文件夹
     * @throws Exception
     */
    @Override
    public void downloadFile(XFile xFileDir, String fileSource, String dirTarget) throws Exception{
        String urlFile = xFileDir.getAbsolutePath() + "/" + fileSource;
        XFile xFile = new XFile(urlFile);

        if(!xFile.exists()){
            throw new Exception("the URL is not exist! the URL is "+ urlFile);
        }

        if(!xFile.isFile()){
            throw new Exception("the URL is not file! the URL is "+ urlFile);
        }

        if(!xFile.canRead()){
            throw new Exception("the URL can't read! the URL is "+ urlFile);
        }

        String fileTarget=null;
        if(dirTarget.endsWith("/") || dirTarget.endsWith("\\")){
            fileTarget =dirTarget + fileSource;
        } else {
            fileTarget =dirTarget + File.separator + fileSource;
        }

        long startTime = System.currentTimeMillis();
        int filesz = 0, c;
        try(
            XFileInputStream in = new XFileInputStream(xFile);
            XFileOutputStream out = new XFileOutputStream(fileTarget);
        ) {
            byte[] buf = new byte[8196];
            while ((c = in.read(buf)) > 0) {
                filesz += c;
                out.write(buf, 0, c);
            }
            //in.close();
            //out.close();
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
     * @param xFileDir nfs根目录
     * @param dirSource 共享目录，可以为空。为空时从xFileDir指定的目录中获取文件
     * @param dirTarget 输出文件夹
     * @throws Exception
     */
    @Override
    public void downloadFiles (XFile xFileDir, String dirSource, String dirTarget) throws Exception {
        XFile xFileBase = xFileDir;
        if(!StringUtils.isEmpty(dirSource)){
            String dirUrl = xFileDir.getAbsolutePath() + "/" + dirSource;
            xFileBase = new XFile(dirUrl);
        }
        String[] fileList = xFileBase.list();

        for (String file : fileList) {
            downloadFile(xFileBase, file, dirTarget);
        }
    }

    public static void main(String[] args) throws Exception {
//        //测试(window7旗舰版环境)
//        String ip ="YANGCH-PC"; //YANGCH-PC  192.168.0.143
//        //String dir = "/d/nfsout"; //window中表示 d:\nfsout目录，export文件的配置“D:\nfsout -public”
//        String dir = "d/nfspout";  //权限访问
//        String user = "";
//        String password = "";
//
//        String fileName = "nfspout12.txt"; //访问指定文件
//
//        String dirSource = "";  //访问指定目录的文件
//        String dirSource2 = "subdir";
//
//        String dirTarget = "E:\\tmps\\nfs\\outdir\\";   //输出目录
//        String dirTarget2 = "E:\\tmps\\nfs\\outdir\\subdir";
//
//        NFSServImpl nfs = new NFSServImpl();
//        XFile xFileDir = nfs.login(ip, user, password, dir); //登录验证
//        nfs.downloadFile(xFileDir, fileName, dirTarget);   //下载单个文件
//        //nfs.downloadFiles(xFileDir, dirSource, dirTarget);   //下载指定目录的文件
//        //nfs.downloadFiles(xFileDir, dirSource2, dirTarget2);

        String ip ="192.168.0.117";
        String dir = "es";
        NFSServImpl nfs = new NFSServImpl();
        XFile xFileDir = nfs.login(ip, null, null, dir); //登录验证
        nfs.downloadFile(xFileDir, "test.txt", "d:");

    }
}
