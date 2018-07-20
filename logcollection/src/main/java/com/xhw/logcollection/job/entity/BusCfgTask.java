package com.xhw.logcollection.job.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "BUS_CFG_TASK")
public class BusCfgTask {
    /**
     * 系统类别代码，从全局参数获取
     */
    @Id
    @Column(name = "JGXTLB")
    private String jgxtlb;

    /**
     * 表名
     */
    @Id
    @Column(name = "BM")
    private String bm;

    /**
     * 表名描述
     */
    @Column(name = "BMMS")
    private String bmms;

    /**
     * 存量数据采集标记，0-不采集，1-采集
     */
    @Column(name = "CLCJBJ")
    private String clcjbj;

    /**
     * 时间戳字段
     */
    @Column(name = "SJCZD")
    private String sjczd;

    /**
     * 存量数据起始日期, 精确到天
     */
    @Column(name = "CLQSRQ")
    private Date clqsrq;

    /**
     * 存量数据过滤条件
     */
    @Column(name = "CLGLTJ")
    private String clgltj;

    /**
     * 存量数据采集完成标记。0-未启动，1-正在执行，2-已完成
     */
    @Column(name = "CLWCBJ")
    private String clwcbj;

    /**
     * 增量数据客户端过滤类型，多个过滤条件，半角“,”分割
     */
    @Column(name = "ZLKHDGLLX")
    private String zlkhdgllx;

    /**
     * 是否采集“insert”增量数据，0-否，1-是
     */
    @Column(name = "ZLINSERT")
    private String zlinsert;

    /**
     * 是否采集“update”增量数据，0-否，1-是
     */
    @Column(name = "ZLUPDATE")
    private String zlupdate;

    /**
     * 是否采集“delete”增量数据，0-否，1-是
     */
    @Column(name = "ZLDELETE")
    private String zldelete;

    /**
     * 策略创建时间，精确到秒
     */
    @Column(name = "CJSJ")
    private Date cjsj;

    /**
     * 策略更新时间，精确到秒
     */
    @Column(name = "GXSJ")
    private Date gxsj;

    @Transient
    private String schema;

    /**
     * 获取系统类别代码，从全局参数获取
     *
     * @return JGXTLB - 系统类别代码，从全局参数获取
     */
    public String getJgxtlb() {
        return jgxtlb;
    }

    /**
     * 设置系统类别代码，从全局参数获取
     *
     * @param jgxtlb 系统类别代码，从全局参数获取
     */
    public void setJgxtlb(String jgxtlb) {
        this.jgxtlb = jgxtlb == null ? null : jgxtlb.trim();
    }

    /**
     * 获取表名
     *
     * @return BM - 表名
     */
    public String getBm() {
        return bm;
    }

    /**
     * 设置表名
     *
     * @param bm 表名
     */
    public void setBm(String bm) {
        this.bm = bm == null ? null : bm.trim();
    }

    /**
     * 获取表名描述
     *
     * @return BMMS - 表名描述
     */
    public String getBmms() {
        return bmms;
    }

    /**
     * 设置表名描述
     *
     * @param bmms 表名描述
     */
    public void setBmms(String bmms) {
        this.bmms = bmms == null ? null : bmms.trim();
    }

    /**
     * 获取存量数据采集标记，0-不采集，1-采集
     *
     * @return CLCJBJ - 存量数据采集标记，0-不采集，1-采集
     */
    public String getClcjbj() {
        return clcjbj;
    }

    /**
     * 设置存量数据采集标记，0-不采集，1-采集
     *
     * @param clcjbj 存量数据采集标记，0-不采集，1-采集
     */
    public void setClcjbj(String clcjbj) {
        this.clcjbj = clcjbj == null ? null : clcjbj.trim();
    }

    /**
     * 获取时间戳字段
     *
     * @return SJCZD - 时间戳字段
     */
    public String getSjczd() {
        return sjczd;
    }

    /**
     * 设置时间戳字段
     *
     * @param sjczd 时间戳字段
     */
    public void setSjczd(String sjczd) {
        this.sjczd = sjczd == null ? null : sjczd.trim();
    }

    /**
     * 获取存量数据起始日期, 精确到天
     *
     * @return CLQSRQ - 存量数据起始日期, 精确到天
     */
    public Date getClqsrq() {
        return clqsrq;
    }

    /**
     * 设置存量数据起始日期, 精确到天
     *
     * @param clqsrq 存量数据起始日期, 精确到天
     */
    public void setClqsrq(Date clqsrq) {
        this.clqsrq = clqsrq;
    }

    /**
     * 获取存量数据过滤条件
     *
     * @return CLGLTJ - 存量数据过滤条件
     */
    public String getClgltj() {
        return clgltj;
    }

    /**
     * 设置存量数据过滤条件
     *
     * @param clgltj 存量数据过滤条件
     */
    public void setClgltj(String clgltj) {
        this.clgltj = clgltj == null ? null : clgltj.trim();
    }

    /**
     * 获取存量数据采集完成标记。0-未启动，1-正在执行，2-已完成
     *
     * @return CLWCBJ - 存量数据采集完成标记。0-未启动，1-正在执行，2-已完成
     */
    public String getClwcbj() {
        return clwcbj;
    }

    /**
     * 设置存量数据采集完成标记。0-未启动，1-正在执行，2-已完成
     *
     * @param clwcbj 存量数据采集完成标记。0-未启动，1-正在执行，2-已完成
     */
    public void setClwcbj(String clwcbj) {
        this.clwcbj = clwcbj == null ? null : clwcbj.trim();
    }

    /**
     * 获取增量数据客户端过滤类型，多个过滤条件，半角“,”分割
     *
     * @return ZLKHDGLLX - 增量数据客户端过滤类型，多个过滤条件，半角“,”分割
     */
    public String getZlkhdgllx() {
        return zlkhdgllx;
    }

    /**
     * 设置增量数据客户端过滤类型，多个过滤条件，半角“,”分割
     *
     * @param zlkhdgllx 增量数据客户端过滤类型，多个过滤条件，半角“,”分割
     */
    public void setZlkhdgllx(String zlkhdgllx) {
        this.zlkhdgllx = zlkhdgllx == null ? null : zlkhdgllx.trim();
    }

    /**
     * 获取是否采集“insert”增量数据，0-否，1-是
     *
     * @return ZLINSERT - 是否采集“insert”增量数据，0-否，1-是
     */
    public String getZlinsert() {
        return zlinsert;
    }

    /**
     * 设置是否采集“insert”增量数据，0-否，1-是
     *
     * @param zlinsert 是否采集“insert”增量数据，0-否，1-是
     */
    public void setZlinsert(String zlinsert) {
        this.zlinsert = zlinsert == null ? null : zlinsert.trim();
    }

    /**
     * 获取是否采集“update”增量数据，0-否，1-是
     *
     * @return ZLUPDATE - 是否采集“update”增量数据，0-否，1-是
     */
    public String getZlupdate() {
        return zlupdate;
    }

    /**
     * 设置是否采集“update”增量数据，0-否，1-是
     *
     * @param zlupdate 是否采集“update”增量数据，0-否，1-是
     */
    public void setZlupdate(String zlupdate) {
        this.zlupdate = zlupdate == null ? null : zlupdate.trim();
    }

    /**
     * 获取是否采集“delete”增量数据，0-否，1-是
     *
     * @return ZLDELETE - 是否采集“delete”增量数据，0-否，1-是
     */
    public String getZldelete() {
        return zldelete;
    }

    /**
     * 设置是否采集“delete”增量数据，0-否，1-是
     *
     * @param zldelete 是否采集“delete”增量数据，0-否，1-是
     */
    public void setZldelete(String zldelete) {
        this.zldelete = zldelete == null ? null : zldelete.trim();
    }

    /**
     * 获取策略创建时间，精确到秒
     *
     * @return CJSJ - 策略创建时间，精确到秒
     */
    public Date getCjsj() {
        return cjsj;
    }

    /**
     * 设置策略创建时间，精确到秒
     *
     * @param cjsj 策略创建时间，精确到秒
     */
    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    /**
     * 获取策略更新时间，精确到秒
     *
     * @return GXSJ - 策略更新时间，精确到秒
     */
    public Date getGxsj() {
        return gxsj;
    }

    /**
     * 设置策略更新时间，精确到秒
     *
     * @param gxsj 策略更新时间，精确到秒
     */
    public void setGxsj(Date gxsj) {
        this.gxsj = gxsj;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    @Override
    public String toString() {
        return "BusCfgTask{" +
                "jgxtlb='" + jgxtlb + '\'' +
                ", bm='" + bm + '\'' +
                ", bmms='" + bmms + '\'' +
                ", clcjbj='" + clcjbj + '\'' +
                ", sjczd='" + sjczd + '\'' +
                ", clqsrq=" + clqsrq +
                ", clgltj='" + clgltj + '\'' +
                ", clwcbj='" + clwcbj + '\'' +
                ", zlkhdgllx='" + zlkhdgllx + '\'' +
                ", zlinsert='" + zlinsert + '\'' +
                ", zlupdate='" + zlupdate + '\'' +
                ", zldelete='" + zldelete + '\'' +
                ", cjsj=" + cjsj +
                ", gxsj=" + gxsj +
                '}';
    }

    /**
     * 返回实体类可用于对比的内容
     * @return
     */
    public String getCompareContent(){
        StringBuilder sb = new StringBuilder();

        sb.append("{");
        sb.append("jgxtlb=").append(jgxtlb);            //系统类别代码
        sb.append(", bm='").append( bm).append("'");    //表名
        sb.append(", bmms='").append(bmms).append("'"); //表名描述
        sb.append(", clcjbj=").append(clcjbj );         //存量数据采集标记，0-不采集，1-采集
        sb.append(", sjczd='").append(sjczd ).append("'"); //时间戳字段
        sb.append(", clqsrq=").append(clqsrq==null?"":clqsrq );             //存量数据起始日期
        sb.append(", clgltj='").append(clgltj==null?"": clgltj).append("'"); //存量数据过滤条件
        sb.append(", clwcbj=").append(clwcbj );     //存量数据采集完成标记,0-未启动，1-正在执行，2-已完成
        sb.append(", zlkhdgllx='").append(zlkhdgllx==null?"":zlkhdgllx ).append("'"); //增量数据客户端过滤类型
        sb.append(", zlinsert=").append(zlinsert==null?"":zlinsert ); //是否采集“insert”增量数据，0-否，1-是
        sb.append(", zlupdate=").append(zlupdate==null?"":zlupdate ); //是否采集“update”增量数据
        sb.append(", zldelete=").append(zldelete==null?"":zldelete ); //是否采集“delete”增量数据
        sb.append(", cjsj=").append(cjsj );  //策略创建时间
        sb.append(", gxsj=").append(gxsj );  //策略更新时间
        sb.append('}');

        return sb.toString();
    }
}