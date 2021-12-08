package com.alhtc.system.controller;

import com.alhtc.common.core.utils.StringUtils;
import com.alhtc.common.core.web.controller.BaseController;
import com.alhtc.common.core.web.domain.AjaxResult;
import com.alhtc.common.core.web.page.TableDataInfo;
import com.alhtc.common.log.annotation.Log;
import com.alhtc.common.log.enums.BusinessType;
import com.alhtc.common.security.annotation.RequiresPermissions;
import com.alhtc.common.security.utils.SecurityUtils;
import com.alhtc.system.api.domain.SysDictData;
import com.alhtc.system.service.ISysDictDataService;
import com.alhtc.system.service.ISysDictTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据字典信息
 *
 * @author alhtc
 */
@RestController
@RequestMapping("/dict/data")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysDictDataController extends BaseController {

	private final ISysDictDataService dictDataService;

	private final ISysDictTypeService dictTypeService;

	@RequiresPermissions("system:dict:list")
	@GetMapping("/list")
	public TableDataInfo list(SysDictData dictData) {
		startPage();
		List<SysDictData> list = dictDataService.selectDictDataList(dictData);
		return getDataTable(list);
	}

	/**
	 * 查询字典数据详细
	 */
	@RequiresPermissions("system:dict:query")
	@GetMapping(value = "/{dictCode}")
	public AjaxResult getInfo(@PathVariable Long dictCode) {
		return AjaxResult.success(dictDataService.selectDictDataById(dictCode));
	}

	/**
	 * 根据字典类型查询字典数据信息
	 */
	@GetMapping(value = "/type/{dictType}")
	public AjaxResult dictType(@PathVariable String dictType) {
		List<SysDictData> data = dictTypeService.selectDictDataByType(dictType);
		if (StringUtils.isNull(data)) {
			data = new ArrayList<SysDictData>();
		}
		return AjaxResult.success(data);
	}

	/**
	 * 新增字典类型
	 */
	@RequiresPermissions("system:dict:add")
	@Log(title = "字典数据", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@Validated @RequestBody SysDictData dict) {
		dict.setCreateBy(SecurityUtils.getUsername());
		return toAjax(dictDataService.insertDictData(dict));
	}

	/**
	 * 修改保存字典类型
	 */
	@RequiresPermissions("system:dict:edit")
	@Log(title = "字典数据", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@Validated @RequestBody SysDictData dict) {
		dict.setUpdateBy(SecurityUtils.getUsername());
		return toAjax(dictDataService.updateDictData(dict));
	}

	/**
	 * 删除字典类型
	 */
	@RequiresPermissions("system:dict:remove")
	@Log(title = "字典类型", businessType = BusinessType.DELETE)
	@DeleteMapping("/{dictCodes}")
	public AjaxResult remove(@PathVariable Long[] dictCodes) {
		dictDataService.deleteDictDataByIds(dictCodes);
		return success();
	}
}
