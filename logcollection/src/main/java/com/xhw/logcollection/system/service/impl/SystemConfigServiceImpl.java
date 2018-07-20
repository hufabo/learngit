package com.xhw.logcollection.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xhw.logcollection.system.entity.Agent;
import com.xhw.logcollection.system.entity.DataSource;
import com.xhw.logcollection.system.entity.Register;
import com.xhw.logcollection.system.entity.ShareProtocol;
import com.xhw.logcollection.system.mapper.AgentMapper;
import com.xhw.logcollection.system.mapper.DataSourceMapper;
import com.xhw.logcollection.system.mapper.RegisterMapper;
import com.xhw.logcollection.system.mapper.ShareProtocolMapper;
import com.xhw.logcollection.system.service.SystemConfigService;
import com.xhw.logcollection.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author rentie
 * @version 0.0.1
 * @date 2018/2/5
 * @note
 * @update 修改日期      修改人    修改内容
 * ----------------------------------------------
 */
@Service
public class SystemConfigServiceImpl implements SystemConfigService {
	@Autowired
	private AgentMapper agentMapper;

	@Autowired
	private DataSourceMapper dataSourceMapper;

	@Autowired
	private RegisterMapper registerMapper;

	@Autowired
	private ShareProtocolMapper shareProtocolMapper;

	/**
	 * 添加Agent方式采集配置信息
	 *
	 * @param bean
	 * @return
	 */
	@Override
	public Integer saveAgent(Agent bean) {
		bean.setDlid(UUIDGenerator.getUUID());
		bean.setJlzt("1");
		return agentMapper.insertSelective(bean);
	}

	/**
	 * 添加目标数据库连接配置
	 *
	 * @param bean
	 * @return
	 */
	@Override
	public Integer saveDataSource(DataSource bean) {
		bean.setSjyid(UUIDGenerator.getUUID());
		bean.setJlzt("1");
		return dataSourceMapper.insert(bean);
	}

	/**
	 * 添加安管系统访问授权信息登记信息
	 *
	 * @param bean
	 * @return
	 */
	@Override
	public Integer saveRegister(Register bean) {
		bean.setJlid(UUIDGenerator.getUUID());
		bean.setJlzt("1");
		return registerMapper.insert(bean);
	}

	/**
	 * 添加SMB/NFS共享方式采集配置信息
	 *
	 * @param bean
	 * @return
	 */
	@Override
	public Integer saveShareProtocol(ShareProtocol bean) {
		bean.setMlgxid(UUIDGenerator.getUUID());
		bean.setJlzt("1");
		return shareProtocolMapper.insertSelective(bean);
	}

	/**
	 * 更新Agent方式采集配置信息
	 *
	 * @param bean
	 * @return
	 */
	@Override
	public Integer updateAgent(Agent bean) {
		return agentMapper.updateByPrimaryKeySelective(bean);
	}

	/**
	 * 更新目标数据库连接配置
	 *
	 * @param bean
	 * @return
	 */
	@Override
	public Integer updateDataSource(DataSource bean) {
		return dataSourceMapper.updateByPrimaryKeySelective(bean);
	}

	/**
	 * 更新安管系统访问授权信息登记信息
	 *
	 * @param bean
	 * @return
	 */
	@Override
	public Integer updateRegister(Register bean) {
		return registerMapper.updateByPrimaryKeySelective(bean);
	}

	/**
	 * 更新SMB/NFS共享方式采集配置信息
	 *
	 * @param bean
	 * @return
	 */
	@Override
	public Integer updateShareProtocol(ShareProtocol bean) {
		return shareProtocolMapper.updateByPrimaryKeySelective(bean);
	}

	/**
	 * 查询单个Agent方式采集配置信息
	 *
	 * @param bean
	 * @return
	 */
	@Override
	public Agent getAgent(Agent bean) {
		return agentMapper.selectOne(bean);
	}

	/**
	 * 查询单个目标数据库连接配置信息
	 *
	 * @param bean
	 * @return
	 */
	@Override
	public DataSource getDataSource(DataSource bean) {
		return dataSourceMapper.selectOne(bean);
	}

	/**
	 * 查询单个安管系统访问授权信息登记信息
	 *
	 * @param bean
	 * @return
	 */
	@Override
	public Register getRegister(Register bean) {
		return registerMapper.selectOne(bean);
	}

	/**
	 * 查询单个SMB/NFS共享方式采集配置信息
	 *
	 * @param bean
	 * @return
	 */
	@Override
	public ShareProtocol getShareProtocol(ShareProtocol bean) {
		return shareProtocolMapper.selectOne(bean);
	}

	/**
	 * 查询Agent方式采集配置信息列表
	 *
	 * @param paraMap
	 * @return
	 */
	@Override
	public PageInfo<Agent> queryAgent(Map<String, Object> paraMap) {
		if(paraMap.get("curentPage")!=null && !paraMap.get("curentPage").toString().equals("")){
			PageHelper.startPage(Integer.valueOf(paraMap.get("curentPage").toString()),Integer.valueOf(paraMap.get("pageSize").toString()));
		}
		List<Agent> list = agentMapper.queryAgent(paraMap);
		PageInfo<Agent> pageInfo = new PageInfo<Agent>(list);
		return pageInfo;
	}

	/**
	 * 查询目标数据库连接配置信息列表
	 *
	 * @param paraMap
	 * @return
	 */
	@Override
	public PageInfo<DataSource> queryDataSource(Map<String, Object> paraMap) {
		if(paraMap.get("curentPage")!=null && !paraMap.get("curentPage").toString().equals("")){
			PageHelper.startPage(Integer.valueOf(paraMap.get("curentPage").toString()),Integer.valueOf(paraMap.get("pageSize").toString()));
		}
		List<DataSource> list = dataSourceMapper.queryDataSource(paraMap);
		PageInfo<DataSource> pageInfo = new PageInfo<DataSource>(list);
		return pageInfo;
	}

	/**
	 * 查询安管系统访问授权信息登记信息列表
	 *
	 * @param paraMap
	 * @return
	 */
	@Override
	public PageInfo<Register> queryRegister(Map<String, Object> paraMap) {
		if(paraMap.get("curentPage")!=null && !paraMap.get("curentPage").toString().equals("")){
			PageHelper.startPage(Integer.valueOf(paraMap.get("curentPage").toString()),Integer.valueOf(paraMap.get("pageSize").toString()));
		}
		List<Register> list = registerMapper.queryRegister(paraMap);
		PageInfo<Register> pageInfo = new PageInfo<Register>(list);
		return pageInfo;
	}

	/**
	 * 查询SMB/NFS共享方式采集配置信息列表
	 *
	 * @param paraMap
	 * @return
	 */
	@Override
	public PageInfo<ShareProtocol> queryShareProtocol(Map<String, Object> paraMap) {
		if(paraMap.get("curentPage")!=null && !paraMap.get("curentPage").toString().equals("")){
			PageHelper.startPage(Integer.valueOf(paraMap.get("curentPage").toString()),Integer.valueOf(paraMap.get("pageSize").toString()));
		}
		List<ShareProtocol> list = shareProtocolMapper.queryShareProtocol(paraMap);
		PageInfo<ShareProtocol> pageInfo = new PageInfo<ShareProtocol>(list);
		return pageInfo;
	}
}
