package com.wisdom.auth.provider.config.redis;

import com.wisdom.auth.common.redis.RedisObjectSerializer;
import com.wisdom.auth.db.mapper.model.BaseModuleResources;
import com.wisdom.auth.db.mapper.model.BaseRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Created by yxs on 2019/1/16.
 * Redis配置类
 */
@Configuration
public class RedisAuthConfiguration {

    @Autowired
    private JedisConnectionFactory con;

    @Bean
    public RedisTemplate<String, BaseRole> baseRoleTemplate() {
        RedisTemplate<String, BaseRole> template = new RedisTemplate();
        template.setConnectionFactory(con);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new RedisObjectSerializer());
        return template;
    }

    @Bean
    public RedisTemplate<String, BaseModuleResources> baseModelTemplate() {
        RedisTemplate<String, BaseModuleResources> template = new RedisTemplate();
        template.setConnectionFactory(con);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new RedisObjectSerializer());
        return template;
    }
}
