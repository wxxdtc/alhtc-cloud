package com.alhtc.system.service.impl;

import com.alhtc.system.api.domain.SysLoginInfo;
import com.alhtc.system.mapper.SysLoginInfoMapper;
import com.alhtc.system.service.ISysLoginInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统访问日志情况信息 服务层处理
 *
 * @author alhtc
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysLoginInfoServiceImpl implements ISysLoginInfoService {

	private final SysLoginInfoMapper loginInfoMapper;

	/**
	 * 新增系统登录日志
	 *
	 * @param loginInfo 访问日志对象
	 */
	@Override
	public int insertLoginInfo(SysLoginInfo loginInfo) {
		return loginInfoMapper.insertLoginInfo(loginInfo);
	}

	/**
	 * 查询系统登录日志集合
	 *
	 * @param loginInfo 访问日志对象
	 * @return 登录记录集合
	 */
	@Override
	public List<SysLoginInfo> selectLoginInfoList(SysLoginInfo loginInfo) {
		return loginInfoMapper.selectLoginInfoList(loginInfo);
	}

	/**
	 * 批量删除系统登录日志
	 *
	 * @param infoIds 需要删除的登录日志ID
	 * @return
	 */
	@Override
	public int deleteLoginInfoByIds(Long[] infoIds) {
		return loginInfoMapper.deleteLoginInfoByIds(infoIds);
	}

	/**
	 * 清空系统登录日志
	 */
	@Override
	public void cleanLoginInfo() {
		loginInfoMapper.cleanLoginInfo();
	}
}
