package com.xhw.logcollection.system.mapper;

import com.xhw.logcollection.system.entity.Agent;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface AgentMapper extends Mapper<Agent> {
	/**
	 * 查询Agent方式采集配置信息列表
	 * @param paraMap
	 * @return
	 */
	List<Agent> queryAgent(Map<String, Object> paraMap);
}