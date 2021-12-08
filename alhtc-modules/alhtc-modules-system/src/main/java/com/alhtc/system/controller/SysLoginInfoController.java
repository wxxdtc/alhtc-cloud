package com.alhtc.system.controller;

import com.alhtc.common.core.web.controller.BaseController;
import com.alhtc.common.core.web.domain.AjaxResult;
import com.alhtc.common.core.web.page.TableDataInfo;
import com.alhtc.common.log.annotation.Log;
import com.alhtc.common.log.enums.BusinessType;
import com.alhtc.common.security.annotation.InnerAuth;
import com.alhtc.common.security.annotation.RequiresPermissions;
import com.alhtc.system.api.domain.SysLoginInfo;
import com.alhtc.system.service.ISysLoginInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统访问记录
 *
 * @author alhtc
 */
@RestController
@RequestMapping("/loginInfo")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysLoginInfoController extends BaseController {

	private final ISysLoginInfoService loginInfoService;

	@RequiresPermissions("system:loginInfo:list")
	@GetMapping("/list")
	public TableDataInfo list(SysLoginInfo loginInfo) {
		startPage();
		List<SysLoginInfo> list = loginInfoService.selectLoginInfoList(loginInfo);
		return getDataTable(list);
	}

	@RequiresPermissions("system:loginInfo:remove")
	@Log(title = "登录日志", businessType = BusinessType.DELETE)
	@DeleteMapping("/{infoIds}")
	public AjaxResult remove(@PathVariable Long[] infoIds) {
		return toAjax(loginInfoService.deleteLoginInfoByIds(infoIds));
	}

	@RequiresPermissions("system:loginInfo:remove")
	@Log(title = "登录日志", businessType = BusinessType.DELETE)
	@DeleteMapping("/clean")
	public AjaxResult clean() {
		loginInfoService.cleanLoginInfo();
		return AjaxResult.success();
	}

	@InnerAuth
	@PostMapping
	public AjaxResult add(@RequestBody SysLoginInfo loginInfo) {
		return toAjax(loginInfoService.insertLoginInfo(loginInfo));
	}
}
