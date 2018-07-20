package com.xhw.logcollection.monitor.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "BUS_AUDIT_DDL")
public class BusAuditDdl {
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
    private Long seq;

    /**
     * 数据库用户名
     */
    @Column(name = "ORAUSER")
    private String orauser;

    /**
     * Schema对象名
     */
    @Column(name = "ORASCHEMA")
    private String oraschema;

    /**
     * 操作类型。01-create or replace，02-drop，03-alter，04-rename，05-truncate，06-revoke，99-其他
     */
    @Column(name = "CZLX")
    private String czlx;

    /**
     * 操作对象类型。01-table，02-view，03-procedure，04-tablespace，05-function，06-trigger， 07-index，08-package，09- package body，10- sequence，11- synonym，12- role，99-其他
     */
    @Column(name = "DXLX")
    private String dxlx;

    /**
     * DDL对象名
     */
    @Column(name = "DXM")
    private String dxm;

    /**
     * DDL操作时间，精确到秒
     */
    @Column(name = "CZSJ")
    private Date czsj;

    /**
     * 操作内容
     */
    @Column(name = "NR")
    private String nr;

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
    public Long getSeq() {
        return seq;
    }

    /**
     * 设置sequence号
     *
     * @param seq sequence号
     */
    public void setSeq(Long seq) {
        this.seq = seq;
    }

    /**
     * 获取数据库用户名
     *
     * @return ORAUSER - 数据库用户名
     */
    public String getOrauser() {
        return orauser;
    }

    /**
     * 设置数据库用户名
     *
     * @param orauser 数据库用户名
     */
    public void setOrauser(String orauser) {
        this.orauser = orauser == null ? null : orauser.trim();
    }

    /**
     * 获取Schema对象名
     *
     * @return ORASCHEMA - Schema对象名
     */
    public String getOraschema() {
        return oraschema;
    }

    /**
     * 设置Schema对象名
     *
     * @param oraschema Schema对象名
     */
    public void setOraschema(String oraschema) {
        this.oraschema = oraschema == null ? null : oraschema.trim();
    }

    /**
     * 获取操作类型。01-create or replace，02-drop，03-alter，04-rename，05-truncate，06-revoke，99-其他
     *
     * @return CZLX - 操作类型。01-create or replace，02-drop，03-alter，04-rename，05-truncate，06-revoke，99-其他
     */
    public String getCzlx() {
        return czlx;
    }

    /**
     * 设置操作类型。01-create or replace，02-drop，03-alter，04-rename，05-truncate，06-revoke，99-其他
     *
     * @param czlx 操作类型。01-create or replace，02-drop，03-alter，04-rename，05-truncate，06-revoke，99-其他
     */
    public void setCzlx(String czlx) {
        this.czlx = czlx == null ? null : czlx.trim();
    }

    /**
     * 获取操作对象类型。01-table，02-view，03-procedure，04-tablespace，05-function，06-trigger， 07-index，08-package，09- package body，10- sequence，11- synonym，12- role，99-其他
     *
     * @return DXLX - 操作对象类型。01-table，02-view，03-procedure，04-tablespace，05-function，06-trigger， 07-index，08-package，09- package body，10- sequence，11- synonym，12- role，99-其他
     */
    public String getDxlx() {
        return dxlx;
    }

    /**
     * 设置操作对象类型。01-table，02-view，03-procedure，04-tablespace，05-function，06-trigger， 07-index，08-package，09- package body，10- sequence，11- synonym，12- role，99-其他
     *
     * @param dxlx 操作对象类型。01-table，02-view，03-procedure，04-tablespace，05-function，06-trigger， 07-index，08-package，09- package body，10- sequence，11- synonym，12- role，99-其他
     */
    public void setDxlx(String dxlx) {
        this.dxlx = dxlx == null ? null : dxlx.trim();
    }

    /**
     * 获取DDL对象名
     *
     * @return DXM - DDL对象名
     */
    public String getDxm() {
        return dxm;
    }

    /**
     * 设置DDL对象名
     *
     * @param dxm DDL对象名
     */
    public void setDxm(String dxm) {
        this.dxm = dxm == null ? null : dxm.trim();
    }

    /**
     * 获取DDL操作时间，精确到秒
     *
     * @return CZSJ - DDL操作时间，精确到秒
     */
    public Date getCzsj() {
        return czsj;
    }

    /**
     * 设置DDL操作时间，精确到秒
     *
     * @param czsj DDL操作时间，精确到秒
     */
    public void setCzsj(Date czsj) {
        this.czsj = czsj;
    }

    /**
     * 获取操作内容
     *
     * @return NR - 操作内容
     */
    public String getNr() {
        return nr;
    }

    /**
     * 设置操作内容
     *
     * @param nr 操作内容
     */
    public void setNr(String nr) {
        this.nr = nr == null ? null : nr.trim();
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