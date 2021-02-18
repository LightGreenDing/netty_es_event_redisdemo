package com.example.demo.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/event")
public class UserEventController {
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String register(HttpServletRequest request, String username){
        String id = request.getSession().getId();
        userService.register(username);
        return "恭喜注册成功!";
    }
}
