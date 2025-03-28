package com.kynsof.calendar.application.command.schedule.create;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Setter
@Getter
public class CreateScheduleRequest {
    private UUID resource;
    private UUID business;
    private UUID service;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endingTime;
    private int stock;
    private UUID user;
    private boolean blocked;
}