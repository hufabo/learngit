package com.xhw.logcollection.monitor.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "BUS_INCREMENT_BREAK_POINT_INFO")
public class BusIncrementBreakPointInfo {
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
     * scn号
     */
    @Column(name = "SCN")
    private BigDecimal scn;

    /**
     * sequence号
     */
    @Column(name = "SEQ")
    private BigDecimal seq;

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
     * 获取scn号
     *
     * @return SCN - scn号
     */
    public BigDecimal getScn() {
        return scn;
    }

    /**
     * 设置scn号
     *
     * @param scn scn号
     */
    public void setScn(BigDecimal scn) {
        this.scn = scn;
    }

    /**
     * 获取sequence号
     *
     * @return SEQ - sequence号
     */
    public BigDecimal getSeq() {
        return seq;
    }

    /**
     * 设置sequence号
     *
     * @param seq sequence号
     */
    public void setSeq(BigDecimal seq) {
        this.seq = seq;
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