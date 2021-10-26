package com.brandjunhoe.user.user.application;

import com.brandjunhoe.user.common.exception.DataNotFoundException;
import com.brandjunhoe.user.user.application.dto.UserDTO;
import com.brandjunhoe.user.user.domain.*;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

//    private final CacheManager cacheManager;


    public UserService(UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    public UserDTO activateRegistration(String key) {
        UserDTO userDTO = userRepository.findOneByActivationKey(key).map(UserDTO::new)
                .orElseThrow(() -> new DataNotFoundException("user not content"));

        userDTO.updateActivated();
        this.clearUserCaches(userDTO);

        return userDTO;
    }

    public UserDTO completePasswordReset(String newPassword, String key) {
        UserDTO userDTO = userRepository.findOneByResetKey(key)
                .filter(user -> user.getResetDate().isAfter(Instant.now().minusSeconds(86400)))
                .map(UserDTO::new)
                .orElseThrow(() -> new DataNotFoundException("user not content"));

        userDTO.changePassword(newPassword);
        this.clearUserCaches(userDTO);

        return userDTO;
    }

    public UserDTO requestPasswordReset(String mail) {

        UserDTO userDTO = userRepository.findOneByEmailIgnoreCase(mail)
                .filter(User::isActivated)
                .map(UserDTO::new)
                .orElseThrow(() -> new DataNotFoundException("user not content"));

        userDTO.resetKey();
        this.clearUserCaches(userDTO);

        return userDTO;

    }


    @Transactional(readOnly = true)
    public List<String> getAuthorities() {
        return authorityRepository.findAll().stream().map(Authority::getName).collect(Collectors.toList());
    }


    private void clearUserCaches(UserDTO user) {
       /* Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE)).evict(user.getLogin());
        if (user.getEmail() != null) {
            Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE)).evict(user.getEmail());
        }*/
    }

    @Transactional
    public void usePoints(Long userId, int latefee) {
        User user = userRepository.findById(userId).orElseThrow(() -> new DataNotFoundException("user not found"));
        user.usePoints(latefee);
    }


    public UserDTO loadUserById(Long userId) {
        return userRepository.findById(userId).map(UserDTO::new)
                .orElseThrow(() -> new DataNotFoundException("user not content"));
    }

    @Transactional
    public void savePoint(Long userId, int points) {
        User user = userRepository.findById(userId).orElseThrow(() -> new DataNotFoundException("user not found"));
        user.savePoints(points);
    }


}
