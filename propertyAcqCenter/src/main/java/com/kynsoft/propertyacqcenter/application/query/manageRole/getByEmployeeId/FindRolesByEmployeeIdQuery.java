package com.kynsoft.propertyacqcenter.application.query.manageRole.getByEmployeeId;

import com.kynsoft.propertyacqcenter.application.query.purchase.getByPropertyId.*;
import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FindRolesByEmployeeIdQuery implements IQuery {

    private final String id;
}
