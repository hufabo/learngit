package com.xhw.logcollection.analyze.sqlparse;

import com.xhw.logcollection.model.dto.ResultParse;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;

import java.io.StringReader;

/**
 * @author wanghaiyang
 * @version 0.0.1
 * @date 2018/3/7 17:58
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public class SqlParseEnter {
    public static ResultParse sqlParse(String sql){
        CCJSqlParserManager parser = new CCJSqlParserManager();

        //解析结果
        ResultParse resultParse = new ResultParse();

        Statement stmt = null;
        try {
            stmt = parser.parse(new StringReader(sql));
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
        stmt.accept(new StatementVisitorImpl(resultParse));
        return resultParse;
    }
}
