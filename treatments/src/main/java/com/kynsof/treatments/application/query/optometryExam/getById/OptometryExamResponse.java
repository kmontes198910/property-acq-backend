package com.kynsof.treatments.application.query.optometryExam.getById;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsof.treatments.domain.dto.OptometryExamDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class OptometryExamResponse implements IResponse {
    private UUID id;
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
    private String avccAdd;
    private String sphereAdd;
    private String cylinderAdd;

    public OptometryExamResponse(OptometryExamDto dto) {
        this.id = dto.getId();
        this.sphereOd = dto.getSphereOd();
        this.cylinderOd = dto.getCylinderOd();
        this.axisOd = dto.getAxisOd();
        this.avscOd = dto.getAvscOd();
        this.avccOd = dto.getAvccOd();
        this.sphereOi = dto.getSphereOi();
        this.cylinderOi = dto.getCylinderOi();
        this.axisOi = dto.getAxisOi();
        this.avscOi = dto.getAvscOi();
        this.avccOi = dto.getAvccOi();
        this.addPower = dto.getAddPower();
        this.dp = dto.getDp();
        this.dv = dto.getDv();
        this.filter = dto.getFilter();
        this.avccAdd = dto.getAvccAdd();
        this.sphereAdd = dto.getSphereAdd();
        this.cylinderAdd = dto.getCylinderAdd();
    }
}