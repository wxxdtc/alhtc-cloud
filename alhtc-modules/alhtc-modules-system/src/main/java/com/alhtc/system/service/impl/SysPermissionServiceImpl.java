package com.alhtc.system.service.impl;

import com.alhtc.system.api.domain.SysUser;
import com.alhtc.system.service.ISysMenuService;
import com.alhtc.system.service.ISysPermissionService;
import com.alhtc.system.service.ISysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysPermissionServiceImpl implements ISysPermissionService {

	private final ISysRoleService roleService;

	private final ISysMenuService menuService;

	/**
	 * 获取角色数据权限
	 *
	 * @param userId 用户Id
	 * @return 角色权限信息
	 */
	@Override
	public Set<String> getRolePermission(Long userId) {
		Set<String> roles = new HashSet<>();
		// 管理员拥有所有权限
		if (SysUser.isAdmin(userId)) {
			roles.add("admin");
		} else {
			roles.addAll(roleService.selectRolePermissionByUserId(userId));
		}
		return roles;
	}

	/**
	 * 获取菜单数据权限
	 *
	 * @param userId 用户Id
	 * @return 菜单权限信息
	 */
	@Override
	public Set<String> getMenuPermission(Long userId) {
		Set<String> perms = new HashSet<String>();
		// 管理员拥有所有权限
		if (SysUser.isAdmin(userId)) {
			perms.add("*:*:*");
		} else {
			perms.addAll(menuService.selectMenuPermsByUserId(userId));
		}
		return perms;
	}
}
