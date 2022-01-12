package com.rleon.HRMateChallengeBackend.application.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BycicleResponse {

    private Long id;
    private String location;
    private String model;
    private String color;
    private String photo;
    private boolean isAvailable;
    private Date dateAvailable;

}
