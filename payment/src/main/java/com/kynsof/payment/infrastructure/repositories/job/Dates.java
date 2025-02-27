package com.kynsof.payment.infrastructure.repositories.job;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
public class Dates {
    private Date startDate;
    private Date endDate;
}
