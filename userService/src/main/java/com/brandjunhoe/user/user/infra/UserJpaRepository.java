package com.brandjunhoe.user.user.infra;

import com.brandjunhoe.user.user.domain.User;
import com.brandjunhoe.user.user.domain.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long>, UserRepository {

}
