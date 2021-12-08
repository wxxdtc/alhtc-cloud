package com.alhtc.system.controller;

import com.alhtc.common.core.constant.UserConstants;
import com.alhtc.common.core.web.controller.BaseController;
import com.alhtc.common.core.web.domain.AjaxResult;
import com.alhtc.common.core.web.page.TableDataInfo;
import com.alhtc.common.log.annotation.Log;
import com.alhtc.common.log.enums.BusinessType;
import com.alhtc.common.security.annotation.RequiresPermissions;
import com.alhtc.common.security.utils.SecurityUtils;
import com.alhtc.system.domain.SysConfig;
import com.alhtc.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 参数配置 信息操作处理
 *
 * @author alhtc
 */
@RestController
@RequestMapping("/config")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysConfigController extends BaseController {

	private final ISysConfigService configService;

	/**
	 * 获取参数配置列表
	 */
	@RequiresPermissions("system:config:list")
	@GetMapping("/list")
	public TableDataInfo list(SysConfig config) {
		startPage();
		List<SysConfig> list = configService.selectConfigList(config);
		return getDataTable(list);
	}

	/**
	 * 根据参数编号获取详细信息
	 */
	@GetMapping(value = "/{configId}")
	public AjaxResult getInfo(@PathVariable Long configId) {
		return AjaxResult.success(configService.selectConfigById(configId));
	}

	/**
	 * 根据参数键名查询参数值
	 */
	@GetMapping(value = "/configKey/{configKey}")
	public AjaxResult getConfigKey(@PathVariable String configKey) {
		return AjaxResult.success(configService.selectConfigByKey(configKey));
	}

	/**
	 * 新增参数配置
	 */
	@RequiresPermissions("system:config:add")
	@Log(title = "参数管理", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@Validated @RequestBody SysConfig config) {
		if (UserConstants.NOT_UNIQUE.equals(configService.checkConfigKeyUnique(config))) {
			return AjaxResult.error("新增参数'" + config.getConfigName() + "'失败，参数键名已存在");
		}
		config.setCreateBy(SecurityUtils.getUsername());
		return toAjax(configService.insertConfig(config));
	}

	/**
	 * 修改参数配置
	 */
	@RequiresPermissions("system:config:edit")
	@Log(title = "参数管理", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@Validated @RequestBody SysConfig config) {
		if (UserConstants.NOT_UNIQUE.equals(configService.checkConfigKeyUnique(config))) {
			return AjaxResult.error("修改参数'" + config.getConfigName() + "'失败，参数键名已存在");
		}
		config.setUpdateBy(SecurityUtils.getUsername());
		return toAjax(configService.updateConfig(config));
	}

	/**
	 * 删除参数配置
	 */
	@RequiresPermissions("system:config:remove")
	@Log(title = "参数管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{configIds}")
	public AjaxResult remove(@PathVariable Long[] configIds) {
		configService.deleteConfigByIds(configIds);
		return success();
	}

	/**
	 * 刷新参数缓存
	 */
	@RequiresPermissions("system:config:remove")
	@Log(title = "参数管理", businessType = BusinessType.CLEAN)
	@DeleteMapping("/refreshCache")
	public AjaxResult refreshCache() {
		configService.resetConfigCache();
		return AjaxResult.success();
	}
}
