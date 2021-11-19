package com.brandjunhoe.auth.user.infra;


import com.brandjunhoe.auth.user.domain.User;
import com.brandjunhoe.auth.user.domain.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Create by DJH on 2021/10/26.
 */
public interface UserJpaRepository extends JpaRepository<User, Long>, UserRepository {

}
