package com.brandjunhoe.user.user.domain;


import java.util.List;
import java.util.Optional;

/**
 * Create by DJH on 2021/10/11.
 */
public interface UserRepository {

    String USERS_BY_LOGIN_CACHE = "usersByLogin";

    String USERS_BY_EMAIL_CACHE = "usersByEmail";

    Optional<User> findOneByActivationKey(String key);

    Optional<User> findOneByResetKey(String key);

    Optional<User> findOneByEmailIgnoreCase(String mail);

    Optional<User> findById(Long userId);
}
