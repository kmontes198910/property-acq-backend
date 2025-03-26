package com.kynsof.calendar.domain.dto;

import com.kynsof.calendar.application.query.service.ServicesResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessServicePriceResponse implements Serializable {
    private Double price;
    private ServicesResponse service;
}
