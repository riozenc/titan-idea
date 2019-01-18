package com.wisdom.auth.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * Created by yxs on 2019/1/17.
 */
@EnableResourceServer
@SpringBootApplication
public class AuthServerDataApplication {
    public static void main (String[] args){
        SpringApplication.run(AuthServerDataApplication.class, args);
    }
}
