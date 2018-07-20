package com.xhw.logcollection.util;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;

/**
 * jdbc连接数据库工具类
 */
@Deprecated
public class BaseDao {
	private static Property property = Property.getInstance();
	
	protected Connection con;
	protected PreparedStatement pstmt;
	protected ResultSet rs;
	
	//开闭原则：对修改关闭，对扩展开发
	static{
		try {
			Class.forName(property.driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection(){
		try {
			this.con = DriverManager.getConnection(property.url, property.user, property.pwd);
			return this.con;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void closeDb(Connection con,PreparedStatement pstmt,ResultSet rs){
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	public int executeUpdate(String sql,Object...params){
		try {
			this.pstmt = this.getConnection().prepareStatement(sql);
			if (null != params) {
				for (int i = 0; i < params.length; i++) {
					Object object = params[i];
					this.pstmt.setObject(i + 1, object);
				}
			}
			return this.pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			this.closeDb(this.con, this.pstmt, null);
		}
		return 0;
	}
	
	public <T>List<T> find(String sql,Object[] params,Class<T> clazz){
		List<T> list = new ArrayList<>();
		try {
			this.pstmt = this.getConnection().prepareStatement(sql);
			ParameterMetaData pmd = this.pstmt.getParameterMetaData();
			for(int i = 0;i < pmd.getParameterCount();i++){
				this.pstmt.setObject(i+1, params[i]);
			}
			
			Field[] f = clazz.getDeclaredFields();//通过反射获取对象中所有的属性
            Map<String, String> map = new HashMap<String, String>();
            for (int i = 0; i < f.length; i++) {
				Field field = f[i];
				map.put(field.getName().toUpperCase(), field.getName());
			}
	            
	            
			this.rs = this.pstmt.executeQuery();
			ResultSetMetaData rsm = this.rs.getMetaData();
			while (this.rs.next()) {
				T obj = clazz.newInstance();
				for (int i = 0;i < rsm.getColumnCount();i++) {
					String columnName = rsm.getColumnName(i + 1);
					Object value = this.rs.getObject(columnName);//  表的列,rownum r
					if (map.containsKey(columnName)) {
						BeanUtils.setProperty(obj, map.get(columnName), value);
					}
				}
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		} finally{
			this.closeDb(this.con, this.pstmt, this.rs);
		}
		return list;
	}

	public List<Object> find(String sql,Object[] params){
		List<Object> list = new ArrayList<>();
		try {
			this.pstmt = this.getConnection().prepareStatement(sql);
			ParameterMetaData pmd = this.pstmt.getParameterMetaData();
			for(int i = 0;i < pmd.getParameterCount();i++){
				this.pstmt.setObject(i+1, params[i]);
			}

			this.rs = this.pstmt.executeQuery();
			ResultSetMetaData rsm = this.rs.getMetaData();
			while (this.rs.next()) {
				JSONObject obj = new JSONObject();
				for (int i = 0;i < rsm.getColumnCount();i++) {
					String columnName = rsm.getColumnName(i + 1);
					Object value = this.rs.getObject(columnName);//  表的列,rownum r
					obj.put(columnName,value);
				}
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		} finally{
			this.closeDb(this.con, this.pstmt, this.rs);
		}
		return list;
	}
	
	public void commonQuery(String sql,Object[] params,ProcessHanderEvent event){
		try {
			this.pstmt = this.getConnection().prepareStatement(sql);
			if (null != params) {
				for (int i = 0; i < params.length; i++) {
					Object object = params[i];
					this.pstmt.setObject(i + 1, object);
				}
			}
			this.rs = this.pstmt.executeQuery();
			while (this.rs.next()) {
				event.processRow(this.rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			this.closeDb(con, pstmt, rs);
		}
	}

}
