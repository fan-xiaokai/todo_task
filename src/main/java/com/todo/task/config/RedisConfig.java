package com.todo.task.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author 樊小凯
 * @Date 2023/07/30/16:21
 * @Description:
 */
@Data
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String REDIS_HOST;

    @Value("${spring.redis.port}")
    private String REDIS_PORT;

    @Value("${spring.redis.password}")
    private String REDIS_PASSWORD;

    public String getRedisHostPort(){
        return this.REDIS_HOST+":" + this.REDIS_PORT;
    }
}
