package com.xhw.logcollection.analyze.sqlparse;

import com.xhw.logcollection.model.dto.ResultColVal;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitor;

/**
 * @author wanghaiyang
 * @version 0.0.1
 * @date 2018/3/6 11:36
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public class SelectItemVisitorImpl implements SelectItemVisitor {

    private ResultColVal resultColVal;

    public SelectItemVisitorImpl(ResultColVal resultColVal){
        this.resultColVal = resultColVal;
    }

    @Override
    public void visit(AllColumns allColumns) {
        System.out.println("allColumns="+allColumns);
    }

    @Override
    public void visit(AllTableColumns allTableColumns) {
        System.out.println("allTableColumns="+allTableColumns);
    }

    @Override
    public void visit(SelectExpressionItem selectExpressionItem) {
        //System.out.println("selectExpressionItem="+selectExpressionItem);
        System.out.println(selectExpressionItem.getExpression().toString());
        this.resultColVal.setVal(selectExpressionItem.getExpression().toString());
    }
}
