package com.example.demo.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService implements ApplicationEventPublisherAware {
    // 注入事件发布者
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
    /**
     * 发布事件
     */
    public void register(String username) {
        log.info("执行用户[{}]的注册逻辑", username);
        applicationEventPublisher.publishEvent(new UserRegisterEvent(this, username));
    }
}
