package com.brandjunhoe.gateway;

import com.brandjunhoe.gateway.filter.GatewayErrorFilter;
import com.brandjunhoe.gateway.filter.GatewayPostFilter;
import com.brandjunhoe.gateway.filter.GatewayPreFilter;
import com.brandjunhoe.gateway.filter.GatewayRouteFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@EnableZuulProxy
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public GatewayPreFilter preFilter(){
        return new GatewayPreFilter();
    }

    @Bean
    public GatewayPostFilter postFilter() {
        return new GatewayPostFilter();
    }

    @Bean
    public GatewayRouteFilter routeFilter() {
        return new GatewayRouteFilter();
    }

    @Bean
    public GatewayErrorFilter errorFilter() {
        return new GatewayErrorFilter();
    }

}
