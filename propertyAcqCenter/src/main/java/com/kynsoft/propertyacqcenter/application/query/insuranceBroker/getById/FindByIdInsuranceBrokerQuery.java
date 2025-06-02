package com.kynsoft.propertyacqcenter.application.query.insuranceBroker.getById;

import com.kynsoft.propertyacqcenter.application.query.insurance.getById.*;
import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class FindByIdInsuranceBrokerQuery implements IQuery {

    private final UUID id;
}
