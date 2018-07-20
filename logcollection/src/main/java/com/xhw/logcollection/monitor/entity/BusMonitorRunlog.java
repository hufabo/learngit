package com.xhw.logcollection.monitor.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 运行情况监控表
 * @author 孔纲
 * @date 2018/3/2
 */
@Table(name = "BUS_MONITOR_RUNLOG")
public class BusMonitorRunlog {

    /**
     * 备案编号
     */
    @Column(name = "babh")
    @Id
    private String babh;

    /**
     * 自增主键
     */
    @Column(name = "id")
    private String id;

    /**
     * 系统类别代码
     */
    @Column(name = "jgxtlb")
    @Id
    private String jgxtlb;

    /**
     * 文件服务器连接状态。 0-出错，1-正常
     */
    @Column(name = "fwqljzt")
    private String fwqljzt;

    /**
     * 文件服务器错误描述
     */
    @Column(name = "fwqcwms")
    private String fwqcwms;

    /**
     * 目标数据库连接状态。0-出错，1-正常
     */
    @Column(name = "sjkljzt")
    private String sjkljzt;

    /**
     * 目标数据库错误描述
     */
    @Column(name = "sjkcwms")
    private String sjkcwms;

    /**
     * 增量数据获取方式。1-目录共享，2-Agent
     */
    @Column(name = "zlhqfs")
    private String zlhqfs;

    /**
     * Agent连接状态。0-出错，1-正常
     */
    @Column(name = "khdzt")
    private String khdzt;

    /**
     * Agent错误描述
     */
    @Column(name = "khdcwms")
    private String khdcwms;

    /**
     * 在线日志共享目录连接状态。0-出错，1-正常
     */
    @Column(name = "zxrzmlzt")
    private String zxrzmlzt;

    /**
     * 在线日志共享目录错误描述
     */
    @Column(name = "zxrzcwms")
    private String zxrzcwms;

    /**
     * 归档日志共享目录连接状态。0-出错，1-正常
     */
    @Column(name = "gdrzmlzt")
    private String gdrzmlzt;

    /**
     * 归档日志共享目录错误描述
     */
    @Column(name = "gdrzcwms")
    private String gdrzcwms;

    /**
     * 存量数据采集任务运行状态。0-停止，1-正常，2-异常
     */
    @Column(name = "clyxzt")
    private String clyxzt;

    /**
     * 增量数据采集任务运行状态。0-停止，1-正常，2-异常
     */
    @Column(name = "zlyxzt")
    private String zlyxzt;

    /**
     * 更新时间，精确到秒
     */
    @Column(name = "gxsj")
    private Date gxsj;

    @Transient
    private String cwtitle;

    public BusMonitorRunlog() {
    }

    public String getBabh() {
        return babh;
    }

    public void setBabh(String babh) {
        this.babh = babh;
    }

    public String getJgxtlb() {
        return jgxtlb;
    }

    public void setJgxtlb(String jgxtlb) {
        this.jgxtlb = jgxtlb;
    }

    public String getFwqljzt() {
        return fwqljzt;
    }

    public void setFwqljzt(String fwqljzt) {
        this.fwqljzt = fwqljzt;
    }

    public String getFwqcwms() {
        return fwqcwms;
    }

    public void setFwqcwms(String fwqcwms) {
        this.fwqcwms = fwqcwms;
    }

    public String getSjkljzt() {
        return sjkljzt;
    }

    public void setSjkljzt(String sjkljzt) {
        this.sjkljzt = sjkljzt;
    }

    public String getSjkcwms() {
        return sjkcwms;
    }

    public void setSjkcwms(String sjkcwms) {
        this.sjkcwms = sjkcwms;
    }

    public String getZlhqfs() {
        return zlhqfs;
    }

    public void setZlhqfs(String zlhqfs) {
        this.zlhqfs = zlhqfs;
    }

    public String getKhdzt() {
        return khdzt;
    }

    public void setKhdzt(String khdzt) {
        this.khdzt = khdzt;
    }

    public String getKhdcwms() {
        return khdcwms;
    }

    public void setKhdcwms(String khdcwms) {
        this.khdcwms = khdcwms;
    }

    public String getZxrzmlzt() {
        return zxrzmlzt;
    }

    public void setZxrzmlzt(String zxrzmlzt) {
        this.zxrzmlzt = zxrzmlzt;
    }

    public String getZxrzcwms() {
        return zxrzcwms;
    }

    public void setZxrzcwms(String zxrzcwms) {
        this.zxrzcwms = zxrzcwms;
    }

    public String getGdrzmlzt() {
        return gdrzmlzt;
    }

    public void setGdrzmlzt(String gdrzmlzt) {
        this.gdrzmlzt = gdrzmlzt;
    }

    public String getGdrzcwms() {
        return gdrzcwms;
    }

    public void setGdrzcwms(String gdrzcwms) {
        this.gdrzcwms = gdrzcwms;
    }

    public String getClyxzt() {
        return clyxzt;
    }

    public void setClyxzt(String clyxzt) {
        this.clyxzt = clyxzt;
    }

    public String getZlyxzt() {
        return zlyxzt;
    }

    public void setZlyxzt(String zlyxzt) {
        this.zlyxzt = zlyxzt;
    }

    public Date getGxsj() {
        return gxsj;
    }

    public void setGxsj(Date gxsj) {
        this.gxsj = gxsj;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCwtitle() {
        return cwtitle;
    }

    public void setCwtitle(String cwtitle) {
        this.cwtitle = cwtitle;
    }

    @Override
    public String toString() {
        return "BusMonitorRunlog{" +
                "babh='" + babh + '\'' +
                ", jgxtlb='" + jgxtlb + '\'' +
                ", fwqljzt='" + fwqljzt + '\'' +
                ", fwqcwms='" + fwqcwms + '\'' +
                ", sjkljzt='" + sjkljzt + '\'' +
                ", sjkcwms='" + sjkcwms + '\'' +
                ", zlhqfs='" + zlhqfs + '\'' +
                ", khdzt='" + khdzt + '\'' +
                ", khdcwms='" + khdcwms + '\'' +
                ", zxrzmlzt='" + zxrzmlzt + '\'' +
                ", zxrzcwms='" + zxrzcwms + '\'' +
                ", gdrzmlzt='" + gdrzmlzt + '\'' +
                ", gdrzcwms='" + gdrzcwms + '\'' +
                ", clyxzt='" + clyxzt + '\'' +
                ", zlyxzt='" + zlyxzt + '\'' +
                ", gxsj=" + gxsj +
                '}';
    }
}
