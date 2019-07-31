/**
 *    Auth:riozenc
 *    Date:2019年2月11日 上午9:47:34
 *    Title:org.gateway.handler.AuthorizationHandler.java
 **/
package org.gateway.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;

@ControllerAdvice
@RequestMapping("authorization")
public class AuthorizationHandler {

	@Autowired
	private RestTemplate restTemplate;

	@ResponseBody
	@PostMapping(params = "method=login")
	public String login(ServerWebExchange exchange) {
		MultiValueMap<String, String> params = exchange.getFormData().block();

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Basic dGVzdDp0ZXN0");

		// body
		MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
		requestBody.add("username", params.getFirst("username"));
		requestBody.add("password", params.getFirst("password"));
		requestBody.add("grant_type", "password");

		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(
				requestBody, httpHeaders);

		String result = restTemplate.postForObject("http://AUTH-CENTER/auth/oauth/token", requestEntity, String.class);

		System.out.println(result);
		return result;
	}

	public void getUser() {

	}

	public void getRoles() {
	}

	public void getToken() {
	}

	public void isValid() {

	}

}
