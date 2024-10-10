package com.iris.blog.config.captcha.config;

import com.anji.captcha.properties.AjCaptchaProperties;
import com.anji.captcha.service.CaptchaCacheService;
import com.anji.captcha.service.impl.CaptchaServiceFactory;
import com.iris.blog.config.captcha.core.CaptchaRedisServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 基于 aj-captcha 实现滑块验证码，文档：https://ajcaptcha.beliefteam.cn/captcha-doc/
 * @author lstar
 * @date: 2024-04
 * @description: 验证码的配置类
 */
@Configuration(proxyBeanMethods = false)
public class CaptchaConfiguration {

    @Bean
    public CaptchaCacheService captchaCacheService(AjCaptchaProperties config,
                                                   StringRedisTemplate stringRedisTemplate) {
        CaptchaCacheService captchaCacheService = CaptchaServiceFactory.getCache(config.getCacheType().name());
        if (captchaCacheService instanceof CaptchaRedisServiceImpl) {
            ((CaptchaRedisServiceImpl) captchaCacheService).setStringRedisTemplate(stringRedisTemplate);
        }
        return captchaCacheService;
    }

}
