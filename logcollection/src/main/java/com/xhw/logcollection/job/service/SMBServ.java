package com.xhw.logcollection.job.service;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;

/**
 * SMB协议接口
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-08
 * @note
 * @update
 * 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
@Deprecated
public interface SMBServ {

    /**
     * 获取SMB URL地址
     *
     * @param ip IP地址
     * @param user 用户名
     * @param password 访问密码
     * @param fileSource 访问目录
     * @note
     */
    public String getSmbUrl(String ip, String user, String password, String fileSource);

    /**
     * 登录SMB指定的目录
     *
     * @param ip IP地址
     * @param user 用户名
     * @param password 访问密码
     * @param fileSource 访问目录
     * @note
     */
    public NtlmPasswordAuthentication login(String ip, String user, String password, String fileSource) throws Exception;

    /**
     *  获取SmbFile
     *
     * @param auth SMB授权
     * @param remoteSmbUrl smb URL地址
     * @throws Exception
     */
    public SmbFile getSmbFile(NtlmPasswordAuthentication auth, String remoteSmbUrl ) throws Exception;

    /**
     * 下载指定文件
     *
     * @param smbFile SmbFile文件对象
     * @param dirTarget 输出文件夹
     * @throws Exception
     */
    public void downloadFile(SmbFile smbFile, String dirTarget) throws Exception;

    /**
     * 下载共享目录中指定目录的所有文件
     *
     * @param smbFile SmbFile目录对象
     * @param dirTarget 输出文件夹
     * @throws Exception
     */
    public void downloadFiles(SmbFile smbFile, String dirTarget) throws Exception;
}
