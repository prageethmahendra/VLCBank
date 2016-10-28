package com.vlc.application.controllers;

import com.vlc.application.resources.entity.User;
import com.vlc.application.resources.entity.UserRepository;
import com.vlc.application.operation.AuthHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by prageethmahendra on 27/10/2016.
 */
@Controller
public class LoginController {

    @Autowired
    AuthHandler authHandler;

    @Autowired
    UserRepository userRepository;

    @RequestMapping("/login")
    public
    @ResponseBody
    User login(@RequestParam(value = "userName", required = false, defaultValue = "") String userName,
               @RequestParam(value = "password", required = false, defaultValue = "") String password) {//
        User user = authHandler.login(userName, password);
        if (user == null) {
            System.out.println("user = " + userName);
            return null;
        } else {
            System.out.println("user.getUserName() = " + user.getUserName());
        }
        return user;
    }

}
