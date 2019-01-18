package com.wisdom.auth.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * Created by yxs on 2019/1/16. 编译  规则
 */
@SpringBootApplication
@EnableAuthorizationServer
@EnableFeignClients({"com.wisdom.auth.db.feignclient"})
public class AuthServerProviderApplication {
   public static void main(String[] args){
       SpringApplication.run(AuthServerProviderApplication.class, args);
   }
}
