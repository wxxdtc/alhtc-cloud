package com.alhtc.system;

import com.alhtc.common.security.annotation.EnableAlhtcFeignClients;
import com.alhtc.common.security.annotation.EnableCustomConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 系统模块
 *
 * @author wangxiaoxu
 */
@EnableCustomConfig
@EnableAlhtcFeignClients
@SpringBootApplication
public class AlhtcSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlhtcSystemApplication.class, args);
	}

}
