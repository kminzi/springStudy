package com.study.springstudy.configs;

import com.study.springstudy.accounts.AccountService;
import com.study.springstudy.common.AppProperties;
import com.study.springstudy.common.BaseTest;
import com.study.springstudy.common.TestDescription;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthServerConfigTest extends BaseTest {

    @Autowired
    AccountService accountService;

    @Autowired
    AppProperties appProperties;

    @Test
    @TestDescription("인증 token을 발급 받는 테스트")
    public void getAuthToken() throws Exception {

        //given - appConfig를 통해 기본 사용자 저장
//        Set<AccountRole> set= new HashSet<>();
//        set.add(AccountRole.ADMIN);
//        set.add(AccountRole.USER);
//
//        Account account = Account.builder()
//                .email(appProperties.getUserUsername())
//                .password(appProperties.getUserPassword())
//                .roles(set)
//                .build();
//        accountService.saveAccount(account);

        //when&then
        //grant type: password
        this.mockMvc.perform(post("/oauth/token")
                    .with(httpBasic(appProperties.getClientId(), appProperties.getClientSecret()))
                    .param("username", appProperties.getUserUsername())
                    .param("password", appProperties.getUserPassword())
                    .param("grant_type", "password")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token").exists());
    }

}