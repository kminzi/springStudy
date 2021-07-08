package com.study.springstudy.accounts;

import com.study.springstudy.common.TestDescription;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AccountServiceTest {
    //service테스트이므로 controller를 사용할 이유가 없음. 따라서 basecontroller 상속 x

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

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

    @Test(expected = UsernameNotFoundException.class)
    @TestDescription("존재하지 않는 user의 이메일로 조회하는 경우 - expected 사용")
    public void findByUsernameFail1(){
        String email = "error";
        accountService.loadUserByUsername(email);
    }

    @Test
    @TestDescription("존재하지 않는 user의 이메일로 조회하는 경우 - try catch 사용")
    public void findByUsernameFail2(){
        String email = "error";
        try {
            accountService.loadUserByUsername(email);
            fail("supposed to be failed");
        }catch (UsernameNotFoundException e){
            assertThat(e.getMessage()).containsSequence(email);
        }
    }

    @Test
    @TestDescription("존재하지 않는 user의 이메일로 조회하는 경우 - @rule ExpectedException 사용")
    public void findByUsernameFail3(){
        //Expected
        //기대하는 예외를 먼저 써줘야 정상 동작함
        String email = "error";
        expectedException.expect(UsernameNotFoundException.class);
        expectedException.expectMessage(Matchers.containsString(email));

        //when
        accountService.loadUserByUsername(email);
    }

}