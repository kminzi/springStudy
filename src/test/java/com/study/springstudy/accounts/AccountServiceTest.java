package com.study.springstudy.accounts;

import com.study.springstudy.common.TestDescription;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AccountServiceTest {
    //service테스트이므로 controller를 사용할 이유가 없음. 따라서 basecontroller 상속 x

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Test
    @TestDescription("이름으로 계정 검색")
    public void findByUsername(){
        //given
        Set<AccountRole> set= new HashSet<>();
        set.add(AccountRole.ADMIN);
        set.add(AccountRole.USER);
        String password = "password";
        String email = "minji@gmail.com";

        Account account = Account.builder()
                .email(email)
                .password(password)
                .roles(set)
                .build();
        this.accountRepository.save(account);

        //when
        UserDetailsService userDetailsService = (UserDetailsService)accountService;
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        //then
        assertThat(userDetails.getPassword()).isEqualTo(password);
        System.out.println(userDetails.getAuthorities());
    }
}