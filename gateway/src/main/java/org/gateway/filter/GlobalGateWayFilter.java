/**
 *    Auth:riozenc
 *    Date:2019年1月3日 上午10:50:17
 *    Title:org.gateway.filter.GlobalGateWayFilter.java
 **/
package org.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalGateWayFilter implements GlobalFilter {

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		// TODO Auto-generated method stub
		
		System.out.println("GlobalGateWayFilter---"+exchange.getRequest().getRemoteAddress());
		HttpHeaders headers = exchange.getRequest().getHeaders();
		Iterator iterator = headers.keySet().iterator();
		while (iterator.hasNext()){
			Object key = iterator.next();
			List list = headers.get(key);
			System.out.println("======================key="+key.toString());
			for (Object value:list ) {
				System.out.println(value.toString()+";;");
			}

		}
        return chain.filter(exchange);
	}

}
