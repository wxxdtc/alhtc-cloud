package com.alhtc.system.controller;

import com.alhtc.common.core.constant.UserConstants;
import com.alhtc.common.core.domain.R;
import com.alhtc.common.core.utils.StringUtils;
import com.alhtc.common.core.web.controller.BaseController;
import com.alhtc.common.core.web.domain.AjaxResult;
import com.alhtc.common.core.web.page.TableDataInfo;
import com.alhtc.common.log.annotation.Log;
import com.alhtc.common.log.enums.BusinessType;
import com.alhtc.common.security.annotation.InnerAuth;
import com.alhtc.common.security.annotation.RequiresPermissions;
import com.alhtc.common.security.utils.SecurityUtils;
import com.alhtc.system.api.domain.SysRole;
import com.alhtc.system.api.domain.SysUser;
import com.alhtc.system.api.model.LoginUser;
import com.alhtc.system.service.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户信息
 *
 * @author alhtc
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysUserController extends BaseController {
	private final ISysUserService userService;

	private final ISysRoleService roleService;

	private final ISysPostService postService;

	private final ISysPermissionService permissionService;

	private final ISysConfigService configService;

	/**
	 * 获取用户列表
	 */
	@Log
	@RequiresPermissions("system:user:list")
	@GetMapping("/list")
	public TableDataInfo list(SysUser user) {
		startPage();
		List<SysUser> list = userService.selectUserList(user);
		return getDataTable(list);
	}

	/**
	 * 获取当前用户信息
	 */
	@InnerAuth
	@GetMapping("/info/{username}")
	public R<LoginUser> info(@PathVariable("username") String username) {
		SysUser sysUser = userService.selectUserByUserName(username);
		if (StringUtils.isNull(sysUser)) {
			return R.fail("用户名或密码错误");
		}
		// 角色集合
		Set<String> roles = permissionService.getRolePermission(sysUser.getUserId());
		// 权限集合
		Set<String> permissions = permissionService.getMenuPermission(sysUser.getUserId());
		LoginUser sysUserVo = new LoginUser();
		sysUserVo.setSysUser(sysUser);
		sysUserVo.setRoles(roles);
		sysUserVo.setPermissions(permissions);
		return R.success(sysUserVo);
	}

	/**
	 * 注册用户信息
	 */
	@InnerAuth
	@PostMapping("/register")
	public R<Boolean> register(@RequestBody SysUser sysUser) {
		String username = sysUser.getUserName();
		if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser")))) {
			return R.fail("当前系统没有开启注册功能！");
		}
		if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(username))) {
			return R.fail("保存用户'" + username + "'失败，注册账号已存在");
		}
		return R.success(userService.registerUser(sysUser));
	}

	/**
	 * 获取用户信息
	 *
	 * @return 用户信息
	 */
	@GetMapping("getInfo")
	public AjaxResult getInfo() {
		Long userId = SecurityUtils.getUserId();
		// 角色集合
		Set<String> roles = permissionService.getRolePermission(userId);
		// 权限集合
		Set<String> permissions = permissionService.getMenuPermission(userId);
		AjaxResult ajax = AjaxResult.success();
		ajax.put("user", userService.selectUserById(userId));
		ajax.put("roles", roles);
		ajax.put("permissions", permissions);
		return ajax;
	}

	/**
	 * 根据用户编号获取详细信息
	 */
	@RequiresPermissions("system:user:query")
	@GetMapping(value = {"/", "/{userId}"})
	public AjaxResult getInfo(@PathVariable(value = "userId", required = false) Long userId) {
		userService.checkUserDataScope(userId);
		AjaxResult ajax = AjaxResult.success();
		List<SysRole> roles = roleService.selectRoleAll();
		ajax.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
		ajax.put("posts", postService.selectPostAll());
		if (StringUtils.isNotNull(userId)) {
			ajax.put(AjaxResult.DATA_TAG, userService.selectUserById(userId));
			ajax.put("postIds", postService.selectPostListByUserId(userId));
			ajax.put("roleIds", roleService.selectRoleListByUserId(userId));
		}
		return ajax;
	}

	/**
	 * 新增用户
	 */
	@RequiresPermissions("system:user:add")
	@Log(title = "用户管理", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@Validated @RequestBody SysUser user) {
		if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(user.getUserName()))) {
			return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
		} else if (StringUtils.isNotEmpty(user.getPhonenumber())
				&& UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
			return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
		} else if (StringUtils.isNotEmpty(user.getEmail())
				&& UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
			return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
		}
		user.setCreateBy(SecurityUtils.getUsername());
		user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
		return toAjax(userService.insertUser(user));
	}

	/**
	 * 修改用户
	 */
	@RequiresPermissions("system:user:edit")
	@Log(title = "用户管理", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@Validated @RequestBody SysUser user) {
		userService.checkUserAllowed(user);
		if (StringUtils.isNotEmpty(user.getPhonenumber())
				&& UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
			return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
		} else if (StringUtils.isNotEmpty(user.getEmail())
				&& UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
			return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
		}
		user.setUpdateBy(SecurityUtils.getUsername());
		return toAjax(userService.updateUser(user));
	}

	/**
	 * 删除用户
	 */
	@RequiresPermissions("system:user:remove")
	@Log(title = "用户管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{userIds}")
	public AjaxResult remove(@PathVariable Long[] userIds) {
		if (ArrayUtils.contains(userIds, SecurityUtils.getUserId())) {
			return AjaxResult.error("当前用户不能删除");
		}
		return toAjax(userService.deleteUserByIds(userIds));
	}

	/**
	 * 重置密码
	 */
	@RequiresPermissions("system:user:edit")
	@Log(title = "用户管理", businessType = BusinessType.UPDATE)
	@PutMapping("/resetPwd")
	public AjaxResult resetPwd(@RequestBody SysUser user) {
		userService.checkUserAllowed(user);
		user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
		user.setUpdateBy(SecurityUtils.getUsername());
		return toAjax(userService.resetPwd(user));
	}

	/**
	 * 状态修改
	 */
	@RequiresPermissions("system:user:edit")
	@Log(title = "用户管理", businessType = BusinessType.UPDATE)
	@PutMapping("/changeStatus")
	public AjaxResult changeStatus(@RequestBody SysUser user) {
		userService.checkUserAllowed(user);
		user.setUpdateBy(SecurityUtils.getUsername());
		return toAjax(userService.updateUserStatus(user));
	}

	/**
	 * 根据用户编号获取授权角色
	 */
	@RequiresPermissions("system:user:query")
	@GetMapping("/authRole/{userId}")
	public AjaxResult authRole(@PathVariable("userId") Long userId) {
		AjaxResult ajax = AjaxResult.success();
		SysUser user = userService.selectUserById(userId);
		List<SysRole> roles = roleService.selectRolesByUserId(userId);
		ajax.put("user", user);
		ajax.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
		return ajax;
	}

	/**
	 * 用户授权角色
	 */
	@RequiresPermissions("system:user:edit")
	@Log(title = "用户管理", businessType = BusinessType.GRANT)
	@PutMapping("/authRole")
	public AjaxResult insertAuthRole(Long userId, Long[] roleIds) {
		userService.insertUserAuth(userId, roleIds);
		return success();
	}
}
