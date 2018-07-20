package com.xhw.logcollection.model.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wanghaiyang
 * @version 0.0.1
 * @date 2018/3/5 10:26
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public class ResultListColVal {
    List<ResultColVal> lstData;
    public ResultListColVal(){
        this.lstData = new ArrayList<>();
    }

    public void addResultColVal(ResultColVal resultColVal){
        this.lstData.add(resultColVal);
    }

    public List<ResultColVal> listAll(){
        return this.lstData;
    }
}
