package com.study.springstudy.events;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

public class EventResource extends RepresentationModel{

    //해당 annotation을 사용하면 event에 감싸서 나오지 않음(기본으로는 Bean Serialize)
    @JsonUnwrapped
    private Event event;

    public EventResource(Event event){
        this.event = event;
    }

    public Event getEvent(){
        return event;
    }
}
