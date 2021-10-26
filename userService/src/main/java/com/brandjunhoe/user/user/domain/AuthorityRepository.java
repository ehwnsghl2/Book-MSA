package com.brandjunhoe.user.user.domain;


import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Create by DJH on 2021/10/11.
 */
public interface AuthorityRepository {

    List<Authority> findAll();

}
