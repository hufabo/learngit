package com.xhw.logcollection.model.dto;

import java.util.Date;

/**
 * 增量日志数据实体对象
 * @version 0.0.1
 * @Author wanghaiyang
 * @Description
 * @Date 2018/2/27 17:17
 * @Modified By:
 */
public class IncrementDataDto {

    /**
     * 当前会话连接用户名
     */
    private String segOwner;

    /**
     * 操作对象类型
     */
    private String segTypeName;

    /**
     * 会话号
     */
    private String session;

    /**
     * serial
     */
    private String serial;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 系统用户名
     */
    private String osUserName;

    /**
     * 机器名
     */
    private String machineName;

    /**
     * 提交时间
     */
    private Date commitTimeStamp;

    /**
     * 会话信息，包括登录用户名，客户端用户名，机器名，进程ID，程序名称
     */
    private String sessionInfo;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 操作类型
     */
    private String operation;

    /**
     * 操作sql
     */
    private String sqlRedo;

    /**
     * 撤销sql
     */
    private String sqlUndo;

    /**
     * scn
     */
    private String scn;

    /**
     * 开始scn
     */
    private String startScn;

    /**
     * 提交scn
     */
    private String commintScn;

    /**
     * 时间
     */
    private Date timeStamp;

    /**
     * 开始时间
     */
    private Date startTimeStamp;

    /**
     * 表空间
     */
    private String tableSpace;

    /**
     * 序号
     */
    private String sequence;

    /**
     * redo value
     */
    private String redoValue;

    /**
     * undo value
     */
    private String undoValue;

    public String getSession() {
        return session;
    }

    public String getSegOwner() {
        return segOwner;
    }

    public void setSegOwner(String segOwner) {
        this.segOwner = segOwner;
    }

    public String getSegTypeName() {
        return segTypeName;
    }

    public void setSegTypeName(String segTypeName) {
        this.segTypeName = segTypeName;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOsUserName() {
        return osUserName;
    }

    public void setOsUserName(String osUserName) {
        this.osUserName = osUserName;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public Date getCommitTimeStamp() {
        return commitTimeStamp;
    }

    public void setCommitTimeStamp(Date commitTimeStamp) {
        this.commitTimeStamp = commitTimeStamp;
    }

    public String getSessionInfo() {
        return sessionInfo;
    }

    public void setSessionInfo(String sessionInfo) {
        this.sessionInfo = sessionInfo;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getSqlRedo() {
        return sqlRedo;
    }

    public void setSqlRedo(String sqlRedo) {
        this.sqlRedo = sqlRedo;
    }

    public String getSqlUndo() {
        return sqlUndo;
    }

    public void setSqlUndo(String sqlUndo) {
        this.sqlUndo = sqlUndo;
    }

    public String getScn() {
        return scn;
    }

    public void setScn(String scn) {
        this.scn = scn;
    }

    public String getStartScn() {
        return startScn;
    }

    public void setStartScn(String startScn) {
        this.startScn = startScn;
    }

    public String getCommintScn() {
        return commintScn;
    }

    public void setCommintScn(String commintScn) {
        this.commintScn = commintScn;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Date getStartTimeStamp() {
        return startTimeStamp;
    }

    public void setStartTimeStamp(Date startTimeStamp) {
        this.startTimeStamp = startTimeStamp;
    }

    public String getTableSpace() {
        return tableSpace;
    }

    public void setTableSpace(String tableSpace) {
        this.tableSpace = tableSpace;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getRedoValue() {
        return redoValue;
    }

    public void setRedoValue(String redoValue) {
        this.redoValue = redoValue;
    }

    public String getUndoValue() {
        return undoValue;
    }

    public void setUndoValue(String undoValue) {
        this.undoValue = undoValue;
    }
}
