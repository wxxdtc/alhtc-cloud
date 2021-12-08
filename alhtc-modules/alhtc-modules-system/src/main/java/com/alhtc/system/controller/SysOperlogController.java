package com.alhtc.system.controller;

import com.alhtc.common.core.web.controller.BaseController;
import com.alhtc.common.core.web.domain.AjaxResult;
import com.alhtc.common.core.web.page.TableDataInfo;
import com.alhtc.common.log.annotation.Log;
import com.alhtc.common.log.enums.BusinessType;
import com.alhtc.common.security.annotation.InnerAuth;
import com.alhtc.common.security.annotation.RequiresPermissions;
import com.alhtc.system.api.domain.SysOperLog;
import com.alhtc.system.service.ISysOperLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 操作日志记录
 *
 * @author alhtc
 */
@RestController
@RequestMapping("/operlog")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysOperlogController extends BaseController {

	private final ISysOperLogService operLogService;

	@RequiresPermissions("system:operlog:list")
	@GetMapping("/list")
	public TableDataInfo list(SysOperLog operLog) {
		startPage();
		List<SysOperLog> list = operLogService.selectOperLogList(operLog);
		return getDataTable(list);
	}

	@Log(title = "操作日志", businessType = BusinessType.DELETE)
	@RequiresPermissions("system:operlog:remove")
	@DeleteMapping("/{operIds}")
	public AjaxResult remove(@PathVariable Long[] operIds) {
		return toAjax(operLogService.deleteOperLogByIds(operIds));
	}

	@RequiresPermissions("system:operlog:remove")
	@Log(title = "操作日志", businessType = BusinessType.CLEAN)
	@DeleteMapping("/clean")
	public AjaxResult clean() {
		operLogService.cleanOperLog();
		return AjaxResult.success();
	}

	@InnerAuth
	@PostMapping
	public AjaxResult add(@RequestBody SysOperLog operLog) {
		return toAjax(operLogService.insertOperlog(operLog));
	}
}
