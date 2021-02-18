package com.example.demo.redistest;

import com.example.demo.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class WorkService {
    @Autowired
    private RedisUtil redisUtil;
    public static int count = 0;

    private static final int TIME = 1000;

    public void work() {
        String key = "TEST_KEY";
        if (redisUtil.lock(key)) {
            log.info("线程：{},已经获取锁", Thread.currentThread().getName());
            try {
                for (int i = 0; i < TIME; i++) {
                    WorkService.count++;
                }
            } catch (Exception e) {
                log.error("发生错误", e);
            } finally {
                redisUtil.unlock(key);
                log.info("线程：{},已经释放锁", Thread.currentThread().getName());
            }
        } else {
            log.info("线程：{},未获取到锁", Thread.currentThread().getName());
        }
    }

    public void notLockWork() {
        for (int i = 0; i < TIME; i++) {
            WorkService.count++;
        }
    }
    public void workb() {
        String key = "TEST_KEY";
        String identity = UUID.randomUUID().toString();
        // 最大超时时间
        int timeout = 20;
        // 新获取锁方法
        if (redisUtil.lockb(key, identity, timeout)) {
            try {
                increment();
            }catch (Exception e) {
                log.error("发生错误",e);
            }finally {
                // 新释放锁方法
                redisUtil.unlock(key, identity);
            }
        } else {
            log.info("线程：{},未获取到锁", Thread.currentThread().getName());
        }
    }

    private void increment() {
        for (int i = 0; i < TIME; i++) {
            WorkService.count++;
        }
    }
}
