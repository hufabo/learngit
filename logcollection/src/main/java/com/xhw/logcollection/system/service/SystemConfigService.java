package com.xhw.logcollection.system.service;

import com.github.pagehelper.PageInfo;
import com.xhw.logcollection.system.entity.Agent;
import com.xhw.logcollection.system.entity.DataSource;
import com.xhw.logcollection.system.entity.Register;
import com.xhw.logcollection.system.entity.ShareProtocol;

import java.util.List;
import java.util.Map;

/**
 * 系统配置管理Service
 * @author rentie
 * @version 0.0.1
 * @date 2018/2/5
 * @note
 * @update 修改日期      修改人    修改内容
 * ----------------------------------------------
 */
public interface SystemConfigService {
	/**
	 * 添加Agent方式采集配置信息
	 * @param bean
	 * @return
	 */
	Integer saveAgent(Agent bean);

	/**
	 * 添加目标数据库连接配置
	 * @param bean
	 * @return
	 */
	Integer saveDataSource(DataSource bean);

	/**
	 *添加安管系统访问授权信息登记信息
	 * @param bean
	 * @return
	 */
	Integer saveRegister(Register bean);

	/**
	 * 添加SMB/NFS共享方式采集配置信息
	 * @param bean
	 * @return
	 */
	Integer saveShareProtocol(ShareProtocol bean);

	/**
	 * 更新Agent方式采集配置信息
	 * @param bean
	 * @return
	 */
	Integer updateAgent(Agent bean);

	/**
	 * 更新目标数据库连接配置
	 * @param bean
	 * @return
	 */
	Integer updateDataSource(DataSource bean);

	/**
	 *更新安管系统访问授权信息登记信息
	 * @param bean
	 * @return
	 */
	Integer updateRegister(Register bean);

	/**
	 * 更新SMB/NFS共享方式采集配置信息
	 * @param bean
	 * @return
	 */
	Integer updateShareProtocol(ShareProtocol bean);

	/**
	 * 查询单个Agent方式采集配置信息
	 * @param bean
	 * @return
	 */
	Agent getAgent(Agent bean);

	/**
	 * 查询单个目标数据库连接配置信息
	 * @param bean
	 * @return
	 */
	DataSource getDataSource(DataSource bean);

	/**
	 * 查询单个安管系统访问授权信息登记信息
	 * @param bean
	 * @return
	 */
	Register getRegister(Register bean);

	/**
	 * 查询单个SMB/NFS共享方式采集配置信息
	 * @param bean
	 * @return
	 */
	ShareProtocol getShareProtocol(ShareProtocol bean);

	/**
	 * 查询Agent方式采集配置信息列表
	 * @param paraMap
	 * @return
	 */
	PageInfo<Agent> queryAgent(Map<String, Object> paraMap);

	/**
	 * 查询目标数据库连接配置信息列表
	 * @param paraMap
	 * @return
	 */
	PageInfo<DataSource> queryDataSource(Map<String, Object> paraMap);

	/**
	 * 查询安管系统访问授权信息登记信息列表
	 * @param paraMap
	 * @return
	 */
	PageInfo<Register> queryRegister(Map<String, Object> paraMap);

	/**
	 * 查询SMB/NFS共享方式采集配置信息列表
	 * @param paraMap
	 * @return
	 */
	PageInfo<ShareProtocol> queryShareProtocol(Map<String, Object> paraMap);

}
