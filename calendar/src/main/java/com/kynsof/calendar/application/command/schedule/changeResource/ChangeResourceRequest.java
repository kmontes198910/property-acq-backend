package com.kynsof.calendar.application.command.schedule.changeResource;

import com.kynsof.calendar.domain.dto.enumType.EStatusSchedule;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
public class ChangeResourceRequest {
    private UUID resource;
    private UUID scheduledId;
}