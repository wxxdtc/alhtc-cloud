package com.alhtc.system.service;

import com.alhtc.system.domain.VillageInfo;

import java.util.List;

/**
 * 公告 服务层
 *
 * @author alhtc
 */
public interface IVillageInfoService {
	/**
	 * 查询公告信息
	 *
	 * @param infoId 公告ID
	 * @return 公告信息
	 */
	VillageInfo selectInfoById(Long infoId);

	/**
	 * 查询公告列表
	 *
	 * @param info 公告信息
	 * @return 公告集合
	 */
	List<VillageInfo> selectInfoList(VillageInfo info);

	/**
	 * 新增公告
	 *
	 * @param info 公告信息
	 * @return 结果
	 */
	int insertInfo(VillageInfo info);

	/**
	 * 修改公告
	 *
	 * @param info 公告信息
	 * @return 结果
	 */
	int updateInfo(VillageInfo info);

	/**
	 * 删除公告信息
	 *
	 * @param infoId 公告ID
	 * @return 结果
	 */
	int deleteInfoById(Long infoId);

	/**
	 * 批量删除公告信息
	 *
	 * @param infoIds 需要删除的公告ID
	 * @return 结果
	 */
	int deleteInfoByIds(Long[] infoIds);
}
