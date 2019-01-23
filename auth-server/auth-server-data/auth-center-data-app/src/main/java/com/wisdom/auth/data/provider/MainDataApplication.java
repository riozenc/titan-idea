package com.wisdom.auth.data.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * Created by yxs on 2019/1/9.
 */
@EnableResourceServer
@SpringCloudApplication
public class MainDataApplication {
    public static void main (String[] args){
        SpringApplication.run(MainDataApplication.class, args);
    }
}
