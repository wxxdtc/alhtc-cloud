package com.alhtc.modules.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 监控中心
 *
 * @author wangxiaoxu
 */
@EnableAdminServer
@SpringBootApplication
public class AlhtcMonitorApplication {
	public static void main(String[] args) {
		SpringApplication.run(AlhtcMonitorApplication.class, args);
	}
}
