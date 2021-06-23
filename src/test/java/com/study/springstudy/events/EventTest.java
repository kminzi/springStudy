package com.study.springstudy.events;

import com.study.springstudy.common.TestDescription;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;
import junitparams.JUnitParamsRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(JUnitParamsRunner.class)
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
    @TestDescription("파라미터를 적용한 무료 적용 비지니스 로직 테스트")
    @Parameters(method = "paramsForTestFree")
    public void testFree2(int basePrice, int maxPrice, boolean isFree){
        //given
        Event event = Event.builder()
                .basePrice(basePrice)
                .maxPrice(maxPrice)
                .build();

        //when
        event.update();

        //then
        assertThat(event.isFree()).isEqualTo(isFree);
    }
    private Object[] paramsForTestFree(){
        return new Object[] {
                new Object[] {0, 0, true},
                new Object[] {100, 0, false},
                new Object[] {0, 100, false},
                new Object[] {100, 200, false}
        };
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

    @Test
    @TestDescription("파라미터를 이용한 오프라인 적용 비지니스 로직 테스트")
    @Parameters({
            "kt, true",
            ", false"
    })
    public void testOffline2(String location, boolean isOffline){
        //given
        Event event = Event.builder()
                .location(location)
                .build();

        //when
        event.update();

        //then
        assertThat(event.isOffline()).isEqualTo(isOffline);
        System.out.println(event.builder().toString());
    }
}