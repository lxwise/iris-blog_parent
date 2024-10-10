package com.iris.blog.service;

import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 消息推送服务类
 */
@Slf4j
public class SseEmitterServer {
    private static final String KEY_PREFIX = "SseEmitter_";
    private static final String ONLINE_SESSION_COUNT = "OnlineSessionCount";

    /**
     * 使用map对象，便于根据userId来获取对应的SseEmitter，或者放redis里面
     */
//    public static Map<String, SseEmitter> SSE_EMITTER_MAP = new ConcurrentHashMap<>();
    public static  ExpiringMap<String, SseEmitter> SSE_EMITTER_MAP = ExpiringMap.builder()
            .maxSize(1000)
            .expiration(2, TimeUnit.HOURS)
            .variableExpiration()
            .expirationPolicy(ExpirationPolicy.ACCESSED)
            .expirationListener((key, value) -> {
                log.info("SseEmitter已过期，key：{}", key);
                removeUser(getCacheKey((String) key));
            })
            .build();

    /**
     * 创建用户连接并返回 SseEmitter
     *
     * @param sessionId 用户ID
     * @return SseEmitter
     */
    public static SseEmitter connect(String sessionId) {
        // 设置超时时间，0表示不过期。默认30秒，超过时间未完成会抛出异常：AsyncRequestTimeoutException
        String cacheKey = getCacheKey(sessionId);
        if (SSE_EMITTER_MAP.containsKey(cacheKey)) {
            return SSE_EMITTER_MAP.get(cacheKey);
        }
        SseEmitter sseEmitter = new SseEmitter(0L);
        // 注册回调
        sseEmitter.onCompletion(completionCallBack(sessionId));
        sseEmitter.onError(errorCallBack(sessionId));
        sseEmitter.onTimeout(timeoutCallBack(sessionId));
        SSE_EMITTER_MAP.put(getCacheKey(sessionId), sseEmitter);
        log.info("创建新的sse连接，当前会话：{}", sessionId);
        return sseEmitter;
    }

    /**
     * 给指定用户发送信息  -- 单播
     */
    public static void sendMsg(String userId, String message) {
        sendMessage(getCacheKey(userId), message);
    }

    /**
     * 给指定用户发送信息
     */
    public static void sendMessage(String cacheKey, String message) {
        if (SSE_EMITTER_MAP.containsKey(cacheKey)) {
            try {
                SseEmitter sseEmitter = SSE_EMITTER_MAP.get(cacheKey);
                sseEmitter.send(message, MediaType.APPLICATION_JSON);
                log.info("用户[{}]推送成功:{}", cacheKey, message);
            } catch (IOException e) {
                log.error("用户[{}]推送异常:{}", cacheKey, e.getMessage());
                removeUser(cacheKey);
            }
        }
    }

    /**
     * 向多人发布消息   -- 组播
     *
     * @param groupId 开头标识
     * @param message 消息内容
     */
    public static void groupSendMessage(String groupId, String message) {
        Set<String> keys = SSE_EMITTER_MAP.keySet().stream().filter(k -> k.startsWith(KEY_PREFIX + groupId)).collect(Collectors.toSet());
        if (CollectionUtil.isNotEmpty(keys)) {
            batchSendMessage(message, keys);
        }
    }

    /**
     * 群发所有人   -- 广播
     */
    public static void batchSendMessage(String message) {
        Set<String> keys = SSE_EMITTER_MAP.keySet();
        if (CollectionUtil.isNotEmpty(keys)) {
            batchSendMessage(message, keys);
        }
    }

    /**
     * 群发消息
     */
    public static void batchSendMessage(String message, Set<String> keys) {
        keys.forEach(key -> sendMessage(key, message));
    }

    /**
     * 移除用户连接
     */
    public static void removeUser(String cacheKey) {
        SSE_EMITTER_MAP.remove(cacheKey);
        log.info("移除用户：{}", cacheKey);
    }

    /**
     * 获取当前连接信息
     */
    public static List<String> getIds() {
        return SSE_EMITTER_MAP.entrySet().stream().map(k -> k.getKey().replace(KEY_PREFIX, "")).collect(Collectors.toList());
    }

    /**
     * 获取当前连接数量
     */
    public static int getUserCount() {
        return SSE_EMITTER_MAP.size();

    }

    private static Runnable completionCallBack(String userId) {
        return () -> {
            log.info("结束连接：{}", userId);
            removeUser(getCacheKey(userId));
        };
    }

    private static Runnable timeoutCallBack(String userId) {
        return () -> {
            log.info("连接超时：{}", userId);
            removeUser(getCacheKey(userId));
        };
    }

    private static Consumer<Throwable> errorCallBack(String userId) {
        return throwable -> {
            log.info("连接异常：{}", userId);
            removeUser(getCacheKey(userId));
        };
    }

    /**
     * 设置cache key
     *
     * @param configKey 参数键
     * @return 缓存键key
     */
    public static String getCacheKey(String configKey) {
        return KEY_PREFIX + configKey;
    }
}
