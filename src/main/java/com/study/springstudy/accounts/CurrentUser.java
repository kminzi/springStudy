package com.study.springstudy.accounts;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) //parameter에 붙일 수 있고,
@Retention(RetentionPolicy.RUNTIME) //runtime까지 유지할 것,
@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : account") // 필요한 속성 입력(Null or getAccount사용)
//userdetail -> user -> account adapter -> account 과정을 담음
public @interface CurrentUser {
}
