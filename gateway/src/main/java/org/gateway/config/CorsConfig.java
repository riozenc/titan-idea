/**
 *    Auth:riozenc
 *    Date:2019年2月15日 上午11:25:47
 *    Title:org.gateway.config.CorsConfig.java
 **/
package org.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

import java.util.List;

@Configuration
public class CorsConfig {
	private static final String MAX_AGE = "18000L";
	@Autowired
	private RestTemplate restTemplate;
	@Bean
	public WebFilter corsFilter() {
		return (ServerWebExchange ctx, WebFilterChain chain) -> {
			ServerHttpRequest request = ctx.getRequest();
			if (CorsUtils.isCorsRequest(request)) {
				HttpHeaders requestHeaders = request.getHeaders();
				ServerHttpResponse response = ctx.getResponse();
				HttpMethod requestMethod = requestHeaders.getAccessControlRequestMethod();
				HttpHeaders headers = response.getHeaders();
				headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, requestHeaders.getOrigin());
				headers.addAll(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,
						requestHeaders.getAccessControlRequestHeaders());
				if (requestMethod != null) {
					headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, requestMethod.name());
				}
				headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
				headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*");
				headers.add(HttpHeaders.ACCESS_CONTROL_MAX_AGE, MAX_AGE);
				if (request.getMethod() == HttpMethod.OPTIONS) {
					response.setStatusCode(HttpStatus.OK);
					return Mono.empty();
				}
				else if(request.getHeaders().containsKey("Authorization")){
					List<String> list = request.getHeaders().get("Authorization");
					String value = "";
					for (int i=0; i<list.size(); i++){
						value = list.get(i);
						if(value.toLowerCase().startsWith("Bearer".toLowerCase())){
							break;
						}
					}
					if(value.isEmpty()){
						response.setStatusCode(HttpStatus.PROXY_AUTHENTICATION_REQUIRED);
						return Mono.empty();
					}
					String userInfo = restTemplate.getForObject("http://AUTH-CENTER/auth/extractToken?token="+value, String.class);

					System.out.println(userInfo);
				}
			}
			return chain.filter(ctx);
		};

	}
}
