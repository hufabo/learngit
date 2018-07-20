package com.xhw.logcollection.util;

import com.sun.xfile.XFile;
import com.sun.xfile.XFileInputStream;
import com.sun.xfile.XFileOutputStream;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author 孔纲
 * @Date 2018/3/15
 */
public class NFSUtils {

    private static Logger logger = LoggerFactory.getLogger(NFSUtils.class);

    /**
     * 上传文件到远程目录，可以自动创建父目录
     * 注意：remoteFilePath 远程url中路径分隔符是/,不能使用\代替，否则会因为找不到文件报错 no write permission
     * @author konggang
     * @date 2018/3/20 14:54
     */
    public static void nfsPutFile(String remoteFilePath, String localFilePath) throws IOException {
        XFileInputStream in = null;
        XFileOutputStream out = null;
        try {
            XFile xf = new XFile(remoteFilePath);
            // 检查父目录
            String parentPath = xf.getParent();
            XFile parentFile = new XFile(parentPath);
            if(!parentFile.exists()){
                parentFile.mkdirs();
            }
            // 打开数据流
            in = new XFileInputStream(localFilePath);
            out = new XFileOutputStream(xf);
            int c,filesz = 0;
            byte[] buf = new byte[8196];
            while ((c = in.read(buf)) > 0) {
                filesz += c;
                out.write(buf, 0, c);
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            in.close();
            out.close();
        }
    }

    /**
     * 下载远程文件
     * @nfsHome nfs根目录
     * @filePath 文件或目录，如果是目录，则下载目录下所有文件
     * @localDir 本地文件保存目录
     * @author konggang
     * @date 2018/3/16 10:56
     */
    public static void nfsGetFile(String nfsHome, String filePath, String localDir){
        XFile xf = new XFile(nfsHome);
        if (!xf.exists()) {
            logger.error("URL is bad! nfsHome={}", nfsHome);
            throw new RuntimeException("URL is bad");
        }
        xf.list(); // 缺少这一句的话，会出现找不到文件的错误
        XFile file = new XFile(nfsHome);
        // 如果文件不在跟目录，需要一步一步进入文件目录
        if(filePath != null && !"".equals(filePath)){
            String path[] = filePath.split("/");
            for(String splittext : path){
                nfsHome = nfsHome + "/" + splittext;
                file = new XFile(nfsHome);
                file.list();
            }
        }

        // 针对目录的处理
        if(file.isDirectory()){
            nfsGetDir(file, localDir);
        }else{
            nfsGetFile(file, localDir);
        }

    }

    public static void nfsGetDir(XFile file, String localDir){
        String basePath = file.getAbsolutePath();
        String[] paths = file.list();
        for(String path:paths){
            String filePath = basePath + "/" + path;
            file = new XFile(filePath);
            file.list();
            if(file.isDirectory()){
                nfsGetDir(file, localDir);
            }else{
                nfsGetFile(file, localDir);
            }
        }
    }

    public static File nfsGetFile(XFile file, String localDir){
        File local = null;
        XFileInputStream in = null;
        XFileOutputStream out = null;
        InputStream in2 = null;
        try {
            String localFile = localDir + "/" + file.getName();
            local = new File(localFile);
            // 判断远端文件和本地文件是否一致，如果一致，则直接返回
            in = new XFileInputStream(file);
            if(local.exists()){
                long remote_length = file.length();
                long local_length = local.length();
                // 通过文件大小简单判断相同文件
                if(remote_length == local_length){
                    logger.info("文件完全相同，取消传输！" + file.getName());
                    return local;
                }
            }else{
                File parentDir = local.getParentFile();
                if(!parentDir.exists()){
                    parentDir.mkdirs();
                }
                if(!local.exists()){
                    local.createNewFile();
                }
            }
            out = new XFileOutputStream(localFile);
            int c,filesz = 0;
            byte[] buf = new byte[2 * 1024 * 1024];
            while ((c = in.read(buf)) > 0) {
                filesz += c;
                out.write(buf, 0, c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(in2);
            IOUtils.closeQuietly(out);
        }
        return local;
    }

    public static void main(String[] args) throws Exception {
//        NFSUtils.nfsGetFile("nfs://192.168.0.117/d/nfs","201315/123.txt","d:\\");
        //FileUtil.writeFile("D:\\","123","teawesaf");
//        NFSUtils.nfsPutFile("nfs://192.168.0.117/d/nfs/stock/123.txt","D:\\123.txt");
        //FileUtil.moveFile("D:\\", "d:\\temp", "123");
//        NFSUtils.nfsGetFile("nfs://192.168.20.47//data/oracle/flash_recovery_area/ORCL/archivelog","/2018_04_20/o1_mf_1_133_fflgo25z_.arc","D:\\tmps\\putFiles\\incr\\arclogs\\10");
//        NFSUtils.nfsPutFile("nfs://192.168.20.47/data/oracle/flash_recovery_area/ORCL/archivelog/test2.xml","D:\\test2.xml");

//        XFile xf = new XFile("nfs://192.168.20.47//data/oracle/flash_recovery_area/ORCL/archivelog//2018_04_20/o1_mf_1_133_fflgo25z_.arc");
//        if (!xf.exists()) {
//            logger.error("URL is bad!");
//        }
    }
}
