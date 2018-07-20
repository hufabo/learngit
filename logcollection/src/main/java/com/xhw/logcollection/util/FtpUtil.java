package com.xhw.logcollection.util;

import com.xhw.logcollection.Application;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @Author 孔纲
 * @Date 2018/3/8
 */
public class FtpUtil {

    private static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void checkConnection(String host, int port, String username, String password) {

    }

    /**
     * Description: 向FTP服务器上传文件  
     * @param host FTP服务器hostname  
     * @param port FTP服务器端口  
     * @param username FTP登录账号  
     * @param password FTP登录密码  
     * @param basePath FTP服务器基础目录 
     * @param filePath FTP服务器文件存放路径。例如分日期存放：/2015/01/01。文件的路径为basePath+filePath 
     * @param filename 上传到FTP服务器上的文件名  
     * @param input 输入流  
     * @return 成功返回true，否则返回false  
     */
    public static boolean uploadFile(String host, int port, String username, String password, String basePath,
                                     String filePath, String filename, InputStream input) {
        boolean result = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(host, port);// 连接FTP服务器  
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            ftp.login(username, password);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                logger.debug("ftp连接失败！ " + reply);
                return result;
            }
            logger.debug("ftp连接成功！");
            //切换到上传目录
            String finalPath = "";
            if(basePath != null && !"".equals(basePath)){
                finalPath += basePath;
            }
            if(filePath != null && !"".equals(filePath)){
                finalPath = finalPath.concat("/").concat(filePath);
            }
            logger.debug("文件上传路径：" + finalPath);
            // 解决路径中包含中文时 上传失败的问题
            finalPath = new String(finalPath.getBytes("GBK"),"iso-8859-1");
            if (!ftp.changeWorkingDirectory(finalPath)) {
                //如果目录不存在创建目录
                String[] dirs = finalPath.replace("\\","/").split("/");
                String tempPath = "";
                for (String dir : dirs) {
                    if (null == dir || "".equals(dir)) continue;
//                    tempPath += "/" + dir;
                    if (!ftp.changeWorkingDirectory(dir)) {
                        logger.debug("进入目录失败：" + dir);
                        if (!ftp.makeDirectory(dir)) {
                            logger.debug("创建目录失败：" + dir);
                            // 再次尝试进入（有可能失败的原因是其他线程已经创建了该目录）
                            if(!ftp.changeWorkingDirectory(dir)){
                                logger.debug("再次进入目录失败！返回失败！" + dir);
                                return result;
                            }
                        } else {
                            ftp.changeWorkingDirectory(dir);
                        }
                    }
                }
            }
            //设置上传文件的类型为二进制类型  
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            //上传文件
            ftp.enterLocalPassiveMode();
            if (!ftp.storeFile(filename, input)) {
                logger.debug("上传文件操作失败！" + filename);
                return result;
            }
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
            logger.debug("上传文件异常！" + e.getMessage());
        } finally {
            try {
                //关闭文件流
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                //退出
                ftp.logout();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                //断开连接
                ftp.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * Description: 从FTP服务器下载文件  
     * @param host FTP服务器hostname  
     * @param port FTP服务器端口  
     * @param username FTP登录账号  
     * @param password FTP登录密码  
     * @param remotePath FTP服务器上的相对路径  
     * @param fileName 要下载的文件名  
     * @param localPath 下载后保存到本地的路径  
     * @return
     */
    public static boolean downloadFile(String host, int port, String username, String password, String remotePath,
                                       String fileName, String localPath) {
        boolean result = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(host, port);
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器  
            ftp.login(username, password);// 登录  
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                logger.error("ftp连接失败！");
                return result;
            }
            ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录  
            FTPFile[] fs = ftp.listFiles();
            for (FTPFile ff : fs) {
                if (ff.getName().equals(fileName)) {
                    File localFile = new File(localPath + "/" + ff.getName());
                    OutputStream is = new FileOutputStream(localFile);
                    ftp.retrieveFile(ff.getName(), is);
                    is.close();
                }
            }
            ftp.logout();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("下载文件异常！");
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
//        File file = new File("d:\\test2.xml");
//        boolean b = FtpUtil.uploadFile("180.168.178.174", 21049, "langang", "Ftp@qh666", "", "incr\\20180411\\", "test2.xml", new FileInputStream(file));
//        boolean b = FtpUtil.uploadFile("192.168.20.237", 21, "ceshi", "ceshi", "", "64984984123", "test2.xml", new FileInputStream(file));
//        System.out.println(b);

        Long a = null;
        System.out.println(0 != a);

//        try {
//            FTPClient ftp = new FTPClient();
//            ftp.connect("192.168.20.237", 21);
//            ftp.login("ceshi", "ceshi");// 登录
//            int reply = ftp.getReplyCode();
//            System.out.println(reply);
//            boolean b1 = ftp.changeWorkingDirectory("logs");
//            System.out.println("切换目录：" + b1);
//            boolean b = ftp.makeDirectory("123");
//            System.out.println(b);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//        }
    }
}
