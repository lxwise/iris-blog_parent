package com.iris.blog.config.redis;

import com.iris.blog.common.constant.RedisKeyConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 参数管理
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0
 */
@Component
public class SysParamsRedis {
    @Resource
    private RedisUtil redisUtil;

    public void delete(String paramCodes) {

        redisUtil.delHashKey(RedisKeyConstant.SYS_PARAMS_KEY, paramCodes);
    }

    public void set(String paramCode, String paramValue){
        if(paramValue == null){
            return ;
        }
        redisUtil.addHashKeyValue(RedisKeyConstant.SYS_PARAMS_KEY, paramCode, paramValue);
    }

    public String get(String paramCode){
        return (String) redisUtil.getHashKey(RedisKeyConstant.SYS_PARAMS_KEY, paramCode);
    }

}
