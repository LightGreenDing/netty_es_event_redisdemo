package com.example.demo.observer;

import com.example.demo.observer.service.SimpleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserController {
    @Autowired
    UserRegisterSubject subject;

    @Autowired
    private SimpleUserService userService;


    @GetMapping("/reg")
    public String reg(String username) {
        userService.register(username);
        subject.notifyObservers(username);
        return "success";
    }
}
