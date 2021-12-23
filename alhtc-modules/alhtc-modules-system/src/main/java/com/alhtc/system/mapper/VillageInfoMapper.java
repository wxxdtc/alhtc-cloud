package com.alhtc.system.mapper;

import com.alhtc.system.domain.VillageInfo;

import java.util.List;

/**
 * 通知公告表 数据层
 * 
 * @author alhtc
 */
public interface VillageInfoMapper
{
    /**
     * 查询公告信息
     * 
     * @param infoId 公告ID
     * @return 公告信息
     */
    public VillageInfo selectInfoById(Long infoId);

    /**
     * 查询公告列表
     * 
     * @param info 公告信息
     * @return 公告集合
     */
    public List<VillageInfo> selectInfoList(VillageInfo info);

    /**
     * 新增公告
     * 
     * @param info 公告信息
     * @return 结果
     */
    public int insertInfo(VillageInfo info);

    /**
     * 修改公告
     * 
     * @param info 公告信息
     * @return 结果
     */
    public int updateInfo(VillageInfo info);

    /**
     * 批量删除公告
     * 
     * @param infoId 公告ID
     * @return 结果
     */
    public int deleteInfoById(Long infoId);

    /**
     * 批量删除公告信息
     * 
     * @param infoIds 需要删除的公告ID
     * @return 结果
     */
    public int deleteInfoByIds(Long[] infoIds);
}