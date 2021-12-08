package com.alhtc.system.api.factory;

import com.alhtc.common.core.domain.R;
import com.alhtc.system.api.RemoteUserService;
import com.alhtc.system.api.domain.SysUser;
import com.alhtc.system.api.model.LoginUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 用户服务降级处理
 *
 * @author alhtc
 */
@Component
public class RemoteUserFallbackFactory implements FallbackFactory<RemoteUserService> {
	private static final Logger log = LoggerFactory.getLogger(RemoteUserFallbackFactory.class);

	@Override
	public RemoteUserService create(Throwable throwable) {
		log.error("用户服务调用失败:{}", throwable.getMessage());
		return new RemoteUserService() {
			@Override
			public R<LoginUser> getUserInfo(String username, String source) {
				return R.fail("获取用户失败:" + throwable.getMessage());
			}

			@Override
			public R<Boolean> registerUserInfo(SysUser sysUser, String source) {
				return R.fail("注册用户失败:" + throwable.getMessage());
			}
		};
	}
}
