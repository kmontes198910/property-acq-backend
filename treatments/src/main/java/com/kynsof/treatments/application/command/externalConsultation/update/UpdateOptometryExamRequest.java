package com.kynsof.treatments.application.command.externalConsultation.update;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateOptometryExamRequest {
    private UUID id; // Nuevo campo
    private String sphereOd;
    private String cylinderOd;
    private String axisOd;
    private String avscOd;
    private String avccOd;
    private String sphereOi;
    private String cylinderOi;
    private String axisOi;
    private String avscOi;
    private String avccOi;
    private String addPower;
    private String dp;
    private String dv;
    private String filter;
    @JsonProperty("isCurrent")
    private boolean isCurrent;
    private String avccAdd;
    private String sphereAdd;
    private String cylinderAdd;
    private String avscAdd;
    private String axisAdd;
}