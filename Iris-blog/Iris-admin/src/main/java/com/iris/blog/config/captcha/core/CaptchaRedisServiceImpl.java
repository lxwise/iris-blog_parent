package com.iris.blog.config.captcha.core;

import com.anji.captcha.service.CaptchaCacheService;
import com.iris.blog.utils.CaptchaUtil;
import lombok.Setter;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 基于 Redis 实现验证码的存储
 */
@Setter
public class CaptchaRedisServiceImpl implements CaptchaCacheService {

    private static StringRedisTemplate stringRedisTemplate;
    @Resource
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        CaptchaRedisServiceImpl.stringRedisTemplate = stringRedisTemplate;
    }
    @Override
    public String type() {
        return "redis";
    }

    @Override
    public void set(String key, String value, long expiresInSeconds) {
        stringRedisTemplate.opsForValue().set(key, value, expiresInSeconds, TimeUnit.SECONDS);
    }

    @Override
    public boolean exists(String key) {
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(key));
    }

    @Override
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public Long increment(String key, long val) {
        return stringRedisTemplate.opsForValue().increment(key,val);
    }

}
