package com.xhw.logcollection.util;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import java.io.*;
import java.util.Enumeration;

/**
 * 压缩解压工具类
 * 采用ant方式
 * @Author 孔纲
 * @Date 2018/3/10
 */
public class ZipUtil {

    public static void main(String[] args) {
        //zip ("D:\\tmps", "D:\\tmps.zip");
        //zip ("D:\\test.txt", "D:\\test.zip");
        //unZip("D:\\test.zip", "D:\\");
    }

    /**
     * 功能：把 sourceDir 目录下的所有文件进行 zip 格式的压缩，保存为指定 zip 文件
     * @param sourceDir 源文件或者源目录
     * @param zipFile 压缩文件
     */
    public static void zip(String sourceDir, String zipFile) {
        OutputStream os;
        try {
            os = new FileOutputStream(zipFile);
            BufferedOutputStream bos = new BufferedOutputStream(os);
            ZipOutputStream zos = new ZipOutputStream(bos);
            File file = new File(sourceDir);
            String basePath;
            if (file.isDirectory()) {
                basePath = file.getPath();
            } else {//直接压缩单个文件时，取父目录
                basePath = file.getParent();
            }
            // 根目录下获取parent带\后缀 如 D:\，非根目录下获取parent不带\后缀 如 D:\tmps\putfiles\incr\succeeded
            if(!basePath.endsWith("/") && !basePath.endsWith("\\")){
                basePath.concat("/");
            }
            zipFile(file, basePath, zos);
            zos.closeEntry();
            zos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 功能：执行文件压缩成zip文件
     * @param source
     * @param basePath  待压缩文件根目录
     * @param zos
     */
    private static void zipFile(File source, String basePath, ZipOutputStream zos) {
        File[] files;
        if (source.isDirectory()) {
            files = source.listFiles();
        } else {
            files = new File[1];
            files[0] = source;
        }
        String pathName;//存相对路径(相对于待压缩的根目录)
        byte[] buf = new byte[1024];
        int length;
        try {
            for (File file : files) {
                if (file.isDirectory()) {
                    pathName = file.getPath().substring(basePath.length())
                            + "/";
                    zos.putNextEntry(new ZipEntry(pathName));
                    zipFile(file, basePath, zos);
                } else {
                    pathName = file.getPath().substring(basePath.length());
                    InputStream is = new FileInputStream(file);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    zos.putNextEntry(new ZipEntry(pathName));
                    while ((length = bis.read(buf)) > 0) {
                        zos.write(buf, 0, length);
                    }
                    is.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能：解压 zip 文件，只能解压 zip 文件
     * @param zipfile
     * @param destDir
     */
    public static void unzip(String zipfile, String destDir) {
        destDir = destDir.endsWith("\\") ? destDir : destDir + "\\";
        byte b[] = new byte[1024];
        int length;
        ZipFile zipFile;
        try {
            zipFile = new ZipFile(new File(zipfile));
            Enumeration enumeration = zipFile.getEntries();
            ZipEntry zipEntry = null;
            while (enumeration.hasMoreElements()) {
                zipEntry = (ZipEntry) enumeration.nextElement();
                File loadFile = new File(destDir + zipEntry.getName());
                if (zipEntry.isDirectory()) {
                    loadFile.mkdirs();
                } else {
                    if (!loadFile.getParentFile().exists()){
                        loadFile.getParentFile().mkdirs();
                    }
                    OutputStream outputStream = new FileOutputStream(loadFile);
                    InputStream inputStream = zipFile.getInputStream(zipEntry);
                    while ((length = inputStream.read(b)) > 0)
                        outputStream.write(b, 0, length);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
