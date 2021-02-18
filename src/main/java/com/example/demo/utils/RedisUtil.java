package com.example.demo.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * redis 工具类
 */
@Slf4j
@Component
public class RedisUtil {
    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 简单加锁(1.某个线程所持有的锁可以被其它线程随意释放掉,2.不支持可配置的阻塞/非阻塞锁)
     *
     * @param key 锁名称
     * @return lock 是否获取锁， true：获取, false：未获取
     */
    public boolean lock(String key) {
        boolean lock;
        try {
            // setIfAbsent 等价于 Redis 的 setnx 命令，具有原子性
            lock = Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, "LOCK"));
        } catch (Exception e) {
            log.error("获取锁：{}，出现异常{}", key, e);
            return false;
        }
        return lock;
    }

    /**
     * 简单释放锁
     *
     * @param key 锁名称
     */
    public void unlock(String key) {
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 加锁/获取锁
     *
     * @param key      锁名称
     * @param identity 标识
     * @return boolean 是否加锁成功
     */
    public boolean lock(String key, String identity) {
        boolean lock;
        try {
            lock = Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, identity));
        } catch (Exception e) {
            log.error("获取锁：{}，出现错误{}", key, e);
            return false;
        }
        return lock;
    }

    /**
     * 释放锁
     *
     * @param key      锁名称
     * @param identity 标识
     */
    public void unlock(String key, String identity) {
        try {
            Object obj = redisTemplate.opsForValue().get(key);
            String value = obj != null ? obj.toString() : null;
            if (value != null) {
                if (value.equals(identity)) {
                    redisTemplate.delete(key);
                }
            }
        } catch (Exception e) {
            log.error("释放锁操作失败,key:" + key + "失败原因:", e);
        }
    }

    /**
     * 阻塞锁
     * @param key 锁名称
     * @param identity 标识
     * @param sec 超时时长（秒）
     * @return boolean 是否成功获取锁
     */
    public boolean lockb(String key, String identity, int sec) {
        try {
            int count = 0;
            while (!lock(key, identity)) {
                Thread.sleep(100);
                count++;
                if (count > sec * 1000L / 100) {
                    if (sec != 0) {
                        log.warn("线程：{}获取锁{}超时", Thread.currentThread().getName(), key);
                    }
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            log.error("获取锁：{}，出现错误{}", key, e);
            return false;
        }
    }

    /**
     * 默认锁最大存活时长
     */
    private final static int LOCK_MAX_TIMEOUT_SEC = 600;

    /**
     * 加锁/获取锁 (加入超时)
     * @param key 锁名称
     * @param identity 标识
     * @param expireSec 过期时间
     * @return boolean 是否加锁成功
     */
    public boolean expireLock(String key, String identity, int expireSec) {
        boolean lock;
        try {
            lock = Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, identity, Duration.ofSeconds(expireSec)));
        } catch (Exception e) {
            log.error("获取锁：{}，出现错误{}", key, e);
            return false;
        }
        return lock;
    }

    public boolean expireLock(String key, String identity) {
        return expireLock(key, identity, LOCK_MAX_TIMEOUT_SEC);
    }
}
