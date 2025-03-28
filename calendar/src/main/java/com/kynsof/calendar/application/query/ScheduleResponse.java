package com.kynsof.calendar.application.query;

import com.kynsof.calendar.application.query.service.ServicesResponse;
import com.kynsof.calendar.domain.dto.ScheduleDto;
import com.kynsof.calendar.domain.dto.enumType.EStatusSchedule;
import com.kynsof.share.core.domain.bus.query.IResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ScheduleResponse implements IResponse {

    private UUID id;
    private ResourceResponse resource;
    private BusinessResponse business;
    private ServicesResponse service;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endingTime;
    private int stock;
    private EStatusSchedule status;
    private boolean blocked;

    public ScheduleResponse(ScheduleDto schedule) {
        this.id = schedule.getId();
        this.resource = new ResourceResponse(schedule.getResource());
        this.business = new BusinessResponse(schedule.getBusiness());
        this.date = schedule.getDate();
        this.startTime = schedule.getStartTime();
        this.endingTime = schedule.getEndingTime();
        this.stock = schedule.getStock();
        this.status = schedule.getStatus();
        this.service = new ServicesResponse(schedule.getService());
        this.blocked = schedule.getStatus().equals(EStatusSchedule.BLOCKED);
    }

}
