package com.brandjunhoe.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;

/**
 * Create by DJH on 2021/10/26.
 */
@Configuration
@EnableAuthorizationServer // OAuth 인증서버의 환경 설정, OAuth 계정에 대한 데이터 위치 설정(H2) 및 JWT 토큰 사용을 위한 설정
public class AuthConfiguration extends AuthorizationServerConfigurerAdapter {


    private final ClientDetailsService clientDetailsService;
    private final AuthenticationManager authenticationManager;
    private final ResourceServerProperties resourceServerProperties;


    public AuthConfiguration(ClientDetailsService clientDetailsService, ResourceServerProperties resourceServerProperties, AuthenticationManager authenticationManager) {
        this.clientDetailsService = clientDetailsService;
        this.resourceServerProperties = resourceServerProperties;
        this.authenticationManager = authenticationManager;
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 인증 과정 endpoint에 대한 설정을 해줍니다.
        super.configure(endpoints);
        endpoints.accessTokenConverter(jwtAccessTokenConverter())
                .authenticationManager(authenticationManager);
    }

    /**
     * 인증 서버에 등록될 클라이언트 정의
     * 즉, OAuth2 서비스로 보호되는 서비스에 접근할 수 있는 클라이언트 애플리케이션 등록
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // oauth_client_details 테이블에 등록된 사용자로 조회합니다.
        clients.withClientDetails(clientDetailsService);

       /* clients.inMemory()      // 애플리케이션 정보를 위한 저장소 (인메모리 / JDBC)
                .withClient("assuapp")      // assuapp 애플리케이션이 토큰을 받기 위해 인증 서버 호출 시 제시할 시크릿과 애플리케이션명
                .secret(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("12345"))
                .authorizedGrantTypes("refresh_token", "password", "client_credentials", "authorization_code")    // OAuth2 에서 지원하는 인가 그랜트 타입, 여기선 패스워드/클라이언트 자격증명 그랜트타입
                .scopes("webclient", "mobileclient");       // 토큰 요청 시 애플리케이션의 수행 경계 정의
    }*/
    }

    // 클라이언트 대한 정보를 설정하는 부분
    // jdbc(DataBase)를 이용하는 방식
  /*  @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource).passwordEncoder(passwordEncoder);
    }*/


    /*@Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .redirectUris("http://localhost:8081/oauth2/callback")
                .authorizedGrantTypes("authorization_code")
                .accessTokenValiditySeconds(60 * 60 * 4)            // access token 유효 기간 (초 단위)
                .refreshTokenValiditySeconds(60 * 60 * 24 * 120);   // refresh token 유효 기간 (초 단위)
    }*/

    @Bean
    @Primary
    public JdbcClientDetailsService jdbcClientDetailsService(DataSource dataSource) {
        return new JdbcClientDetailsService(dataSource);
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        // JWT key-value 방식을 사용하기 위한 설정입니다.
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        // yml jwt key-value
        accessTokenConverter.setSigningKey(resourceServerProperties.getJwt().getKeyValue());

        return accessTokenConverter;
    }


}
