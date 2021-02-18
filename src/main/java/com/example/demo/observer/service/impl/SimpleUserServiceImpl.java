package com.example.demo.observer.service.impl;

import com.example.demo.observer.service.SimpleUserService;
import org.springframework.stereotype.Service;

@Service
public class SimpleUserServiceImpl implements SimpleUserService {
    @Override
    public void register(String username) {
        System.out.println("注册");
    }
}
