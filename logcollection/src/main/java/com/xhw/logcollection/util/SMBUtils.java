package com.xhw.logcollection.util;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.NumberFormat;

/**
 * SMB 工具类
 * @Author 孔纲
 * @Date 2018/3/15
 */
public class SMBUtils {

    private static Logger logger  = LoggerFactory.getLogger(SMBUtils.class);

    /**
     * 下载远程目录下所有文件
     * @author konggang
     * @date 2018/3/16 9:54
     */
    public static void smbGetFiles(String remoteDir, String localDir, NtlmPasswordAuthentication auth) throws Exception {
        SmbFile remoteDirFile = new SmbFile(remoteDir, auth);
        SmbFile[] smbFiles = remoteDirFile.listFiles();
        for (int i=0; i<smbFiles.length ; i++) {
            smbGetFile(smbFiles[i].getPath(), localDir, auth);
        }
    }

    /**
     * 下载远程文件到本地
     * @author konggang
     * @date 2018/3/15 10:40
     */
    public static File smbGetFile(String remoteUrl, String localDir, NtlmPasswordAuthentication auth) {
        File localFile = null;
        InputStream in = null;
        InputStream in2 = null;
        OutputStream out = null;
        try {
            SmbFile remoteFile = new SmbFile(remoteUrl, auth);
            if(!remoteFile.exists()){
                logger.error("File not found!" + remoteUrl);
                throw new RuntimeException("File not found!");
            }
            String fileName = remoteFile.getName();
            long total = remoteFile.length();
            localFile = new File(localDir.concat(File.separator).concat(fileName));
            in = remoteFile.getInputStream();
            if(!localFile.exists()){
                File parentPath = new File(localDir);
                if(!parentPath.exists()){
                    parentPath.mkdirs();
                }
                localFile.createNewFile();
            }else{
                // 判断远端文件和本地文件是否一致，如果一致，则直接返回
                long remote_length = remoteFile.getDiskFreeSpace();
                long local_length = localFile.length();
                // 改为通过简单的大小判断是否相同
                if(remote_length == local_length){
                    logger.info("文件完全相同，取消传输！" + fileName);
                    return localFile;
                }
            }
            out = new FileOutputStream(localFile);
            int filesz = 0, c;
            byte[] buf = new byte[2*1024*1024];
            while ((c = in.read(buf)) > 0) {
                filesz += c;
                out.write(buf, 0, c);
                // 刷新大小，打印进度
                out.flush();
                long length = localFile.length();
                NumberFormat nf = NumberFormat.getPercentInstance();
                System.out.println(fileName + ":" + nf.format((float)length/(float)total));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(in2);
            IOUtils.closeQuietly(out);
        }
        return localFile;
    }

    /**
     * 上传文件到远程服务器
     * @author konggang
     * @date 2018/3/15 10:40
     */
    public static void smbPutFile(String remoteUrl, String localFilePath, NtlmPasswordAuthentication auth) {
        FileInputStream fis = null;
        try {
            File localFile = new File(localFilePath);
            localFile.setReadOnly();
            String fileName = localFile.getName();
            SmbFile remoteFile = new SmbFile(remoteUrl + "/" + fileName, auth);
            fis = new FileInputStream(localFile);
            IOUtils.copyLarge(fis, remoteFile.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除远程服务器上的文件
     * @author konggang
     * @date 2018/3/15 10:39
     */
    public static void smbDelete(String remoteUrl, NtlmPasswordAuthentication auth) {
        try {
            SmbFile remoteFile = new SmbFile(remoteUrl, auth);
            if (remoteFile.exists()) {
                remoteFile.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws Exception {
//        SMBUtils test = new SMBUtils();
//        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("192.168.0.117", "konggang", "198899"); // 先登录验证
//        String url = "smb://192.168.0.117/smb/test.txt";
//        test.smbDel(url, auth);

//        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("192.168.0.117", "konggang", "198899");
//        String url = "smb://192.168.0.117/smb/";
//        String localFile = "D:\\tmps\\putFiles\\incr\\arclogs\\C3A293D5D5DE45B8982CAAC3EC4B9135\\2018_04_03\\O1_MF_1_11_FD67OYFT_.ARC";
//        smbGetFiles(url, localFile, auth);

//        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("192.168.0.117", "konggang", "198899");
//        String url = "smb://192.168.20.237/smb/";
//        String localFile = "D:\\tmps\\putFiles\\incr\\arclogs\\C3A293D5D5DE45B8982CAAC3EC4B9135\\2018_04_03\\O1_MF_1_11_FD67OYFT_.ARC";
//        smbGetFiles(url, localFile, auth);

//        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("192.168.20.237", "smb", "Ceshi123456");
//        SmbFile remoteFile = new SmbFile("smb://192.168.20.237/archivelog/ORCL/ARCHIVELOG", auth);
//        if(remoteFile.exists()){
//            System.out.println(remoteFile.getName());
//        }

    }
}
