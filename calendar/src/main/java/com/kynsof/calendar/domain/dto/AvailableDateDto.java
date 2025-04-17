package com.kynsof.calendar.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AvailableDateDto implements Serializable {
    private LocalDate date;
    private List<AvailableTimeSlotDto> timeSlots;
}
