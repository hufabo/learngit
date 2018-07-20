package com.xhw.logcollection.analyze.sqlparse;

import com.xhw.logcollection.model.dto.ResultParse;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsListVisitor;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import net.sf.jsqlparser.statement.select.SubSelect;

import java.util.List;

/**
 * @author wanghaiyang
 * @version 0.0.1
 * @date 2018/3/5 11:22
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public class ItemsListVisitorImpl implements ItemsListVisitor {
    private ResultParse result;
    public ItemsListVisitorImpl(ResultParse result){
        this.result = result;
    }

    @Override
    public void visit(SubSelect subSelect) {
        System.out.println("subSelect=="+subSelect);
    }

    @Override
    public void visit(ExpressionList expressionList) {
        System.out.println("expressionList=="+expressionList);
        List<Expression> list =	expressionList.getExpressions();
        if(list!= null) {
            for (int i = 0; i <list.size() ; i++) {
                Expression expr = list.get(i);

                //设置值
                this.result.getDataVal().listAll().get(i).setVal(expr.toString());
            }
        }
    }

    @Override
    public void visit(MultiExpressionList multiExpressionList) {
        System.out.println("multiExpressionList=="+multiExpressionList);
    }
}
