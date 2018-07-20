package com.xhw.logcollection.analyze.sqlparse;

import com.xhw.logcollection.model.dto.ResultColVal;
import com.xhw.logcollection.model.dto.ResultListColVal;
import com.xhw.logcollection.model.dto.ResultParse;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Commit;
import net.sf.jsqlparser.statement.SetStatement;
import net.sf.jsqlparser.statement.StatementVisitor;
import net.sf.jsqlparser.statement.Statements;
import net.sf.jsqlparser.statement.alter.Alter;
import net.sf.jsqlparser.statement.create.index.CreateIndex;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.create.view.AlterView;
import net.sf.jsqlparser.statement.create.view.CreateView;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.statement.execute.Execute;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.merge.Merge;
import net.sf.jsqlparser.statement.replace.Replace;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.upsert.Upsert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wanghaiyang
 * @version 0.0.1
 * @date 2018/3/5 9:36
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public class StatementVisitorImpl implements StatementVisitor {
    private ResultParse result ;
    public StatementVisitorImpl(ResultParse result) {
        this.result = result;
    }

    @Override
    public void visit(Select select) {

    }

    @Override
    public void visit(Upsert upsert) {

    }

    @Override
    public void visit(Commit commit) {

    }

    @Override
    public void visit(Delete delete) {
        Expression exprWhere = delete.getWhere();
        ResultListColVal dataValue = this.result.getDataVal();
        exprWhere.accept(new ExpressionVisitorImpl(this.result.getWhereVal()));
    }

    @Override
    public void visit(Update update) {
        System.out.println("update==="+update.toString());

        List<Column> setClolums = update.getColumns();
        List<Expression> expressions =	update.getExpressions();

        ResultListColVal dataValue = this.result.getDataVal();
        for (int i = 0; i < setClolums.size() ; i++) {
            Column col = setClolums.get(i);

            Expression expression = expressions.get(i);
            ResultColVal resultColVal = new ResultColVal(col.toString(), expression.toString());
            dataValue.addResultColVal(resultColVal);

            System.out.println(col+","+expression);
        }

        Expression exprWhere =  update.getWhere();
        System.out.println("exprWhere=="+exprWhere);
        exprWhere.accept(new ExpressionVisitorImpl(this.result.getWhereVal()));
    }

    @Override
    public void visit(Insert insert) {
        List<Column> lst = insert.getColumns();
        if(lst==null){
            lst = new ArrayList<>();
            //TODO
            // insert into tb_a values('v1', trim('v2'), 'v3')
            lst.add(new Column("c1"));
            lst.add(new Column("c2"));
            lst.add(new Column("c3"));
        }

        for (Column col : lst) {
            System.out.println("col=="+col);

            this.result.getDataVal().addResultColVal(
                    new ResultColVal(col.toString(), null));
        }

        ItemsList itemsList = insert.getItemsList();
        if(itemsList!=null) {
            System.out.println("itemsList=" + itemsList);
            itemsList.accept(new ItemsListVisitorImpl(this.result));
        }
    }

    @Override
    public void visit(Replace replace) {

    }

    @Override
    public void visit(Drop drop) {

    }

    @Override
    public void visit(Truncate truncate) {

    }

    @Override
    public void visit(CreateIndex createIndex) {

    }

    @Override
    public void visit(CreateTable createTable) {

    }

    @Override
    public void visit(CreateView createView) {

    }

    @Override
    public void visit(AlterView alterView) {

    }

    @Override
    public void visit(Alter alter) {

    }

    @Override
    public void visit(Statements statements) {

    }

    @Override
    public void visit(Execute execute) {

    }

    @Override
    public void visit(SetStatement setStatement) {

    }

    @Override
    public void visit(Merge merge) {

    }
}
