package com.iris.blog.common.aspect;

import com.iris.blog.common.ResultCode;
import com.iris.blog.common.annotation.RLimit;
import com.iris.blog.common.exception.BusinessException;
import com.iris.blog.utils.IPUtils;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @author lstar
 * @create 2024-07
 * @description: 基于RateLimiter的限流 AOP
 */
@Aspect
@Component
@RequiredArgsConstructor
public class RLimitAspect {

    private final RedisTemplate redisTemplate;

    @Before("@annotation(limit)")
    public void doBefore(JoinPoint joinPoint, RLimit limit) throws Throwable {
        int time = limit.time();

        HttpServletRequest request = IPUtils.getRequest();
        // 拼接redis key = IP + Api限流
        assert request != null;
        String key = IPUtils.getIp() + request.getRequestURI();
        // 获取redis的value
        Integer maxTimes = null;
        Object value = redisTemplate.opsForValue().get(key);
        if (value != null) {
            maxTimes = (Integer) value;
        }
        if (maxTimes == null) {
            // 如果redis中没有该ip对应的时间则表示第一次调用，保存key到redis
            redisTemplate.opsForValue().set(key, 1, time, TimeUnit.SECONDS);
        } else if (maxTimes < limit.count()) {
            // 如果redis中的时间比注解上的时间小则表示可以允许访问,这是修改redis的value时间
            redisTemplate.opsForValue().set(key, maxTimes + 1, time, TimeUnit.SECONDS);
        } else {
            // 请求过于频繁
            throw new BusinessException(ResultCode.VISIT_LIMIT_ERROR.code, limit.msg());
        }
    }
}
