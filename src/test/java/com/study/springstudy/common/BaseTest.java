package com.study.springstudy.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
//slicing test - 이 경우는 mocking 적용이 되므로 repository 반영 안 됨.
//@WebMvcTest
@SpringBootTest //통합 테스트
@AutoConfigureMockMvc

//restdocs를 위한 설정
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)

//test 환경의 설정 값 적용
@ActiveProfiles("test")
@Ignore //test실행에서 제외하는 annotation
public class BaseTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected ModelMapper modelMapper;
}
