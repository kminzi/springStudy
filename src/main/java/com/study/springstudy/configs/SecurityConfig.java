package com.study.springstudy.configs;

import com.study.springstudy.accounts.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration //bean 설정파일
@EnableWebSecurity //이 annotation으로 인해 spring boot가 제공하는 기본 spring security설정은 적용되지 않음
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AccountService accountService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    //oauth token을 저장하는 저장소
    public TokenStore tokenStore(){
        return new InMemoryTokenStore();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    //authenticationManager 만들기
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    //security filter 적용에 대한 설정
    public void configure(WebSecurity web) throws Exception {
        //정적 리소스에 대해서는 web에서 먼저 처리해주는게 부하가 조금 작다는 장점이 있음
        web.ignoring().mvcMatchers("/docs/index.html");
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations()); //정적리소스에 대한 적용 제거
    }

    @Override
    //이건 spring security 안으로는 들어오고 그 안에서 걸러지는 방식(websecurity -> httpsecurity)
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .mvcMatchers("/docs/index.html").anonymous()
//                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).anonymous();

        http.anonymous().and()
                .formLogin().and()
                .authorizeRequests().mvcMatchers(HttpMethod.GET, "/api/**").anonymous()
                                    .anyRequest().authenticated();
    }
}
