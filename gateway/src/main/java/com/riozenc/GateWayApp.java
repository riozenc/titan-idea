package com.riozenc;

import java.net.URI;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder.Builder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

/**
 * Hello world!
 *
 */
@RestController
@SpringBootApplication
@EnableEurekaClient
public class GateWayApp {

	private static final Logger LOGGER = LoggerFactory.getLogger(GateWayApp.class);

	public static void main(String[] args) {
//		RoutePredicateHandlerMapping
		SpringApplication.run(GateWayApp.class, args);
		LOGGER.info(" Start GateWayApp Done");
	}

	@Bean
	public RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder) {
		Builder builder = routeLocatorBuilder.routes();
//		Builder asyncBuilder = builder.route(r -> r.path("/ab").
//				uri("lb://SECURITY-SERVER/security/appInfo?method=filter")
//				.filter(new PreGatewayFilter()));

//		Builder asyncBuilder = builder
//				.route(r -> r.path("/ab").filters(f -> f.filter(new PreGatewayFilter())).uri("http://httpbin.org:80"));

		
//		http://172.21.99.14:8099/wisdomServer/wisdomClient/main.html
		Builder asyncBuilder = builder
				.route(r -> r.path("/wisdomServer/wisdomClient/main.html").uri("http://172.21.99.14:8099/"));

		RouteLocator routeLocator = asyncBuilder.build();
		return routeLocator;
	}

}
