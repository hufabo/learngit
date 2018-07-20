package com.xhw.logcollection.model.dto;

import com.xhw.logcollection.monitor.entity.BusStockBreakPointInfo;
import com.xhw.logcollection.monitor.entity.BusStockFileInfo;

import javax.persistence.Column;
import java.util.Date;

/**
 * 存量断点文件数据传输对象
 * @author wanghaiyang
 * @version 0.0.1
 * @date 2018/3/2 15:00
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public class BusStockBreakFileDto extends BusStockFileInfo {

    /*************存量数据块断点信息*************/

    /**
     * 完成标记，0-未完成，1-已完成
     */
    private String wcbj;

    /**
     * 时间戳起
     */
    private Date bsjcq;

    /**
     * 时间戳止
     */
    private Date bsjcz;

    /**
     * 当前时间戳
     */
    private Date bdqsjc;

    /**
     * 更新时间，精确到秒
     */
    private Date bgxsj;

    public String getWcbj() {
        return wcbj;
    }

    public void setWcbj(String wcbj) {
        this.wcbj = wcbj;
    }

    public Date getBsjcq() {
        return bsjcq;
    }

    public void setBsjcq(Date bsjcq) {
        this.bsjcq = bsjcq;
    }

    public Date getBsjcz() {
        return bsjcz;
    }

    public void setBsjcz(Date bsjcz) {
        this.bsjcz = bsjcz;
    }

    public Date getBdqsjc() {
        return bdqsjc;
    }

    public void setBdqsjc(Date bdqsjc) {
        this.bdqsjc = bdqsjc;
    }

    public Date getBgxsj() {
        return bgxsj;
    }

    public void setBgxsj(Date bgxsj) {
        this.bgxsj = bgxsj;
    }
}
