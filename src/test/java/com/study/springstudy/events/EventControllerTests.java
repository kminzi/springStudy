package com.study.springstudy.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.springstudy.common.TestDescription;
import org.apache.tomcat.jni.Local;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
//slicing test - 이 경우는 mocking 적용이 되므로 repository 반영 안 됨.
//@WebMvcTest
@SpringBootTest //통합 테스트
@AutoConfigureMockMvc
public class EventControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

//    @MockBean
//    EventRepository eventRepository;

    @Test
    @TestDescription("정상적으로 이벤트를 생성하는 테스트")
    public void createEvent() throws Exception {
        EventDto event = EventDto.builder()
                .name("Spring")
                .description("test1")
                .beginEnrollmentDateTime(LocalDateTime.of(2021, 6, 21, 11, 40))
                .closeEnrollmentDateTime(LocalDateTime.of(2021, 6, 22, 11, 35))
                .beginEventDateTime(LocalDateTime.of(2021, 7, 1, 11, 11))
                .endEventDateTime(LocalDateTime.of(2021, 7, 2, 11, 11))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("kt")
                .build();

        //event.setId(100);
        //Mockito.when(eventRepository.save(event)).thenReturn(event);

        //post 요청을 보내는 것
        mockMvc.perform(post("/api/events/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaTypes.HAL_JSON)
                    .content(objectMapper.writeValueAsString(event))) //객체를 JSON으로 변환해서 body에 넣음
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, "application/hal+json"))
                .andExpect(jsonPath("free").value(false))
                .andExpect(jsonPath("offline").value(true))
                .andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name()));
    }

    @Test
    @TestDescription("입력 받을 수 없는 값을 사용한 경우에 에러가 발생하는 테스트")
    public void createEvent_Bad_Request() throws Exception {
        Event event = Event.builder()
                .id(100)
                .name("Spring")
                .description("test2")
                .beginEnrollmentDateTime(LocalDateTime.of(2021, 6, 21, 11, 40))
                .closeEnrollmentDateTime(LocalDateTime.of(2021, 6, 22, 11, 35))
                .beginEventDateTime(LocalDateTime.of(2021, 7, 1, 11, 11))
                .endEventDateTime(LocalDateTime.of(2021, 7, 2, 11, 11))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .free(true)
                .offline(false)
                .eventStatus(EventStatus.PUBLISHED)
                .location("kt")
                .build();

        mockMvc.perform(post("/api/events/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(event))) //객체를 JSON으로 변환해서 body에 넣음
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @TestDescription("입력 값이 비어있는 경우에 에러가 발생하는 테스트")
    public void createEvent_Bad_Request_Empty_Input() throws Exception {
        EventDto eventDto = EventDto.builder().build();

        this.mockMvc.perform(post("/api/events")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(this.objectMapper.writeValueAsString(eventDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @TestDescription("입력 값이 잘못된 경우에 에러가 발생하는 테스트")
    public void createEvent_Bad_Request_Wrong_Input() throws Exception {
        EventDto eventDto = EventDto.builder()
                .name("Spring")
                .description("test3")
                .beginEnrollmentDateTime(LocalDateTime.of(2021, 6, 23, 11, 40))
                .closeEnrollmentDateTime(LocalDateTime.of(2021, 6, 22, 11, 35))
                .beginEventDateTime(LocalDateTime.of(2021, 7, 1, 11, 11))
                .endEventDateTime(LocalDateTime.of(2021, 5, 2, 11, 11))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("kt")
                .build();

        this.mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(eventDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].objectName").exists())
                .andExpect(jsonPath("$[0].field").exists())
                .andExpect(jsonPath("$[0].defaultMessage").exists())
        ;
    }

}
