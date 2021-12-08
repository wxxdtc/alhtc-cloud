package com.alhtc.common.log.service;

import com.alhtc.common.core.constant.SecurityConstants;
import com.alhtc.system.api.RemoteLogService;
import com.alhtc.system.api.domain.SysOperLog;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 异步调用日志服务
 *
 * @author alhtc
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AsyncLogService {

	private final RemoteLogService remoteLogService;

	/**
	 * 保存系统日志记录
	 */
	@Async
	public void saveSysLog(SysOperLog sysOperLog) {
		remoteLogService.saveLog(sysOperLog, SecurityConstants.INNER);
	}
}
