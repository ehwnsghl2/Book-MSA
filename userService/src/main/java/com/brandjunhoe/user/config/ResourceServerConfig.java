package com.brandjunhoe.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * Create by DJH on 2021/10/27.
 */

// 해당 ResourceServer설정을 통해 인증정보에 따라 request 처리 여부를 결정해 줄 수 있습니다.
@EnableResourceServer
@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http.authorizeRequests()
                // / URL 접근시 'read'oauth2 scope가 있어야지 접근이 가능하도록 설정
                // 토큰없이 ㅈ버근시 접근이 제한됨
                .antMatchers("/").access("#oauth2.hasScope('read')")
                .anyRequest().authenticated();
    }

}
