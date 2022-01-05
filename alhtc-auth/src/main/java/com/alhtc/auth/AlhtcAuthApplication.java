package com.alhtc.auth;

import com.alhtc.common.security.annotation.EnableAlhtcFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 认证授权中心
 *
 * @author wangxiaoxu
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableAlhtcFeignClients
public class AlhtcAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlhtcAuthApplication.class, args);
	}

}
