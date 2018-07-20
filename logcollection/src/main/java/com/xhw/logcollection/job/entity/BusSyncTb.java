package com.xhw.logcollection.job.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "BUS_SYNC_TB")
public class BusSyncTb {
    /**
     * 同步批次号
     */
    @Id
    @Column(name = "TBPCH")
    private String tbpch;

    /**
     * 同步总数据量
     */
    @Column(name = "TBZL")
    private Integer tbzl;

    /**
     * 同步数据量
     */
    @Column(name = "TBSJL")
    private Integer tbsjl;

    /**
     * 同步标识位
     */
    @Column(name = "TBBZW")
    private BigDecimal tbbzw;

    /**
     * 更新时间
     */
    @Column(name = "GXSJ")
    private Date gxsj;

    /**
     * 同步任务类型,1软件心跳,2软件运行状态,3存量数据处理状态,
4存量数据块断点,5增量数据断点,6存量数据文件,7增量数据文件,
8DDL数据审计,9数据文件入库反馈

     */
    @Column(name = "TBLX")
    private String tblx;

    /**
     * 同步结果代码
     */
    @Column(name = "rstcode")
    private String rstCode;

    /**
     * 同步结果描述信息
     */
    @Column(name = "rstmsg")
    private String rstMsg;

    /**
     * 同步结果加密串
     */
    @Column(name = "rstkeystr")
    private String rstKeyStr;

    /**
     * 获取同步批次号
     *
     * @return TBPCH - 同步批次号
     */
    public String getTbpch() {
        return tbpch;
    }

    /**
     * 设置同步批次号
     *
     * @param tbpch 同步批次号
     */
    public void setTbpch(String tbpch) {
        this.tbpch = tbpch == null ? null : tbpch.trim();
    }

    /**
     * 获取同步总数据量
     *
     * @return TBZL - 同步总数据量
     */
    public Integer getTbzl() {
        return tbzl;
    }

    /**
     * 设置同步总数据量
     *
     * @param tbzl 同步总数据量
     */
    public void setTbzl(Integer tbzl) {
        this.tbzl = tbzl;
    }

    /**
     * 获取同步数据量
     *
     * @return TBSJL - 同步数据量
     */
    public Integer getTbsjl() {
        return tbsjl;
    }

    /**
     * 设置同步数据量
     *
     * @param tbsjl 同步数据量
     */
    public void setTbsjl(Integer tbsjl) {
        this.tbsjl = tbsjl;
    }

    /**
     * 获取同步标识位
     *
     * @return TBBZW - 同步标识位
     */
    public BigDecimal getTbbzw() {
        return tbbzw;
    }

    /**
     * 设置同步标识位
     *
     * @param tbbzw 同步标识位
     */
    public void setTbbzw(BigDecimal tbbzw) {
        this.tbbzw = tbbzw;
    }

    /**
     * 获取更新时间
     *
     * @return GXSJ - 更新时间
     */
    public Date getGxsj() {
        return gxsj;
    }

    /**
     * 设置更新时间
     *
     * @param gxsj 更新时间
     */
    public void setGxsj(Date gxsj) {
        this.gxsj = gxsj;
    }

    /**
     * 获取同步任务类型,1软件心跳,2软件运行状态,3存量数据处理状态,
4存量数据块断点,5增量数据断点,6存量数据文件,7增量数据文件,
8DDL数据审计,9数据文件入库反馈

     *
     * @return TBLX - 同步任务类型,1软件心跳,2软件运行状态,3存量数据处理状态,
4存量数据块断点,5增量数据断点,6存量数据文件,7增量数据文件,
8DDL数据审计,9数据文件入库反馈

     */
    public String getTblx() {
        return tblx;
    }

    /**
     * 设置同步任务类型,1软件心跳,2软件运行状态,3存量数据处理状态,
4存量数据块断点,5增量数据断点,6存量数据文件,7增量数据文件,
8DDL数据审计,9数据文件入库反馈

     *
     * @param tblx 同步任务类型,1软件心跳,2软件运行状态,3存量数据处理状态,
4存量数据块断点,5增量数据断点,6存量数据文件,7增量数据文件,
8DDL数据审计,9数据文件入库反馈

     */
    public void setTblx(String tblx) {
        this.tblx = tblx == null ? null : tblx.trim();
    }

    public String getRstCode() {
        return rstCode;
    }

    public void setRstCode(String rstCode) {
        this.rstCode = rstCode;
    }

    public String getRstMsg() {
        return rstMsg;
    }

    public void setRstMsg(String rstMsg) {
        this.rstMsg = rstMsg;
    }

    public String getRstKeyStr() {
        return rstKeyStr;
    }

    public void setRstKeyStr(String rstKeyStr) {
        this.rstKeyStr = rstKeyStr;
    }
}