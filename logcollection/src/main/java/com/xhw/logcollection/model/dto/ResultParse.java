package com.xhw.logcollection.model.dto;

/**
 * @author wanghaiyang
 * @version 0.0.1
 * @date 2018/3/5 10:12
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public class ResultParse {

    public ResultListColVal dataVal;
    public ResultListColVal whereVal;

    public ResultParse(){
        dataVal  = new ResultListColVal();
        whereVal = new ResultListColVal();
    }

    public ResultListColVal getDataVal() {
        return dataVal;
    }

    public ResultListColVal getWhereVal() {
        return whereVal;
    }

    public void test(){
        System.out.println("===============data value=====================");
        for (ResultColVal resultColVal : dataVal.lstData) {
            System.out.println(resultColVal);
        }

        System.out.println("===============where value=====================");
        for (ResultColVal resultColVal : whereVal.lstData) {
            System.out.println(resultColVal);
        }
    }

    private String toXml(String tagName, ResultListColVal resultListColVal){
        StringBuilder xml = new StringBuilder();
        xml.append("<").append(tagName).append(">");
        for (ResultColVal resultColVal : resultListColVal.lstData) {
            xml.append(resultColVal.toXml());
        }

        xml.append("</").append(tagName).append(">");
        return xml.toString();
    }

    public String toDataValueXml(){
        return this.toXml("data_value", this.dataVal);
    }
    public String toWhereValueXml(){
        return this.toXml("where_value", this.whereVal);
    }
    public String toOldValueXml(){
        return this.toXml("old_value", this.dataVal);
    }

}
