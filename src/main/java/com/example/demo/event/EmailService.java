package com.example.demo.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 实现ApplicationListener<Event>的方式
 * 发送邮件
 */
@Service
@Slf4j
public class EmailService implements ApplicationListener<UserRegisterEvent> {
    /**
     * 监听用户注册事件, 异步发送执行发送邮件逻辑
     */
    @Override
    @Async
    public void onApplicationEvent(UserRegisterEvent event) {
        log.info("给用户[{}]发送邮件", event.getUsername());
    }
}
