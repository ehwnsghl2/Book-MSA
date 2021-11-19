package com.brandjunhoe.user.user.presentation;

import com.brandjunhoe.user.user.application.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * Create by DJH on 2021/09/15.
 */
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @Value("${server.port}")
    private int port;

    @Value("${spring.message}")
    private String message;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public String userController() {
        return "UserController";
    }

    @GetMapping("/detail")
    public String contents() {
        return "User Detail - Port " + port + " - " + message;
    }

}
