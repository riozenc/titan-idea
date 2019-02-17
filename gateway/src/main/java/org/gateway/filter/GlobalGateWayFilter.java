/**
 *    Auth:riozenc
 *    Date:2019年1月3日 上午10:50:17
 *    Title:org.gateway.GlobalGateWayFilter.java
 **/
package org.gateway.filter;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class GlobalGateWayFilter implements GlobalFilter, Ordered {

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		// TODO Auto-generated method stub
		System.out.println("GlobalGateWayFilter---" + exchange.getRequest().getRemoteAddress());

		return chain.filter(exchange);
		
		
//		URI uri = exchange.getRequest().getURI();
//
//		if (isSecurityURI(uri)) {
//			return chain.filter(exchange);
//		}
//
//		// 认证校验
////		Boolean flag = restTemplate.getForObject("http://SECURITY-SERVER/security/user?method=tokenIsValid",
////				Boolean.class);
//
//		Map<String, String> map = new HashMap<>();
//		map.put("token", "123");
//		map.put("url", "111");
//		Boolean flag = restTemplate.postForObject("http://SECURITY-SERVER/security/user?method=tokenIsValid", map,
//				Boolean.class);
//
//		if (flag) {
//			return chain.filter(exchange);
//		} else {
//			return Mono.error(new Exception("没有权限哦!"));
//		}

	}

	public boolean isSecurityURI(URI uri) {
		if (uri.getPath().contains("security")) {
			return true;
		}
		return false;
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return 1;
	}
}
