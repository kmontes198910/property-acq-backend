package com.kynsof.patients.application.query.dashboard.countPatient;


import com.kynsof.share.core.domain.bus.query.IResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountPatientResponse implements IResponse {
    private Long countPatient;


    public CountPatientResponse(Long aLong) {
        this.countPatient = aLong;
    }

}