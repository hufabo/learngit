package com.xhw.logcollection.analyze.sqlparse;

import com.xhw.logcollection.model.dto.ResultListColVal;
import net.sf.jsqlparser.statement.select.*;

import java.util.List;

/**
 * @author wanghaiyang
 * @version 0.0.1
 * @date 2018/3/6 11:25
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public class SelectVisitorImpl implements SelectVisitor {

    private ResultListColVal resultListColVal;

    public SelectVisitorImpl(ResultListColVal resultListColVal){
        this.resultListColVal=resultListColVal;
    }

    @Override
    public void visit(PlainSelect plainSelect) {
        List<SelectItem> selectItems = plainSelect.getSelectItems();
        for (int i = 0;i<selectItems.size(); i++) {
            selectItems.get(i).accept(new SelectItemVisitorImpl(this.resultListColVal.listAll().get(i)));
        }
    }

    @Override
    public void visit(SetOperationList setOperationList) {
        System.out.println("setOperationList="+setOperationList);
        List<SelectBody> selectBodyList = setOperationList.getSelects();
        for (SelectBody selectBody:selectBodyList){
           selectBody.accept(new SelectVisitorImpl(this.resultListColVal));
        }
    }

    @Override
    public void visit(WithItem withItem) {
        System.out.println("withItem="+withItem);
    }
}
