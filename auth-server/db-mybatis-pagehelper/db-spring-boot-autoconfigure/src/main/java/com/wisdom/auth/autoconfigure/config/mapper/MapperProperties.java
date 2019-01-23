package com.wisdom.auth.autoconfigure.config.mapper;

import org.springframework.boot.context.properties.ConfigurationProperties;
import tk.mybatis.mapper.entity.Config;

/**
 * Created by yxs on 2019/1/7.
 */
@ConfigurationProperties(prefix = "mapper")
public class MapperProperties extends Config{
}
