package com.brandjunhoe.user.user.application;

import com.brandjunhoe.user.user.domain.User;
import com.brandjunhoe.user.user.domain.UserRepository;
import com.brandjunhoe.user.user.infra.UserJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


}
