package com.xhw.logcollection.monitor.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "BUS_STOCK_BREAK_POINT_INFO")
public class BusStockBreakPointInfo {
    /**
     * 系统类别代码
     */
    @Id
    @Column(name = "JGXTLB")
    private String jgxtlb;

    /**
     * 自增主键
     */
    @Column(name = "id")
    private String id;

    /**
     * 表名
     */
    @Id
    @Column(name = "BM")
    private String bm;

    /**
     * 数据块编号
     */
    @Id
    @Column(name = "SJKBH")
    private BigDecimal sjkbh;

    /**
     * 时间戳起
     */
    @Column(name = "SJCQ")
    private Date sjcq;

    /**
     * 时间戳止
     */
    @Column(name = "SJCZ")
    private Date sjcz;

    /**
     * 当前时间戳
     */
    @Column(name = "DQSJC")
    private Date dqsjc;

    /**
     * 完成标记，0-未完成，1-已完成
     */
    @Column(name = "WCBJ")
    private String wcbj;

    /**
     * 更新时间，精确到秒
     */
    @Column(name = "GXSJ")
    private Date gxsj;

    /**
     * 获取系统类别代码
     *
     * @return JGXTLB - 系统类别代码
     */
    public String getJgxtlb() {
        return jgxtlb;
    }

    /**
     * 设置系统类别代码
     *
     * @param jgxtlb 系统类别代码
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
     * 获取数据块编号
     *
     * @return SJKBH - 数据块编号
     */
    public BigDecimal getSjkbh() {
        return sjkbh;
    }

    /**
     * 设置数据块编号
     *
     * @param sjkbh 数据块编号
     */
    public void setSjkbh(BigDecimal sjkbh) {
        this.sjkbh = sjkbh;
    }

    /**
     * 获取时间戳起
     *
     * @return SJCQ - 时间戳起
     */
    public Date getSjcq() {
        return sjcq;
    }

    /**
     * 设置时间戳起
     *
     * @param sjcq 时间戳起
     */
    public void setSjcq(Date sjcq) {
        this.sjcq = sjcq;
    }

    /**
     * 获取时间戳止
     *
     * @return SJCZ - 时间戳止
     */
    public Date getSjcz() {
        return sjcz;
    }

    /**
     * 设置时间戳止
     *
     * @param sjcz 时间戳止
     */
    public void setSjcz(Date sjcz) {
        this.sjcz = sjcz;
    }

    /**
     * 获取当前时间戳
     *
     * @return DQSJC - 当前时间戳
     */
    public Date getDqsjc() {
        return dqsjc;
    }

    /**
     * 设置当前时间戳
     *
     * @param dqsjc 当前时间戳
     */
    public void setDqsjc(Date dqsjc) {
        this.dqsjc = dqsjc;
    }

    /**
     * 获取完成标记，0-未完成，1-已完成
     *
     * @return WCBJ - 完成标记，0-未完成，1-已完成
     */
    public String getWcbj() {
        return wcbj;
    }

    /**
     * 设置完成标记，0-未完成，1-已完成
     *
     * @param wcbj 完成标记，0-未完成，1-已完成
     */
    public void setWcbj(String wcbj) {
        this.wcbj = wcbj == null ? null : wcbj.trim();
    }

    /**
     * 获取更新时间，精确到秒
     *
     * @return GXSJ - 更新时间，精确到秒
     */
    public Date getGxsj() {
        return gxsj;
    }

    /**
     * 设置更新时间，精确到秒
     *
     * @param gxsj 更新时间，精确到秒
     */
    public void setGxsj(Date gxsj) {
        this.gxsj = gxsj;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}