package com.study.springstudy.events;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;

@Component
public class EventValidator {
    public void validate(EventDto eventDto, Errors errors){
        if (eventDto.getBasePrice() > eventDto.getMaxPrice() && eventDto.getMaxPrice() != 0){
            errors.rejectValue("basePrice","wrongValue","Basefield Wrong");
            errors.rejectValue("maxPrice","wrongValue","Maxfield Wrong");
        }

        LocalDateTime endEventDateTime = eventDto.getEndEventDateTime();
        if(endEventDateTime.isBefore(eventDto.getBeginEventDateTime())||
        endEventDateTime.isBefore(eventDto.getBeginEnrollmentDateTime())||
        endEventDateTime.isBefore(eventDto.getCloseEnrollmentDateTime())){
            errors.rejectValue("endEventDateTime", "wrongValue", "dateTime Wrong");
        }

        //TODO beginEventDateTime
        //TODO CloseEnrollmentDateTime
    }
}
