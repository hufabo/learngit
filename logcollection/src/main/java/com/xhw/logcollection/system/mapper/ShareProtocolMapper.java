package com.xhw.logcollection.system.mapper;


import com.xhw.logcollection.system.entity.ShareProtocol;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface ShareProtocolMapper extends Mapper<ShareProtocol> {
	/**
	 * 查询SMB/NFS共享方式采集配置信息列表
	 * @param paraMap
	 * @return
	 */
	List<ShareProtocol> queryShareProtocol(Map<String, Object> paraMap);
}