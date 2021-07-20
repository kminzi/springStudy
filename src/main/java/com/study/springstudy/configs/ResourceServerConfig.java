package com.study.springstudy.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
//resource 접근을 할 때 인증을 하는 것
//토큰이 유효한지 확인하는 역할, 즉 접근제어
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("event");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .anonymous().and()
                .authorizeRequests()
                .mvcMatchers(HttpMethod.GET, "/api/**").permitAll() //anonymous로 하면 인증한 사용자는 접근 불가
                .anyRequest().authenticated().and()
                .exceptionHandling()//인증이 잘못되거나 권한이 없는 경우
                .accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }
}
