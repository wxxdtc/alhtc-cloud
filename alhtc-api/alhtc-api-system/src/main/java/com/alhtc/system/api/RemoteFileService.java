package com.alhtc.system.api;

import com.alhtc.common.core.constant.ServiceNameConstants;
import com.alhtc.common.core.domain.R;
import com.alhtc.system.api.domain.SysFile;
import com.alhtc.system.api.factory.RemoteFileFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件服务
 *
 * @author alhtc
 */
@FeignClient(contextId = "remoteFileService", value = ServiceNameConstants.FILE_SERVICE, fallbackFactory = RemoteFileFallbackFactory.class)
public interface RemoteFileService {
	/**
	 * 上传文件
	 *
	 * @param file 文件信息
	 * @return 结果
	 */
	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	R<SysFile> upload(@RequestPart(value = "file") MultipartFile file);
}
