package com.xhw.logcollection.model.api;

import com.github.pagehelper.PageInfo;
import com.xhw.logcollection.util.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 接口返回统一的公共类
 * @Author rentie
 * @Date 2017/10/14 13:32
 */
public class ApiResultModel {
    /**
     * 状态：0失败，1成功,-1异常
     */
    private String status;

    /**
     * 返回数据
     */
    private Object data;

    /**
     * 返回提示
     */
    private String reason;

    /**
     * 错误信息
     */
    private String errorMessage;

    public ApiResultModel() {
        super();
    }

    public ApiResultModel(String status, Object data, String reason, String errorMessage) {
        super();
        this.status = status;
        this.data = data;
        this.reason = reason;
        this.errorMessage = errorMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setPageInfo(PageInfo<? extends Object> pageInfo) {
        if(pageInfo!=null && pageInfo.getList().size()>0){
            this.setStatus("1");
            this.setReason("数据获取成功");

            JSONObject tbObj = new JSONObject();
            JSONArray dataArr = JSON.dataTrans(pageInfo.getList());
            tbObj.put("total",pageInfo.getTotal());
            tbObj.put("rows",dataArr);
            this.setData(tbObj);

        }else if(pageInfo.getList().size() == 0) {
            this.setStatus("1");
            this.setReason("暂无数据");
        }else {
            this.setStatus("0");
            this.setReason("数据获取失败");
        }
    }
}
