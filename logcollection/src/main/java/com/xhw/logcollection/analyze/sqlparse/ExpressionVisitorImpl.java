package com.xhw.logcollection.analyze.sqlparse;

import com.xhw.logcollection.model.dto.ResultColVal;
import com.xhw.logcollection.model.dto.ResultListColVal;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.arithmetic.*;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.SubSelect;

/**
 * @author wanghaiyang
 * @version 0.0.1
 * @date 2018/3/5 10:00
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public class ExpressionVisitorImpl implements ExpressionVisitor {
    private ResultListColVal result ;
    public ExpressionVisitorImpl(ResultListColVal result) {
        this.result = result;
    }
    @Override
    public void visit(NullValue nullValue) {
        System.out.println("nullValue="+nullValue);
    }

    @Override
    public void visit(Function function) {
        System.out.println("function="+function);
    }

    @Override
    public void visit(SignedExpression signedExpression) {
        System.out.println("signedExpression="+signedExpression);
    }

    @Override
    public void visit(JdbcParameter jdbcParameter) {
        System.out.println("jdbcParameter="+jdbcParameter);
    }

    @Override
    public void visit(JdbcNamedParameter jdbcNamedParameter) {
        System.out.println("jdbcNamedParameter="+jdbcNamedParameter);
    }

    @Override
    public void visit(DoubleValue doubleValue) {
        System.out.println("doubleValue="+doubleValue);
    }

    @Override
    public void visit(LongValue longValue) {
        System.out.println("longValue="+longValue);
    }

    @Override
    public void visit(HexValue hexValue) {
        System.out.println("hexValue="+hexValue);
    }

    @Override
    public void visit(DateValue dateValue) {
        System.out.println("dateValue="+dateValue);
    }

    @Override
    public void visit(TimeValue timeValue) {
        System.out.println("timeValue="+timeValue);
    }

    @Override
    public void visit(TimestampValue timestampValue) {
        System.out.println("timestampValue="+timestampValue);
    }

    @Override
    public void visit(Parenthesis parenthesis) {
        System.out.println("parenthesis="+parenthesis);
        System.out.println("parenthesis2=="+parenthesis.getExpression());

        parenthesis.getExpression().accept(this);
    }

    @Override
    public void visit(StringValue stringValue) {
        System.out.println("stringValue="+stringValue);
    }

    @Override
    public void visit(Addition addition) {
        System.out.println("addition="+addition);
    }

    @Override
    public void visit(Division division) {
        System.out.println("division="+division);
    }

    @Override
    public void visit(Multiplication multiplication) {
        System.out.println("multiplication="+multiplication);
    }

    @Override
    public void visit(Subtraction subtraction) {
        System.out.println("subtraction="+subtraction);
    }

    @Override
    public void visit(AndExpression andExpression) {
        System.out.println("and="+andExpression);

        Expression leftExpr = andExpression.getLeftExpression();
        leftExpr.accept(this);

        Expression rightExpr = andExpression.getRightExpression();
        rightExpr.accept(this);
    }

    @Override
    public void visit(OrExpression orExpression) {
        System.out.println("or="+orExpression);

        Expression leftExpr = orExpression.getLeftExpression();
        leftExpr.accept(this);

        Expression rightExpr = orExpression.getRightExpression();
        rightExpr.accept(this);
    }

    @Override
    public void visit(Between between) {
        System.out.println("between="+between);
        this.result.addResultColVal(new ResultColVal(between.getLeftExpression().toString(), between.getBetweenExpressionStart()+","+between.getBetweenExpressionEnd()));
    }

    @Override
    public void visit(EqualsTo equalsTo) {
        System.out.println("equalsTo="+equalsTo);
        Expression leftExpr = equalsTo.getLeftExpression();
        Expression rightExpr = equalsTo.getRightExpression();

        this.result.addResultColVal(new ResultColVal(leftExpr.toString(), rightExpr.toString()));
    }

    @Override
    public void visit(GreaterThan greaterThan) {
        System.out.println("greaterThan="+greaterThan);
        this.result.addResultColVal(new ResultColVal(greaterThan.getLeftExpression().toString(), greaterThan.getRightExpression().toString()));
    }

    @Override
    public void visit(GreaterThanEquals greaterThanEquals) {
        System.out.println("greaterThanEquals="+greaterThanEquals);
        this.result.addResultColVal(new ResultColVal(greaterThanEquals.getLeftExpression().toString(), greaterThanEquals.getRightExpression().toString()));
    }

    @Override
    public void visit(InExpression inExpression) {
        System.out.println("inExpression="+inExpression);
        this.result.addResultColVal(new ResultColVal(inExpression.getLeftExpression().toString(), inExpression.getRightItemsList().toString()));
    }

    @Override
    public void visit(IsNullExpression isNullExpression) {
        System.out.println("isNullExpression="+isNullExpression);
        this.result.addResultColVal(new ResultColVal(isNullExpression.getLeftExpression().toString(), isNullExpression.isNot()?"not null":"null"));
    }

    @Override
    public void visit(LikeExpression likeExpression) {
        System.out.println("likeExpression="+likeExpression);
        this.result.addResultColVal(new ResultColVal(likeExpression.getLeftExpression().toString(), likeExpression.getRightExpression().toString()));
    }

    @Override
    public void visit(MinorThan minorThan) {
        System.out.println("minorThan="+minorThan);
        this.result.addResultColVal(new ResultColVal(minorThan.getLeftExpression().toString(), minorThan.getRightExpression().toString()));
    }

    @Override
    public void visit(MinorThanEquals minorThanEquals) {
        System.out.println("minorThanEquals="+minorThanEquals);
        this.result.addResultColVal(new ResultColVal(minorThanEquals.getLeftExpression().toString(), minorThanEquals.getRightExpression().toString()));
    }

    @Override
    public void visit(NotEqualsTo notEqualsTo) {
        System.out.println("notEqualsTo="+notEqualsTo);
        System.out.println("notEqualsTo="+notEqualsTo.isNot());
        this.result.addResultColVal(new ResultColVal(notEqualsTo.getLeftExpression().toString(), notEqualsTo.getRightExpression().toString()));
    }

    @Override
    public void visit(Column column) {
        System.out.println("column="+column);
    }

    @Override
    public void visit(SubSelect subSelect) {
        System.out.println("subSelect="+subSelect);
    }

    @Override
    public void visit(CaseExpression caseExpression) {
        System.out.println("caseExpression="+caseExpression);
    }

    @Override
    public void visit(WhenClause whenClause) {
        System.out.println("whenClause="+whenClause);
    }

    @Override
    public void visit(ExistsExpression existsExpression) {
        System.out.println("existsExpression="+existsExpression);
    }

    @Override
    public void visit(AllComparisonExpression allComparisonExpression) {
        System.out.println("allComparisonExpression="+allComparisonExpression);
    }

    @Override
    public void visit(AnyComparisonExpression anyComparisonExpression) {
        System.out.println("anyComparisonExpression="+anyComparisonExpression);
    }

    @Override
    public void visit(Concat concat) {
        System.out.println("concat="+concat);
    }

    @Override
    public void visit(Matches matches) {
        System.out.println("matches="+matches);
    }

    @Override
    public void visit(BitwiseAnd bitwiseAnd) {
        System.out.println("bitwiseAnd="+bitwiseAnd);
    }

    @Override
    public void visit(BitwiseOr bitwiseOr) {
        System.out.println("bitwiseOr="+bitwiseOr);
    }

    @Override
    public void visit(BitwiseXor bitwiseXor) {
        System.out.println("bitwiseXor="+bitwiseXor);
    }

    @Override
    public void visit(CastExpression castExpression) {
        System.out.println("castExpression="+castExpression);
    }

    @Override
    public void visit(Modulo modulo) {
        System.out.println("modulo="+modulo);
    }

    @Override
    public void visit(AnalyticExpression analyticExpression) {
        System.out.println("analyticExpression="+analyticExpression);
    }

    @Override
    public void visit(WithinGroupExpression withinGroupExpression) {
        System.out.println("withinGroupExpression="+withinGroupExpression);
    }

    @Override
    public void visit(ExtractExpression extractExpression) {
        System.out.println("extractExpression="+extractExpression);
    }

    @Override
    public void visit(IntervalExpression intervalExpression) {
        System.out.println("intervalExpression="+intervalExpression);
    }

    @Override
    public void visit(OracleHierarchicalExpression oracleHierarchicalExpression) {
        System.out.println("oracleHierarchicalExpression="+oracleHierarchicalExpression);
    }

    @Override
    public void visit(RegExpMatchOperator regExpMatchOperator) {
        System.out.println("regExpMatchOperator="+regExpMatchOperator);
    }

    @Override
    public void visit(JsonExpression jsonExpression) {
        System.out.println("jsonExpression="+jsonExpression);
    }

    @Override
    public void visit(JsonOperator jsonOperator) {
        System.out.println("jsonOperator="+jsonOperator);
    }

    @Override
    public void visit(RegExpMySQLOperator regExpMySQLOperator) {
        System.out.println("regExpMySQLOperator="+regExpMySQLOperator);
    }

    @Override
    public void visit(UserVariable userVariable) {
        System.out.println("userVariable="+userVariable);
    }

    @Override
    public void visit(NumericBind numericBind) {
        System.out.println("numericBind="+numericBind);
    }

    @Override
    public void visit(KeepExpression keepExpression) {
        System.out.println("keepExpression="+keepExpression);
    }

    @Override
    public void visit(MySQLGroupConcat mySQLGroupConcat) {
        System.out.println("mySQLGroupConcat="+mySQLGroupConcat);
    }

    @Override
    public void visit(RowConstructor rowConstructor) {
        System.out.println("rowConstructor="+rowConstructor);
    }

    @Override
    public void visit(OracleHint oracleHint) {
        System.out.println("oracleHint="+oracleHint);
    }

    @Override
    public void visit(TimeKeyExpression timeKeyExpression) {
        System.out.println("timeKeyExpression="+timeKeyExpression);
    }

    @Override
    public void visit(DateTimeLiteralExpression dateTimeLiteralExpression) {
        System.out.println("dateTimeLiteralExpression="+dateTimeLiteralExpression);
    }

    @Override
    public void visit(NotExpression notExpression) {
        System.out.println("notExpression="+notExpression);
    }
}
