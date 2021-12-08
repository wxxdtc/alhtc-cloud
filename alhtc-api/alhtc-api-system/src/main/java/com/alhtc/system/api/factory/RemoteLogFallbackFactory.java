package com.alhtc.system.api.factory;

import com.alhtc.common.core.domain.R;
import com.alhtc.system.api.RemoteLogService;
import com.alhtc.system.api.domain.SysLoginInfo;
import com.alhtc.system.api.domain.SysOperLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 日志服务降级处理
 * 
 * @author alhtc
 */
@Component
public class RemoteLogFallbackFactory implements FallbackFactory<RemoteLogService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteLogFallbackFactory.class);

    @Override
    public RemoteLogService create(Throwable throwable)
    {
        log.error("日志服务调用失败:{}", throwable.getMessage());
        return new RemoteLogService()
        {
            @Override
            public R<Boolean> saveLog(SysOperLog sysOperLog, String source)
            {
                return null;
            }

            @Override
            public R<Boolean> saveLoginInfo(SysLoginInfo sysLoginInfo, String source)
            {
                return null;
            }
        };

    }
}
