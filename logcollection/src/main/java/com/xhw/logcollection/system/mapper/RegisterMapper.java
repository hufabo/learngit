package com.xhw.logcollection.system.mapper;

import com.xhw.logcollection.system.entity.Register;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface RegisterMapper extends Mapper<Register> {
	/**
	 * 查询安管系统访问授权信息登记信息列表
	 * @param paraMap
	 * @return
	 */
	List<Register> queryRegister(Map<String, Object> paraMap);
}