package com.vlc.application.operation;

import com.vlc.application.resources.entity.User;
import com.vlc.application.resources.entity.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by prageethmahendra on 27/10/2016.
 */
@Controller
public class AuthHandler {

    @Autowired
    UserRepository userRepository;

    public User login(String userName, String password) {
        if (userName == null || password == null ||
                userName.trim().isEmpty() || password.trim().isEmpty()) {
            return null;
        } else {
            if (userRepository == null) {
                return null;
            }
            List<User> users = userRepository.findByUserPassword(userName, password);
            if (users.isEmpty()) {
                return null;
            } else {
                return users.get(0);
            }
        }
    }

    @PostConstruct
    public void init() {
        User employee = new User();
        employee.setUserName("Prageeth");
        employee.setPassword("Prageeth321");
        employee.setAuthorized(true);
        userRepository.save(employee);
    }
}
