package com.xhw.logcollection.model.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @Author 孔纲
 * @Date 2018/3/10
 */
@XmlRootElement(name = "root")
public class FileResultStatusDto{

    private QueryResultHead head;

    private List<FileResultBody> bodies;

    /*
     * 如果java对象属性同时添加了get和set方法，注解不能定义在属性的定义上，只需在get或者set方法上定义一个即可，否则jaxb会报错
     */
    @XmlElementWrapper(name = "body")
    @XmlElement(name = "drvexam")
    public List<FileResultBody> getBodies() {
        return bodies;
    }

    public void setBodies(List<FileResultBody> bodies) {
        this.bodies = bodies;
    }

    public QueryResultHead getHead() {
        return head;
    }

    public void setHead(QueryResultHead head) {
        this.head = head;
    }

    /*
     * 内部类操作必须是静态的，否则无法解析
     */
    public static class FileResultBody{

        private String wjm;
        private String status;

        public String getWjm() {
            return wjm;
        }

        public void setWjm(String wjm) {
            this.wjm = wjm;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public static class QueryResultHead {
        private String code;
        private String message;
        private String rownum;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getRownum() {
            return rownum;
        }

        public void setRownum(String rownum) {
            this.rownum = rownum;
        }
    }
}


