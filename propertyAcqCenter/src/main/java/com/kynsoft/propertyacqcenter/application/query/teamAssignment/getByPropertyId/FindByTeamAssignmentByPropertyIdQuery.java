package com.kynsoft.propertyacqcenter.application.query.teamAssignment.getByPropertyId;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FindByTeamAssignmentByPropertyIdQuery implements IQuery {

    private final String id;
}
