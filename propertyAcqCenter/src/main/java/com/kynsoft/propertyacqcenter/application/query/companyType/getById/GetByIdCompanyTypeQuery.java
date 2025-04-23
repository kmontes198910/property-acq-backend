package com.kynsoft.propertyacqcenter.application.query.companyType.getById;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class GetByIdCompanyTypeQuery implements IQuery {
    private UUID id;
}
