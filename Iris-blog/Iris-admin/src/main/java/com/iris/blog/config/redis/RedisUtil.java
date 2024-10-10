package com.iris.blog.config.redis;

import com.iris.blog.common.ResultCode;
import com.iris.blog.common.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * redis操作工具类
 */
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 设置redis键值对(带失效时间) 当失效时间为空时永久保存
     *
     * @param key     键
     * @param value   值
     * @param timeout 过期时间 (秒)
     */
    public void saveRedisValue(String key, Object value, Long timeout) {
        if (timeout == null) {
            redisTemplate.opsForValue().set(key, value);
        } else {
            redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
        }
    }

    /**
     * 设置redis键值对(带失效时间) 当失效时间为空时永久保存
     *
     * @param key     键
     * @param value   值
     * @param timeout 过期时间 (秒)
     * @param timeUnit 时间单位
     */
    public void saveRedisValue(String key, Object value, Long timeout, TimeUnit timeUnit) {
        if (timeout == null) {
            redisTemplate.opsForValue().set(key, value);
        } else {
            redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
        }
    }

    /**
     * 设置redis键值对 并继承原有key过期时间
     *
     * @param key   键
     * @param value 值
     */
    public void saveRedisValueContinueTimeOut(String key, Object value) {
        this.saveRedisValue(key, value, this.getKeyExpireTime(key));
    }

    /**
     * 缺失时设置redis键值对(带失效时间) 当失效时间为空时永久保存
     *
     * @param key     键
     * @param value   值
     * @param timeout 过期时间 (秒)
     */
    public Boolean saveRedisValueIfAbsent(String key, Object value, Long timeout) {
        if (timeout == null) {
            return redisTemplate.opsForValue().setIfAbsent(key, value);
        } else {
            return redisTemplate.opsForValue().setIfAbsent(key, value, timeout, TimeUnit.SECONDS);
        }
    }

    /**
     * 判断key是否存在
     *
     * @param key:
     */
    public Boolean checkKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 刷新key存在时间
     *
     * @param key:
     * @param timeout: 单位秒
     */
    public void refreshKey(String key, Long timeout) {
        redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 刷新key存在时间
     *
     * @param key:
     * @param timeout: 单位秒
     * @param timeUnit: 时间单位
     */
    public void refreshKey(String key, Long timeout, TimeUnit timeUnit) {
        redisTemplate.expire(key, timeout, timeUnit);
    }

    /**
     * 从Redis中移除key
     *
     * @param key:
     * @return void

     */
    public void delKey(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 从Redis中移除key数组
     *
     * @param keys:
     * @return java.lang.Long
     */
    public Long delKey(Collection keys) {
        return redisTemplate.delete(keys);
    }

    /**
     * 自增+1 key
     *
     * @param key
     * @return
     */
    public Long incrementKey(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    /**
     * 自增 key
     * 增量 inc 正数增加 负数减少
     * @param key
     * @return
     */
    public Long incrementKey(String key,long inc) {
        return redisTemplate.opsForValue().increment(key,inc);
    }

    /**
     * 获取值
     * @param key
     * @return
     */
    public Object getValue(String key) {
        return redisTemplate.boundValueOps(key).get();
    }

    /**
     * 获取值
     *
     * @param key:
     * @param t:
     * @return T
     */
    public <T> T getKeyValue(String key, Class<T> t) {
        Object value = redisTemplate.opsForValue().get(key);
        if (value == null) {
            return null;
        }
        return (T) value;
    }

    /**
     * 放入队列左边
     *
     * @param key     -key
     * @param value   -值
     * @param timeOut -存活时间，不传入永久保存
     */
    public void addToQueueLeft(String key, Object value, Long timeOut) {
        this.redisTemplate.opsForList().leftPush(key, value);
        if (timeOut != null) {
            this.refreshKey(key, timeOut);
        }
    }

    /**
     * 放入队列右边
     *
     * @param key     -key
     * @param value   -值
     * @param timeOut -存活时间，不传入永久保存
     */
    public void addToQueueRight(String key, Object value, Long timeOut) {
        this.redisTemplate.opsForList().rightPush(key, value);
        if (timeOut != null) {
            this.refreshKey(key, timeOut);
        }
    }

    /**
     * 从队列中移除
     *
     * @param key 键名
     * @param value
     * @param -1：指定从List尾部开始向前搜索要移除的元素
     */
    public void removeFormList(String key, Object value) {
        this.redisTemplate.opsForList().remove(key, -1, value);
    }

    /**
     * 从队列中移除
     * @param key 键名
     * @param value
     * @param +1：指定从List头部开始向后搜索要移除的元素
     */
    public void removeFormListLeft(String key, Object value) {
        this.redisTemplate.opsForList().remove(key, 1, value);
    }

    /**
     * 从队列右边取出一个，并将该值移除队列
     *
     * @param key
     * @return
     */
    public <T> T getPopOneFromQueueRight(String key, Class<T> t) {
        return (T) this.redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 从队列左边取出一个，并将该值移除队列
     *
     * @param key
     * @return
     */
    public <T> T getOneFromQueueLeft(String key, Class<T> t) {
        return (T) this.redisTemplate.opsForList().index(key, 0);
    }

    /**
     * 从队列右边取出一个，不移除队列
     *
     * @param key
     * @return
     */
    public <T> T getOneFromQueueRight(String key, Class<T> t) {
        return (T) this.redisTemplate.opsForList().index(key, -1);
    }

    /**
     * 从队列左边取出一个，并将该值移除队列
     *
     * @param key
     * @return
     */
    public <T> T getPopOneFromQueueLeft(String key, Class<T> t) {
        return (T) this.redisTemplate.opsForList().leftPop(key);
    }


    /**
     * 取出队列
     *
     * @param key   -key
     * @param t     -泛型类
     * @param start -起始位
     * @param end   -结束位  为-1时为起始位之后全部
     * @param <T>
     * @return
     */
    public <T> List<T> getFromQueue(String key, Class<T> t, @NotNull Long start, @NotNull Long end) {
        if (StringUtils.isBlank(key)) {
            return Collections.EMPTY_LIST;
        }
        return (List<T>) this.redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 获取队列长度
     * @param key
     * @return
     */
    public Long getQueueSize(String key) {
        return this.redisTemplate.opsForList().size(key);
    }

    /**
     * 从队列中移除
     * @param key 键名
     * @param value
     * @return
     */
    public Long removeOneFromQueue(@NotBlank String key, @NotNull Object value) {
        return this.redisTemplate.opsForList().remove(key, 0, value);
    }

    /**
     * 添加一个元素到set中(有排序方案）
     *
     * @param redisKey -key
     * @param object   -值
     * @param score    -排序值
     */
    public void addToSet(String redisKey, Object object, @NotNull double score) {
        this.redisTemplate.opsForZSet().add(redisKey, object, score);
    }

    /**
     * 批量添加元素到set中
     * @param redisKey
     * @param typedTupleSet
     */
    public void bathZSet(String redisKey, Set<ZSetOperations.TypedTuple<Object>> typedTupleSet) {
        this.redisTemplate.opsForZSet().add(redisKey, typedTupleSet);
    }

    /**
     * @description 无排序方案
     * @param: key
     * @param: value
     * @return: void
     */
    public Long addToSet(String key, Object value) {
        return this.redisTemplate.opsForSet().add(key, value);
    }

    /**
     * 从set中移除 无排序方案
     *
     * @param key
     * @param value
     */
    public void delFromSet(String key, Object value) {
        this.redisTemplate.opsForSet().remove(key, value);
    }

    /**
     * 从set中获取一个分值范围内的元素
     *
     * @param redisKey   -redisKey
     * @param startScore -分值开始
     * @param endScore   -分值结束
     * @param offset     -偏移量
     * @param count      -取出个数
     * @return
     */
    public <T> Set<T> getFromSet(String redisKey, double startScore, double endScore, long offset, long count,
                                 Class<T> tClass) {
        Set<T> set = (Set<T>) this.redisTemplate.opsForZSet().rangeByScore(redisKey, startScore, endScore, offset,
                count);
        if (set != null && set.size() > 0) {
            return set;
        }
        return null;
    }

    /**
     * zSet 交集
     *
     * @param redisKey
     * @param destKey
     * @param zSetKeys
     * @param weights
     * @return
     */
    public Long zIntersectAndStore(String redisKey, String destKey, List<String> zSetKeys, int... weights) {
        return redisTemplate.opsForZSet().intersectAndStore(redisKey, zSetKeys, destKey, RedisZSetCommands.Aggregate.SUM, RedisZSetCommands.Weights.of(weights));
    }

    /**
     * Set 并集
     *
     * @param destKey
     * @param setKeys
     * @return
     */
    public Long unionAndStore(String destKey, Set<String> setKeys) {
        return redisTemplate.opsForSet().unionAndStore(setKeys, destKey);
    }

    /**
     * zSet key 分值
     *
     * @param redisKey
     * @param key
     * @return
     */
    public <T> Double getZsetScore(String key, T value) {
        return redisTemplate.opsForZSet().score(key, value);
    }

    /**
     * 取出n条，这n条会被移出队列 按分数排序
     *
     * @param redisKey -redisKey
     * @param start    开始
     * @param end      结束
     * @param <T>
     * @return
     */
    public <T> Set<T> popFromZSet(String redisKey, long start, long end, Class<T> tClass) {
        Set<T> set = this.redisTemplate.opsForZSet().range(redisKey, start, end);
        if (set != null && set.size() > 0) {
            Long remove = this.redisTemplate.opsForZSet().remove(redisKey, set.toArray());
            return set;
        }
        return null;
    }

    /**
     * 取出n条，这n条会被移出队列 按分数排序
     *
     * @param redisKey -redisKey
     * @param start    开始
     * @param end      结束
     * @param <T>
     * @return
     */
    public <T> Set<T> popFromZSet(String redisKey, double min, double max, long start, long end, Class<T> tClass) {
        Set<T> set = this.redisTemplate.opsForZSet().rangeByScore(redisKey, min, max, start, end);
        if (set != null && set.size() > 0) {
            Long remove = this.redisTemplate.opsForZSet().remove(redisKey, set.toArray());
            return set;
        }
        return null;
    }

    /**
     * @description 获取set长度
     * @param: redisKey
     * @return: java.lang.Long
     */
    public Long getSetSize(String redisKey) {
        return this.redisTemplate.opsForSet().size(redisKey);
    }

    /**
     * 查询set中有没有这个元素
     *
     * @param redisKey
     * @param member
     * @return
     */
    public Boolean isMemBer(String redisKey, Object member) {
        return redisTemplate.opsForSet().isMember(redisKey, member);
    }

    /**
     * 从set中删除元素
     *
     * @param redisKey -key
     * @param values   -元素集合
     * @return
     */
    public <T> long removeFromSet(String redisKey, @NotNull Collection<T> values) {
        long removeNum = 0;
        if (values == null || values.size() < 1) {
            return removeNum;
        }
        for (T t : values) {
            removeNum += this.redisTemplate.opsForZSet().remove(redisKey, t);
        }
        return removeNum;
    }

    /**
     * 从set中删除元素
     *
     * @param redisKey -key
     * @param value   -元素
     * @return
     */
    public void removeFromZSet(String redisKey, @NotNull Object value) {
        if (value == null) {
            return;
        }
        this.redisTemplate.opsForZSet().remove(redisKey, value);
    }

    /**
     * @description 获取set所有
     * @param: redisKey
     * @param: tClass
     * @return: java.util.Set<T>
     */
    public <T> Set<T> getAllSet(String redisKey, Class<T> tClass) {
        return this.redisTemplate.opsForSet().members(redisKey);
    }

    /**
     * 获取set所有
     * @param key redisKey
     * @return
     * @param <T>
     */
    public <T> Set<T> getAllSet(String key) {
        Set<T> result = (Set<T>) redisTemplate.opsForSet().members(key);
        return result;
    }
    /**
     * 获取所有符合条件的key
     *
     * @param redisKeyPre:
     * @return java.util.Set<java.lang.String>
     */
    public Set<String> getKeyByPre(String redisKeyPre) {
        if (!redisKeyPre.endsWith("*")) {
            redisKeyPre = redisKeyPre + "*";
        }
        return this.redisTemplate.keys(redisKeyPre);
    }

    /**
     * @description 获取redis锁 返回{false}表示获取锁失败
     * @param: key
     * @param: value
     * @param: expirationTime
     * @return: java.lang.Boolean
     */
    public Boolean getLock(String key, Object value, @NotNull Long expirationTime) {
        return this.redisTemplate.opsForValue().setIfAbsent(key, value, expirationTime, TimeUnit.SECONDS);
    }

    /**
     * @description 获取redis锁 返回{false}表示获取锁失败
     * @param: key
     * @param: value
     * @return: java.lang.Boolean
     */
    public Boolean getLock(String key, Object value) {
        return this.redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * @description 删除锁
     * @param: key
     * @param: value
     * @param: expirationTime
     * @return: java.lang.Boolean
     */
    public void delLock(String key, Object value) {
        String lockValue = this.getKeyValue(key, String.class);
        if (null == lockValue || !lockValue.equals(value)) {
            return;
        }
        this.delKey(key);
    }

    /**
     * 获取redis key过期时间 单位秒
     *
     * @param redisKey:
     * @return java.lang.Long
     */
    public Long getKeyExpireTime(String redisKey) {
        return this.redisTemplate.opsForValue().getOperations().getExpire(redisKey);
    }

    /**
     * 获取redis key过期时间
     *
     * @param redisKey:
     * @return java.lang.Long
     */
    public Long getKeyExpireTime(String redisKey, TimeUnit timeUnit) {
        return this.redisTemplate.opsForValue().getOperations().getExpire(redisKey, timeUnit);
    }

    /**
     * @description redis原子增长
     * @param: redisKey
     * @param: delta
     */
    public Integer AtomicAddAndGet(String redisKey, int delta) {
        RedisAtomicInteger redisAtomicInteger = new RedisAtomicInteger(redisKey, this.redisTemplate.getConnectionFactory());
        return redisAtomicInteger.addAndGet(delta);
    }

    /**
     * 设置键过期时间
     * @param redisKey
     * @param date
     */
    public void setKeyExpireTime(String redisKey, Date date) {
        this.redisTemplate.expireAt(redisKey, date);
    }

    /**
     * 获取一项Hash 值
     * @param key
     * @param hKey
     * @return java.lang.ObjectF+
     **/
    public Object getHashKey(String key, String hKey) {
        return this.redisTemplate.opsForHash().get(key, hKey);
    }

    /**
     * 获取hash 键值对数量
     *
     * @param key
     * @return
     */
    public Long getHashKeySize(String key) {
        return this.redisTemplate.opsForHash().size(key);
    }

    /**
     * 删除 hashKey 项
     *
     * @param key
     * @param hKey
     * @return
     */
    public Object delHashKey(String key, String hKey) {
        return this.redisTemplate.opsForHash().delete(key, hKey);
    }

    /**
     * 删除 多个hashKey 项
     *
     * @param key
     * @param hkeys
     * @return
     */
    public Object delHashKeys(String key, Object... hkeys) {
        return this.redisTemplate.opsForHash().delete(key, hkeys);
    }

    /**
     * 添加所有hash key value
     * @param key
     * @param map
     * @return void
     **/
    public void putHashAll(String key, Map map) {
        this.redisTemplate.opsForHash().putAll(key, map);
    }

    /*
     * 添加一项hash key value
     * @param key
     * @param hKey
     * @param value
     * @return void
     **/
    public void addHashKeyValue(String key, String hKey, Object value) {
        this.redisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     * 获取全部hash key value
     * @param key  hash键
     * @param keys 键集合
     * @return java.util.List
     **/
    public List getHashKeyAll(String key, Collection<String> keys) {
        return this.redisTemplate.opsForHash().multiGet(key, keys);
    }

    /**
     * 获取hash key值集合
     * @param key
     * @return java.util.Map
     **/
    public Map getHashKeyAll(String key) {
        return this.redisTemplate.opsForHash().entries(key);
    }


    /**
     * score的增加or减少 zincrby
     *
     * @param key
     * @param value
     * @param score
     */
    public <T> Double incrZet(String key, T value, Double score) {
        return redisTemplate.opsForZSet().incrementScore(key, value, score);
    }

    /**
     * 校验短信验证码
     * @param smsCode
     * @param redisKey
     */
    public void checkSmsCode(String smsCode, String redisKey) {
        String redisCode = this.getKeyValue(redisKey, String.class);
        if (StringUtils.isBlank(smsCode)) {
            throw new BusinessException(ResultCode.CAPTCHA_CODE_ERROR);
        }
        if (!smsCode.equals(redisCode)) {
            throw new BusinessException(ResultCode.CAPTCHA_CODE_ERROR);
        }
        // 清除已经验证过的验证码
        this.delKey(redisKey);
    }

    /**
     * 位图结构 设置
     *
     * @param key
     * @param offset
     * @param value
     */
    public void setBit(String key, Long offset, boolean value) {
        this.redisTemplate.opsForValue().setBit(key, offset, value);
    }

    /**
     * 位图结构 获取
     *
     * @param key
     * @param offset
     * @return
     */
    public Boolean getBit(String key, Long offset) {
        return this.redisTemplate.opsForValue().getBit(key, offset);
    }

    /**
     * 位图结构 计数
     *
     * @param key
     * @return
     */
    public Long bitCount(String key) {
        return (Long) this.redisTemplate.execute((RedisCallback<Long>) connection -> connection.bitCount(key.getBytes()));
    }

    /**
     * 获取多个key的value
     *
     * @param keys
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> List<T> getKeys(Collection keys, Class<T> clazz) {
        List<T> list = (List<T>) redisTemplate.opsForValue().multiGet(keys);
        return list;
    }

    /**
     * hash自增+1
     *
     * @param key
     * @param hKey
     * @return Long
     */
    public Long incrementHashKey(String key, String hKey, Long delta) {
        return redisTemplate.opsForHash().increment(key, hKey, null != delta && delta != 0 ? delta : 1);
    }

    /**
     * 从队列右边取出一个元素放置，另一队列的左边
     *
     * @param sourceKey      源队列key
     * @param destinationKey 目标队列key
     * @param clz            转换类型
     * @param <T>
     * @return
     */
    public <T> T queueRightPopAndLeftPush(String sourceKey, String destinationKey, Class<T> clz) {
        return (T) redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey);
    }

    /**
     * 根据key查询score值
     *
     * @param key
     * @param member
     * @return
     */
    public Long getZSetScore(String key, Object member) {
        Double score = redisTemplate.opsForZSet().score(key, member);
        if (null != score) {
            return score.longValue();
        }
        return null;
    }

    /**
     * @description 获取zset长度
     */
    public Long getZSetSize(String redisKey) {
        return redisTemplate.opsForZSet().size(redisKey);
    }

    /**
     * @description 逆向获取zset元素
     */
    public <T> Set<T> getZSetReverseRange(String redisKey, Integer start, Integer end, Class<T> tClass) {
        return redisTemplate.opsForZSet().reverseRange(redisKey, start.longValue(), end.longValue());
    }


    /**
     * @description 删除zset指定下标的值
     */
    public Long removeRangeZSet(String redisKey, Long start, Long end) {
        return redisTemplate.opsForZSet().removeRange(redisKey, start, end);
    }

    /**
     * 从Zset中移除(可批量) 批量删除时传入数组 无排序方案
     *
     * @param key
     * @param
     */
    public Long delFromZSet(String key, Object... value) {
        return redisTemplate.opsForZSet().remove(key, value);
    }
}
