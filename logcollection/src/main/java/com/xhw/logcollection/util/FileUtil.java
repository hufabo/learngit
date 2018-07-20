package com.xhw.logcollection.util;

import com.google.common.io.Files;

import java.io.*;

/**
 * 文件操作工具类
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-03-06
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public class FileUtil {

    /**
     * 获取指定的文件对象
     * @param fileDir 文件目录
     * @param fileName 文件名称
     * @return
     */
    public static File getFile(String fileDir, String fileName) throws IOException {
        return createFile(fileDir.concat(File.separator).concat(fileName));
    }

    /**
     * 创建文件，父目录不存在则先创建父目录
     * @author konggang  
     * @date 2018/5/10 13:43    
     */ 
    public static File createFile(String filePath) throws IOException {
        File file = new File(filePath);
        // 判断父目录是否存在,不存在则创建父目录
        File parentDir = file.getParentFile();
        if(!parentDir.exists()){
            parentDir.mkdirs();
        }
        if(!file.exists()){
            file.createNewFile();
        }
        return file;
    }

    /**
     * 移到文件
     * @param sourceDir 待移到文件的目录
     * @param targetDir 目标目录
     * @param fileName 文件名
     * @throws Exception
     */
    public static void moveFile(String sourceDir, String targetDir, String fileName) throws Exception{
        File sourceFile = getFile(sourceDir, fileName);
        File targetFile = getFile(targetDir, fileName);
        Files.move(sourceFile, targetFile);
    }

    /**
     * 移到文件
     * @param file 待移文件
     * @param targetDir 目标目录
     * @throws Exception
     */
    public static void moveFile(File file, String targetDir) throws Exception{
        // 检查目录文件夹是否存在，不存在则创建
        File dir = new File(targetDir);
        if(!dir.exists()){
            dir.mkdirs();
        }
        String targetFilePath;
        if(targetDir.endsWith("/") || targetDir.endsWith("\\")){
            targetFilePath = targetDir + file.getName();
        }else{
            targetFilePath = targetDir + File.separator + file.getName();
        }
        file.renameTo(new File(targetFilePath));
    }

    /**
     * 删除文件
     * @author konggang
     * @date 2018/3/23 17:11
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 将内容写入文件
     * @author konggang
     * @date 2018/3/13 13:38
     */
    public static void writeFile(String filePath, String fileName, String content) throws IOException {
        File targetFile = FileUtil.createFile(filePath + File.separator + fileName);
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(targetFile);
            org.apache.commons.io.IOUtils.write(content, fos,"UTF-8");
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if(fos != null){
                fos.close();
            }
        }
    }

    public static String readResourceFile(String filePath){
        StringBuilder stringBuilder = new StringBuilder();
        try{
            InputStream inputStream = FileUtil.class.getResourceAsStream(filePath);
            InputStreamReader isr = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader in = new BufferedReader(isr);
            String str;
            while ((str = in.readLine()) != null){
                stringBuilder.append(str);
            }
            in.close();
        }catch (IOException e){
            e.getStackTrace();
        }

        // 去掉bom文件头
        String resultXml= stringBuilder.toString();
        try {
            if(resultXml.startsWith("\uFEFF")){
                byte[] b = resultXml.getBytes("UTF-8");
                resultXml = new String(b,3,b.length-3,"UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return resultXml;
    }


    public static void main(String[] args) throws Exception {
//        File f= getFile("E:\\tmps\\putFiles\\incr\\await","incr_file001.xml");
//        System.out.println(f.getAbsolutePath());
//
//        moveFile("E:\\tmps\\putFiles\\incr\\await",
//                "E:\\tmps\\putFiles\\incr\\succeeded","incr_file001.xml");
//        FileUtil.writeFile("");

        String xml = FileUtil.readResourceFile("/datas/update_strategy.xml");
        System.out.println(xml);
    }

}
