package com.example.demo.observer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailObserver implements Observer{
    @Override
    public void update(String message) {
        log.info("向[{}]发送邮件", message);
    }
}
