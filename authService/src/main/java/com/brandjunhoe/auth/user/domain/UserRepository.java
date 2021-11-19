package com.brandjunhoe.auth.user.domain;


import java.util.Optional;

/**
 * Create by DJH on 2021/10/26.
 */
public interface UserRepository {

    Optional<User> findByUsername(String username);

    User save(User user);
}
