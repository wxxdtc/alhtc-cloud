package com.alhtc.system.controller;

import com.alhtc.common.core.web.controller.BaseController;
import com.alhtc.common.core.web.domain.AjaxResult;
import com.alhtc.common.core.web.page.TableDataInfo;
import com.alhtc.common.log.annotation.Log;
import com.alhtc.common.log.enums.BusinessType;
import com.alhtc.common.security.annotation.RequiresPermissions;
import com.alhtc.common.security.utils.SecurityUtils;
import com.alhtc.system.domain.VillageInfo;
import com.alhtc.system.service.IVillageInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 公告 信息操作处理
 *
 * @author alhtc
 */
@RestController
@RequestMapping("/info")
public class VillageInfoController extends BaseController
{
    @Autowired
    private IVillageInfoService infoService;

    /**
     * 获取通知公告列表
     */
    @RequiresPermissions("village:info:list")
    @GetMapping("/list")
    public TableDataInfo list(VillageInfo info)
    {
        startPage();
        List<VillageInfo> list = infoService.selectInfoList(info);
        return getDataTable(list);
    }

    /**
     * 根据通知公告编号获取详细信息
     */
    @RequiresPermissions("village:info:query")
    @GetMapping(value = "/{infoId}")
    public AjaxResult getInfo(@PathVariable Long infoId)
    {
        return AjaxResult.success(infoService.selectInfoById(infoId));
    }

    /**
     * 新增通知公告
     */
    @RequiresPermissions("village:info:add")
    @Log(title = "发布公告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody VillageInfo info)
    {
        info.setCreateBy(SecurityUtils.getUsername());
        return toAjax(infoService.insertInfo(info));
    }

    /**
     * 修改通知公告
     */
    @RequiresPermissions("village:info:edit")
    @Log(title = "发布公告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody VillageInfo info)
    {
        info.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(infoService.updateInfo(info));
    }

    /**
     * 删除通知公告
     */
    @RequiresPermissions("village:info:remove")
    @Log(title = "发布公告", businessType = BusinessType.DELETE)
    @DeleteMapping("/{infoIds}")
    public AjaxResult remove(@PathVariable Long[] infoIds)
    {
        return toAjax(infoService.deleteInfoByIds(infoIds));
    }
}
