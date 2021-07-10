package com.study.springstudy.configs;

import com.study.springstudy.accounts.Account;
import com.study.springstudy.accounts.AccountRepository;
import com.study.springstudy.accounts.AccountRole;
import com.study.springstudy.accounts.AccountService;
import com.study.springstudy.common.AppProperties;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        //encoding 앞에 prefix를 붙여주는 encoder
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    //app이 구동될 때 기본적으로 계정을 하나 생성하게 됨
    public ApplicationRunner applicationRunner(){
        return new ApplicationRunner() {

            @Autowired
            AccountService accountService;

            @Autowired
            AppProperties appProperties;

            @Override
            public void run(ApplicationArguments args) throws Exception {
                Account admin = Account.builder()
                        .email(appProperties.getAdminUsername())
                        .password(appProperties.getAdminPassword())
                        .roles(Collections.singleton(AccountRole.ADMIN))
                        .build();
                accountService.saveAccount(admin);

                Account user = Account.builder()
                        .email(appProperties.getUserUsername())
                        .password(appProperties.getUserPassword())
                        .roles(Collections.singleton(AccountRole.USER))
                        .build();
                accountService.saveAccount(user);
            }
        };
    }
}
