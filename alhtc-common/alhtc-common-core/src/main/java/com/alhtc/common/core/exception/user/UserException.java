package com.alhtc.common.core.exception.user;

import com.alhtc.common.core.exception.base.BaseException;

/**
 * 用户信息异常类
 *
 * @author wangxiaoxu
 */
public class UserException extends BaseException {

	private static final long serialVersionUID = 1L;

	public UserException(String code, Object[] args) {
		super("user", code, args, null);
	}
}
