package com.brandjunhoe.user.user.infra;

import com.brandjunhoe.user.user.domain.Authority;
import com.brandjunhoe.user.user.domain.AuthorityRepository;
import com.brandjunhoe.user.user.domain.User;
import com.brandjunhoe.user.user.domain.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityJpaRepository extends JpaRepository<Authority, Long>, AuthorityRepository {

}
