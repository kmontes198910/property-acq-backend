package com.kynsof.treatments.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OptometryExamDto {
    private UUID id;
    private  String sphereOd;
    private  String cylinderOd;
    private  String axisOd;
    private  String avscOd;
    private  String avccOd;
    private  String sphereOi;
    private  String cylinderOi;
    private  String axisOi;
    private  String avscOi;
    private  String avccOi;
    private  String addPower;
    private  String dp;
    private  String dv;
    private  String filter;
    private  boolean isCurrent;
    private String avccAdd;
    private String sphereAdd;
    private String cylinderAdd;
    private String avscAdd;
    private String axisAdd;
    private Integer orderNumber;

}