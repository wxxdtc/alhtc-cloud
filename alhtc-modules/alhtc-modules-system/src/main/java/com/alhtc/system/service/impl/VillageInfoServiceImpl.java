package com.alhtc.system.service.impl;

import com.alhtc.system.domain.VillageInfo;
import com.alhtc.system.mapper.VillageInfoMapper;
import com.alhtc.system.service.IVillageInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 公告 服务层实现
 *
 * @author alhtc
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VillageInfoServiceImpl implements IVillageInfoService {

	private final VillageInfoMapper infoMapper;

	/**
	 * 查询公告信息
	 *
	 * @param infoId 公告ID
	 * @return 公告信息
	 */
	@Override
	public VillageInfo selectInfoById(Long infoId) {
		return infoMapper.selectInfoById(infoId);
	}

	/**
	 * 查询公告列表
	 *
	 * @param info 公告信息
	 * @return 公告集合
	 */
	@Override
	public List<VillageInfo> selectInfoList(VillageInfo info) {
		return infoMapper.selectInfoList(info);
	}

	/**
	 * 新增公告
	 *
	 * @param info 公告信息
	 * @return 结果
	 */
	@Override
	public int insertInfo(VillageInfo info) {
		return infoMapper.insertInfo(info);
	}

	/**
	 * 修改公告
	 *
	 * @param info 公告信息
	 * @return 结果
	 */
	@Override
	public int updateInfo(VillageInfo info) {
		return infoMapper.updateInfo(info);
	}

	/**
	 * 删除公告对象
	 *
	 * @param infoId 公告ID
	 * @return 结果
	 */
	@Override
	public int deleteInfoById(Long infoId) {
		return infoMapper.deleteInfoById(infoId);
	}

	/**
	 * 批量删除公告信息
	 *
	 * @param infoIds 需要删除的公告ID
	 * @return 结果
	 */
	@Override
	public int deleteInfoByIds(Long[] infoIds) {
		return infoMapper.deleteInfoByIds(infoIds);
	}
}
