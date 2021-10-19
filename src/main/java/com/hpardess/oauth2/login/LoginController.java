package com.hpardess.oauth2.login;

import com.hpardess.oauth2.user.User;
import com.hpardess.oauth2.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/login")
public class LoginController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    User login(@RequestParam String name, @RequestParam String email, @RequestParam String password) {
        User user = new User(null, email, name);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(User.Role.USER);
        user.setActive(true);

        return userService.save(user);
    }

    @PostMapping("/validateEmail")
    Boolean emailExists(@RequestParam String email) {
        return userService.existsByEmail(email);
    }
}
