package com.alhtc.gateway.config;

import com.alhtc.gateway.handler.ValidateCodeHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

/**
 * 路由配置信息
 *
 * @author alhtc
 */
@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RouterFunctionConfiguration {

	private final ValidateCodeHandler validateCodeHandler;

	@Bean
	public RouterFunction<ServerResponse> routerFunction() {
		return RouterFunctions.route(
				RequestPredicates.GET("/code").and(accept(MediaType.TEXT_PLAIN)),
				validateCodeHandler);
	}
}
