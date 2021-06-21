package com.study.springstudy.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder @NoArgsConstructor @AllArgsConstructor
public class EventDto {

    //notnull:null만 안되는 것, notempty:null, ""도 안되는 것
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @NotNull
    private LocalDateTime beginEnrollmentDateTime;
    @NotNull
    private LocalDateTime closeEnrollmentDateTime;
    @NotNull
    private LocalDateTime beginEventDateTime;
    @NotNull
    private LocalDateTime endEventDateTime;
    private String location; //(option)
    @Min(0)
    private int basePrice; //(option)
    @Min(0)
    private int maxPrice; //(option)
    @Min(0)
    private int limitOfEnrollment;
}
