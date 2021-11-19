package com.brandjunhoe.auth.user.application;

import com.brandjunhoe.auth.user.domain.User;
import com.brandjunhoe.auth.user.domain.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by DJH on 2021/10/26.
 */
@Service
public class UserInformationService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserInformationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("wrongId"));

        return makeLoginUser(user);
    }

    public UserInformation makeLoginUser(User user) {
        UserInformation loginUser = new UserInformation();

        List<GrantedAuthority> Authoritylist = new ArrayList<>();

        switch (user.getUserType()) {
            case 0:
                // admin
                Authoritylist.add(new SimpleGrantedAuthority("ADMIN"));
            case 1:
                Authoritylist.add(new SimpleGrantedAuthority("USER"));
                break;
        }

        loginUser.setUsername(user.getUsername());
        loginUser.setPassword(user.getPassword());
        loginUser.setAuthorities(Authoritylist);

        return loginUser;

    }


}
