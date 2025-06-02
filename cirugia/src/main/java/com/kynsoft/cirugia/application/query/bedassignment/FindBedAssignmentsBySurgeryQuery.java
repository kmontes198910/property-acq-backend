package com.kynsoft.cirugia.application.query.bedassignment;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindBedAssignmentsBySurgeryQuery implements IQuery {
    private UUID surgeryId;
    private UUID businessId;
}
