package com.xhw.logcollection.monitor.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 数据采集情况统计表
 * @author 孔纲
 * @date 2018/3/7
 */
@Table(name = "bus_monitor_stats")
public class BusMonitorStats {

    /*
    备案编号
     */
    @Id
    @Column(name = "babh")
    private String babh;

    /*
    系统类别代码
     */
    @Id
    @Column(name = "jgxtlb")
    private String jgxtlb;

    /*
    统计日期， YYYYMMDD，精确到天
     */
    @Id
    @Column(name = "tjrq")
    private Date tjrq;

    /*
    存量数据采集量
     */
    @Column(name = "CLCJL")
    private Integer CLCJL;

    /*
    存量数据待采集量
     */
    @Column(name = "CLDCJL")
    private Integer CLDCJL;

    /*
    生成存量数据文件数
     */
    @Column(name = "CLWJS")
    private Integer CLWJS;

    /*
    存量数据文件上传量
     */
    @Column(name = "CLWJSCS")
    private Integer CLWJSCS;

    /*
    存量数据文件待传量
     */
    @Column(name = "CLWJDCS")
    private Integer CLWJDCS;

    /*
    增量数据采集量
     */
    @Column(name = "ZLCJL")
    private Integer ZLCJL;

    /*
    Insert采集量
     */
    @Column(name = "INSERTL")
    private Integer INSERTL;

    /*
    Update采集量
     */
    @Column(name = "UPDATEL")
    private Integer UPDATEL;

    /*
    Delete采集量
     */
    @Column(name = "DELETEL")
    private Integer DELETEL;

    /*
    生成增量数据文件数
     */
    @Column(name = "ZLWJS")
    private Integer ZLWJS;

    /*
    增量数据文件上传量
     */
    @Column(name = "ZLWJSCS")
    private Integer ZLWJSCS;

    /*
    增量数据文件待传量
     */
    @Column(name = "ZLWJDCS")
    private Integer ZLWJDCS;

    /*
    增量sequence号差量，数据库最新SEQ与已采集到的SEQ差
     */
    @Column(name = "SEQC")
    private Integer SEQC;

    /*
   DDL数据采集量
    */
    @Column(name = "DDLL")
    private Integer DDLL;

    /*
   更新时间，精确到秒
    */
    @Column(name = "GXSJ")
    private Date GXSJ;

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

    public Date getTjrq() {
        return tjrq;
    }

    public void setTjrq(Date tjrq) {
        this.tjrq = tjrq;
    }

    public Integer getCLCJL() {
        return CLCJL;
    }

    public void setCLCJL(Integer CLCJL) {
        this.CLCJL = CLCJL;
    }

    public Integer getCLDCJL() {
        return CLDCJL;
    }

    public void setCLDCJL(Integer CLDCJL) {
        this.CLDCJL = CLDCJL;
    }

    public Integer getCLWJS() {
        return CLWJS;
    }

    public void setCLWJS(Integer CLWJS) {
        this.CLWJS = CLWJS;
    }

    public Integer getCLWJSCS() {
        return CLWJSCS;
    }

    public void setCLWJSCS(Integer CLWJSCS) {
        this.CLWJSCS = CLWJSCS;
    }

    public Integer getCLWJDCS() {
        return CLWJDCS;
    }

    public void setCLWJDCS(Integer CLWJDCS) {
        this.CLWJDCS = CLWJDCS;
    }

    public Integer getZLCJL() {
        return ZLCJL;
    }

    public void setZLCJL(Integer ZLCJL) {
        this.ZLCJL = ZLCJL;
    }

    public Integer getINSERTL() {
        return INSERTL;
    }

    public void setINSERTL(Integer INSERTL) {
        this.INSERTL = INSERTL;
    }

    public Integer getUPDATEL() {
        return UPDATEL;
    }

    public void setUPDATEL(Integer UPDATEL) {
        this.UPDATEL = UPDATEL;
    }

    public Integer getDELETEL() {
        return DELETEL;
    }

    public void setDELETEL(Integer DELETEL) {
        this.DELETEL = DELETEL;
    }

    public Integer getZLWJS() {
        return ZLWJS;
    }

    public void setZLWJS(Integer ZLWJS) {
        this.ZLWJS = ZLWJS;
    }

    public Integer getZLWJSCS() {
        return ZLWJSCS;
    }

    public void setZLWJSCS(Integer ZLWJSCS) {
        this.ZLWJSCS = ZLWJSCS;
    }

    public Integer getZLWJDCS() {
        return ZLWJDCS;
    }

    public void setZLWJDCS(Integer ZLWJDCS) {
        this.ZLWJDCS = ZLWJDCS;
    }

    public Integer getSEQC() {
        return SEQC;
    }

    public void setSEQC(Integer SEQC) {
        this.SEQC = SEQC;
    }

    public Integer getDDLL() {
        return DDLL;
    }

    public void setDDLL(Integer DDLL) {
        this.DDLL = DDLL;
    }

    public Date getGXSJ() {
        return GXSJ;
    }

    public void setGXSJ(Date GXSJ) {
        this.GXSJ = GXSJ;
    }
}
