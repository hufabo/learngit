package com.xhw.logcollection.util;

import java.sql.ResultSet;

public interface ProcessHanderEvent {
	public void processRow(ResultSet rs);//处理每一行resultSet的结果行
}
