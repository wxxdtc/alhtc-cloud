package com.alhtc.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 网关
 *
 * @author wangxiaoxu
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class AlhtcGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlhtcGatewayApplication.class, args);
	}

}
