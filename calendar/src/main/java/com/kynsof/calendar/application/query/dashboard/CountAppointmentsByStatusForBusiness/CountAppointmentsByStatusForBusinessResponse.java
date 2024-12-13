package com.kynsof.calendar.application.query.dashboard.CountAppointmentsByStatusForBusiness;

import com.kynsof.share.core.domain.bus.query.IResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
public class CountAppointmentsByStatusForBusinessResponse implements IResponse {
    private List<Map<String, Object>> result;

}