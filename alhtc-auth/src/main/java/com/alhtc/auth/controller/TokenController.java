package com.alhtc.auth.controller;

import com.alhtc.auth.form.LoginBody;
import com.alhtc.auth.form.RegisterBody;
import com.alhtc.auth.service.SysLoginService;
import com.alhtc.common.core.domain.R;
import com.alhtc.common.core.utils.JwtUtils;
import com.alhtc.common.core.utils.StringUtils;
import com.alhtc.common.security.auth.AuthUtil;
import com.alhtc.common.security.service.TokenService;
import com.alhtc.common.security.utils.SecurityUtils;
import com.alhtc.system.api.model.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * token 控制
 *
 * @author wangxiaoxu
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TokenController {

	private final TokenService tokenService;

	private final SysLoginService sysLoginService;

	@PostMapping("login")
	public R<Map<String, Object>> login(@RequestBody LoginBody form) {
		// 用户登录
		LoginUser userInfo = sysLoginService.login(form.getUsername(), form.getPassword());
		// 获取登录token
		return R.success(tokenService.createToken(userInfo));
	}

	@DeleteMapping("logout")
	public R<?> logout(HttpServletRequest request) {
		String token = SecurityUtils.getToken(request);
		if (StringUtils.isNotEmpty(token)) {
			String username = JwtUtils.getUserName(token);
			// 删除用户缓存记录
			AuthUtil.logoutByToken(token);
			// 记录用户退出日志
			sysLoginService.logout(username);
		}
		return R.success();
	}

	@PostMapping("refresh")
	public R<?> refresh(HttpServletRequest request) {
		LoginUser loginUser = tokenService.getLoginUser(request);
		if (StringUtils.isNotNull(loginUser)) {
			// 刷新令牌有效期
			tokenService.refreshToken(loginUser);
			return R.success();
		}
		return R.success();
	}

	@PostMapping("register")
	public R<?> register(@RequestBody RegisterBody registerBody) {
		// 用户注册
		sysLoginService.register(registerBody.getUsername(), registerBody.getPassword());
		return R.success();
	}

}
