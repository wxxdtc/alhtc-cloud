package com.alhtc.system.controller;

import com.alhtc.common.core.constant.UserConstants;
import com.alhtc.common.core.web.controller.BaseController;
import com.alhtc.common.core.web.domain.AjaxResult;
import com.alhtc.common.core.web.page.TableDataInfo;
import com.alhtc.common.log.annotation.Log;
import com.alhtc.common.log.enums.BusinessType;
import com.alhtc.common.security.annotation.RequiresPermissions;
import com.alhtc.common.security.utils.SecurityUtils;
import com.alhtc.system.domain.SysPost;
import com.alhtc.system.service.ISysPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 岗位信息操作处理
 *
 * @author alhtc
 */
@RestController
@RequestMapping("/post")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysPostController extends BaseController {

	private final ISysPostService postService;

	/**
	 * 获取岗位列表
	 */
	@RequiresPermissions("system:post:list")
	@GetMapping("/list")
	public TableDataInfo list(SysPost post) {
		startPage();
		List<SysPost> list = postService.selectPostList(post);
		return getDataTable(list);
	}

	/**
	 * 根据岗位编号获取详细信息
	 */
	@RequiresPermissions("system:post:query")
	@GetMapping(value = "/{postId}")
	public AjaxResult getInfo(@PathVariable Long postId) {
		return AjaxResult.success(postService.selectPostById(postId));
	}

	/**
	 * 新增岗位
	 */
	@RequiresPermissions("system:post:add")
	@Log(title = "岗位管理", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@Validated @RequestBody SysPost post) {
		if (UserConstants.NOT_UNIQUE.equals(postService.checkPostNameUnique(post))) {
			return AjaxResult.error("新增岗位'" + post.getPostName() + "'失败，岗位名称已存在");
		} else if (UserConstants.NOT_UNIQUE.equals(postService.checkPostCodeUnique(post))) {
			return AjaxResult.error("新增岗位'" + post.getPostName() + "'失败，岗位编码已存在");
		}
		post.setCreateBy(SecurityUtils.getUsername());
		return toAjax(postService.insertPost(post));
	}

	/**
	 * 修改岗位
	 */
	@RequiresPermissions("system:post:edit")
	@Log(title = "岗位管理", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@Validated @RequestBody SysPost post) {
		if (UserConstants.NOT_UNIQUE.equals(postService.checkPostNameUnique(post))) {
			return AjaxResult.error("修改岗位'" + post.getPostName() + "'失败，岗位名称已存在");
		} else if (UserConstants.NOT_UNIQUE.equals(postService.checkPostCodeUnique(post))) {
			return AjaxResult.error("修改岗位'" + post.getPostName() + "'失败，岗位编码已存在");
		}
		post.setUpdateBy(SecurityUtils.getUsername());
		return toAjax(postService.updatePost(post));
	}

	/**
	 * 删除岗位
	 */
	@RequiresPermissions("system:post:remove")
	@Log(title = "岗位管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{postIds}")
	public AjaxResult remove(@PathVariable Long[] postIds) {
		return toAjax(postService.deletePostByIds(postIds));
	}

	/**
	 * 获取岗位选择框列表
	 */
	@GetMapping("/optionselect")
	public AjaxResult optionselect() {
		List<SysPost> posts = postService.selectPostAll();
		return AjaxResult.success(posts);
	}
}
