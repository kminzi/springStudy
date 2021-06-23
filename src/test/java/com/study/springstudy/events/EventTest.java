package com.study.springstudy.events;

import com.study.springstudy.common.TestDescription;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EventTest {

    @Test
    public void builder(){
        Event event = Event.builder()
                .name("Spring REST API")
                .description("test0617")
                .build();
        assertThat(event).isNotNull();
    }

    @Test
    public void javaBean(){
        //given
        String name = "Event";
        String description = "Spring";

        //when
        Event event = new Event();
        event.setName(name);
        event.setDescription(description);

        //then
        assertThat(event.getName()).isEqualTo(name);
        assertThat(event.getDescription()).isEqualTo(description);
    }

    @Test
    @TestDescription("무료 적용 비지니스 로직 테스트")
    public void testFree(){
        //given
        Event event = Event.builder()
                    .basePrice(100)
                    .maxPrice(0)
                    .build();

        //when
        event.update();

        //then
        assertThat(event.isFree()).isFalse();
        System.out.println(event);
    }

    @Test
    @TestDescription("오프라인 적용 비지니스 로직 테스트")
    public void testOffline(){
        //given
        Event event = Event.builder()
                    .location("kt")
                    .build();

        //when
        event.update();

        //then
        assertThat(event.isOffline()).isTrue();
        System.out.println(event.builder().toString());

        //given
        event = Event.builder()
                .build();

        //when
        event.update();

        //then
        assertThat(event.isOffline()).isFalse();
        System.out.println(event.builder().toString());
    }
}