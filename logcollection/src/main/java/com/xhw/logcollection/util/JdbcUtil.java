package com.xhw.logcollection.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 连接数据库工具
 * @Author 孔纲
 * @Date 2018/3/12
 */
public class JdbcUtil {

    public static Connection getConnection(String drivername, String url, String user, String password) throws Exception {
        Class.forName(drivername);
        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }

    public static void closeResource(ResultSet rs, PreparedStatement pstm, Connection con){
        if(rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // 关闭执行通道
        if(pstm !=null) {
            try {
                pstm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // 关闭连接通道
        try {
            if(con != null &&(!con.isClosed())) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据ResultSet获得结果集MAP
     * @author konggang
     * @date 2018/3/13 14:39
     */
    public static Map<String, String> getResultMap(ResultSet rs) throws SQLException, IOException {
        Map<String, String> data = new HashMap<>();
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            String name = metaData.getColumnName(i);
            int type = metaData .getColumnType(i);
            String value = null;
            if(Types.VARCHAR == type || Types.CHAR == type){
                value = rs.getString(i);
            }else if(Types.BLOB == type){
                Blob blob = rs.getBlob(i);
                if(blob != null){
                    value = new String(blob.getBytes((long)1, (int)blob.length()));
                    // base64编码
                    value = Base64Util.encode(value);
                }
            }else if(Types.CLOB == type){
                Clob clob = rs.getClob(i);
                if(clob != null){
                    value = clob2String(clob);
                }
            }else if(Types.DATE == type){
                Date date = rs.getDate(i);
                if(date != null){
                    value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                }
            }else if(Types.TIMESTAMP == type){
                Timestamp timestamp = rs.getTimestamp(i);
                if(timestamp != null){
                    value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
                }
            }else if(Types.BOOLEAN == type){
                boolean aBoolean = rs.getBoolean(i);
                value = String.valueOf(aBoolean);
            }else if(Types.DECIMAL == type){
                BigDecimal bigDecimal = rs.getBigDecimal(i);
                value = String.valueOf(bigDecimal);
            }else {
                Object object = rs.getObject(i);
                value = String.valueOf(object);
            }
            // 字段名统一小写
            data.put(name.toLowerCase(), value);
        }
        return data;
    }

    private static String clob2String(Clob clob) throws SQLException, IOException {
        Reader is = clob.getCharacterStream();
        BufferedReader br = new BufferedReader(is);
        String s = br.readLine();
        StringBuffer sb = new StringBuffer();
        while (s != null) {
            sb.append(s);
            s = br.readLine();
        }
        if(br!=null){
            br.close();
        }
        if(is!=null){
            is.close();
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String drivername = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@180.168.178.174:52185:orcl";
        String user = "system";
        String password = "Rzcj6666";
        Connection connection = null;

        url = "jdbc:oracle:thin:@(DESCRIPTION =(ADDRESS = (PROTOCOL = TCP)(HOST = 180.168.178.174)(PORT = 52185))(CONNECT_DATA = (SERVER = DEDICATED)(SERVICE_NAME = ORCL)))";
        //url = "jdbc:oracle:thin:@(description=(address_list= (address=(host=rac1) (protocol=tcp)(port=1521))(address=(host=rac2)(protocol=tcp) (port=1521)) (load_balance=yes)(failover=yes))(connect_data=(service_name= orcl)))";
        try {
            connection = JdbcUtil.getConnection(drivername, url, user, password);
            boolean valid = connection.isValid(1000);
            System.out.println(valid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
