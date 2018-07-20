package com.xhw.logcollection.model.dto;

/**
 * @author wanghaiyang
 * @version 0.0.1
 * @date 2018/3/5 10:21
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public class ResultColVal {
    private String col; //字段名称
    private String val; //值

    public ResultColVal(String col, String val){
        this.col = col;
        this.val = val;
    }

    public String getCol() {
        return col;
    }

    public void setCol(String col) {
        this.col = col;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return "ResultColVal{" +
                "col='" + col + '\'' +
                ", val='" + val + '\'' +
                '}';
    }

    public String toXml(){
        StringBuilder xml = new StringBuilder();
        xml.append("<").append(this.col).append(">");
        xml.append(this.val);
        xml.append("</").append(this.col).append(">");

        return xml.toString();
    }
}
