package com.xhw.logcollection.job.service;

import com.sun.xfile.XFile;

/**
 * NFS协议接口
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-08
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
@Deprecated
public interface NFSServ {
    /**
     * 登录NFS指定的目录
     *
     * @param ip IP地址
     * @param user 用户名
     * @param password 访问密码
     * @param dir 访问目录
     * @note
     */
    public XFile login(String ip, String user, String password, String dir) throws Exception;

    /**
     * 下载指定文件
     *
     * @param xFileDir nfs根目录
     * @param fileSource 文件名
     * @param dirTarget 输出文件夹
     * @throws Exception
     */
    public void downloadFile(XFile xFileDir, String fileSource, String dirTarget) throws Exception;

    /**
     * 下载共享目录中指定目录的所有文件
     *
     * @param xFileDir nfs根目录
     * @param dirSource 共享目录，可以为空。为空时从xFileDir指定的目录中获取文件
     * @param dirTarget 输出文件夹
     * @throws Exception
     */
    public void downloadFiles (XFile xFileDir, String dirSource, String dirTarget) throws Exception;
}
