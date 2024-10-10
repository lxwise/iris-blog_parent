package com.iris.blog.common.aspect;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSONObject;
import com.iris.blog.common.annotation.OperateLog;
import com.iris.blog.common.constant.CommonConstant;
import com.iris.blog.common.constant.RedisKeyConstant;
import com.iris.blog.config.redis.RedisUtil;
import com.iris.blog.dao.entity.OperateLogEntity;
import com.iris.blog.domain.vo.SessionUserVO;
import com.iris.blog.utils.IPUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author lstar
 * @create 2024-04
 * @description:操作日志切面
 */
@Aspect
@Component
@Slf4j
public class OperateLogAspect {

    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
//    ThreadFactory customThreadFactory = new ThreadFactoryBuilder().setNameFormat("log-producer-pool-%d").build();
//    ExecutorService pool = new ThreadPoolExecutor(3, 10, 0L, TimeUnit.MILLISECONDS,
//            new LinkedBlockingQueue<>(1000), customThreadFactory, new ThreadPoolExecutor.AbortPolicy());

    @Resource
    private RedisUtil redisUtil;
    private StopWatch sw;
    @Pointcut("@annotation(com.iris.blog.common.annotation.OperateLog)")
    public void logPointCut() {

    }

    @Around(value = "logPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        //因给了演示账号所有权限以供用户观看，所以执行业务前需判断是否是管理员操作
//        if  (!StpUtil.hasRole("admin")) {
//            throw new BusinessException(NO_PERMISSION);
//        }
        sw = new StopWatch();
        sw.start();
        //执行方法
        Object result = joinPoint.proceed();
        try {
            //保存日志
            this.saveOperateLog(joinPoint);
        } catch (Exception e) {
            log.error("保存操作日志失败:", e);
        }

        return result;
    }

    private void saveOperateLog(ProceedingJoinPoint joinPoint) {
        OperateLogEntity entity = getOperateLog(joinPoint);
        entity.setLogType(Boolean.FALSE);
        this.saveLog(entity);
    }

    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) throws Exception {
        OperateLogEntity entity = getOperateLog(joinPoint);
        entity.setErrorInfo(JSONObject.toJSONString(e));
        entity.setErrorMessage(e.getMessage());
        entity.setLogType(Boolean.TRUE);
        this.saveLog(entity);
    }

    @NotNull
    private OperateLogEntity getOperateLog(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        OperateLog info = method.getAnnotation(OperateLog.class);
        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        //请求的参数
        Object[] args = joinPoint.getArgs();

        String params = JSONObject.toJSONString(args);
        //获取request
        HttpServletRequest request = IPUtils.getRequest();
        //接口耗时
        sw.stop();
        // 当前操作用户
        SessionUserVO user = (SessionUserVO) StpUtil.getSession().get(CommonConstant.LOGIN_USER);
        OperateLogEntity entity = new OperateLogEntity();
        entity.setUserId(user.getId());
        entity.setUserName(user.getUsername());
        entity.setRequestUrl(request != null ? request.getRequestURI() : null);
        entity.setRequestType(request != null ? request.getMethod() : null);
        entity.setRequestClassName(className);
        entity.setRequestMethod(methodName);
        entity.setRequestParams(params);
        entity.setRequestTime(String.valueOf(sw.getLastTaskTimeMillis()));
        entity.setRequestIp(IPUtils.getIp());
        entity.setRequestAddress(IPUtils.getIpAreaAddress(null));
        entity.setOperateDesc(info.value());
        entity.setOperateOs(IPUtils.getDeviceDetails());
        return entity;
    }

    /**
     * 保存Log到Redis消息队列
     */
    public void saveLog(OperateLogEntity entity){

        threadPoolTaskExecutor.setThreadNamePrefix("log-producer-pool-");
        //异步保存到队列
        threadPoolTaskExecutor.execute(() -> {
            redisUtil.addToQueueLeft(RedisKeyConstant.SYS_LOG_KEY,entity,null);
            log.info("保存日志的线程名字:{}",Thread.currentThread().getName());
        });
    }

}
