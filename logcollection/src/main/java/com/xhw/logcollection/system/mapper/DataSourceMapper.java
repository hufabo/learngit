package com.xhw.logcollection.system.mapper;


import com.xhw.logcollection.system.entity.DataSource;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface DataSourceMapper extends Mapper<DataSource> {
	/**
	 * 查询目标数据库连接配置信息列表
	 * @param paraMap
	 * @return
	 */
	List<DataSource> queryDataSource(Map<String, Object> paraMap);

}