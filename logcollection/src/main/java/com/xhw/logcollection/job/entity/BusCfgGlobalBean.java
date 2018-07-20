package com.xhw.logcollection.job.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日志采集全局参数bean实体类
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-03-01
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public class BusCfgGlobalBean {
    //安装代码
    private String azdm;
    //存量数据单个数据块最大数据量,表记录数限制
    private long clsjkzdz;
    //存量数据采集任务最大数据库连接数
    private int clsjkljzdz;
    //存量数据采集任务启动时间，单位小时（HH24）
    private int clrwqdsj;
    //存量数据采集任务结束时间，单位小时（HH24）
    private int clrwjssj;
    //增量数据采集周期，单位分钟
    private int zlcjzq;
    //日志解析文件最大值，单位MB
    private int rzjxwjzdz;
    //存量数据文件上传目录
    private String clscml;
    //增量数据文件上传目录
    private String zlscml;
    //交管信息系统类别 eg:10#综合应用平台,60#集成指挥平台
    private String jgxtlb;
    //交管信息系统类别(保存解析后的结果)
    private List<Map<String, String>> xtlb;

    /**
     * 安装代码
     * @return
     */
    public String getAzdm() {
        return azdm;
    }

    /**
     * 安装代码
     * @param azdm
     */
    public void setAzdm(String azdm) {
        this.azdm = azdm;
    }

    /**
     * 存量数据单个数据块最大数据量,表记录数限制
     * @return
     */
    public long getClsjkzdz() {
        return clsjkzdz;
    }

    /**
     * 存量数据单个数据块最大数据量,表记录数限制
     * @param clsjkzdz
     */
    public void setClsjkzdz(long clsjkzdz) {
        this.clsjkzdz = clsjkzdz;
    }

    /**
     * 存量数据采集任务最大数据库连接数
     * @return
     */
    public int getClsjkljzdz() {
        return clsjkljzdz;
    }

    /**
     * 存量数据采集任务最大数据库连接数
     * @param clsjkljzdz
     */
    public void setClsjkljzdz(int clsjkljzdz) {
        this.clsjkljzdz = clsjkljzdz;
    }

    /**
     * 存量数据采集任务启动时间，单位小时（HH24）
     * @return
     */
    public int getClrwqdsj() {
        return clrwqdsj;
    }

    /**
     * 存量数据采集任务启动时间，单位小时（HH24）
     * @param clrwqdsj
     */
    public void setClrwqdsj(int clrwqdsj) {
        this.clrwqdsj = clrwqdsj;
    }

    /**
     * 存量数据采集任务结束时间，单位小时（HH24）
     * @return
     */
    public int getClrwjssj() {
        return clrwjssj;
    }

    /**
     * 存量数据采集任务结束时间，单位小时（HH24）
     * @param clrwjssj
     */
    public void setClrwjssj(int clrwjssj) {
        this.clrwjssj = clrwjssj;
    }

    /**
     * 增量数据采集周期，单位分钟
     * @return
     */
    public int getZlcjzq() {
        return zlcjzq;
    }

    /**
     * 增量数据采集周期，单位分钟
     * @param zlcjzq
     */
    public void setZlcjzq(int zlcjzq) {
        this.zlcjzq = zlcjzq;
    }

    /**
     * 日志解析文件最大值，单位MB
     * @return
     */
    public int getRzjxwjzdz() {
        return rzjxwjzdz;
    }

    /**
     * 日志解析文件最大值，单位MB
     * @param rzjxwjzdz
     */
    public void setRzjxwjzdz(int rzjxwjzdz) {
        this.rzjxwjzdz = rzjxwjzdz;
    }

    /**
     * 存量数据文件上传目录
     * @return
     */
    public String getClscml() {
        return clscml;
    }

    /**
     * 存量数据文件上传目录
     * @param clscml
     */
    public void setClscml(String clscml) {
        this.clscml = clscml;
    }

    /**
     * 增量数据文件上传目录
     * @return
     */
    public String getZlscml() {
        return zlscml;
    }

    /**
     * 增量数据文件上传目录
     * @param zlscml
     */
    public void setZlscml(String zlscml) {
        this.zlscml = zlscml;
    }

    /**
     * 交管信息系统类别 eg:10#综合应用平台,60#集成指挥平台
     * @return
     */
    public String getJgxtlb() {
        return jgxtlb;
    }

    /**
     * 交管信息系统类别 eg:10#综合应用平台,60#集成指挥平台
     * @param jgxtlb
     */
    public void setJgxtlb(String jgxtlb) {
        this.jgxtlb = jgxtlb;
        // 解析，并把结果缓存在xtlb字段上
        List<Map<String, String>> xtlb = new ArrayList<>();
        if(jgxtlb != null){
            String[] parts = jgxtlb.split(",");
            for(String part:parts){
                Map<String, String> item = new HashMap<>();
                String code = part.substring(0,part.indexOf("#"));
                String name = part.substring(part.indexOf("#") + 1);
                item.put("code", code);
                item.put("name", name);
                xtlb.add(item);
            }
        }
        this.xtlb = xtlb;
    }

    public List<Map<String, String>> getXtlb() {
        return xtlb;
    }

    @Override
    public String toString() {
        return "BusCfgGlobalBean{" +
                "azdm='" + azdm + '\'' +
                ", clsjkzdz=" + clsjkzdz +
                ", clsjkljzdz=" + clsjkljzdz +
                ", clrwqdsj=" + clrwqdsj +
                ", clrwjssj=" + clrwjssj +
                ", zlcjzq=" + zlcjzq +
                ", rzjxwjzdz=" + rzjxwjzdz +
                ", clscml='" + clscml + '\'' +
                ", zlscml='" + zlscml + '\'' +
                ", jgxtlb='" + jgxtlb + '\'' +
                '}';
    }
}
