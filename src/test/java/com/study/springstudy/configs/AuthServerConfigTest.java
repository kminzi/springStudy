package com.study.springstudy.configs;

import com.study.springstudy.accounts.Account;
import com.study.springstudy.accounts.AccountRole;
import com.study.springstudy.accounts.AccountService;
import com.study.springstudy.common.BaseControllerTest;
import com.study.springstudy.common.TestDescription;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthServerConfigTest extends BaseControllerTest {

    @Autowired
    AccountService accountService;

    @Test
    @TestDescription("인증 token을 발급 받는 테스트")
    public void getAuthToken() throws Exception {
        Set<AccountRole> set= new HashSet<>();
        set.add(AccountRole.ADMIN);
        set.add(AccountRole.USER);

        String username = "minji@gmail.com";
        String password = "pass";

        Account account = Account.builder()
                .email(username)
                .password(password)
                .roles(set)
                .build();

        String clientId = "myid";
        String clientSecret = "mysecret";

        accountService.saveAccount(account);

        //grant type: password
        this.mockMvc.perform(post("/oauth/token")
                    .with(httpBasic(clientId, clientSecret))
                    .param("username", username)
                    .param("password", password)
                    .param("grant_type", "password")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token").exists());
    }

}