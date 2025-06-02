package com.kynsoft.cirugia.application.query.recoveryroom.getbyid;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetRecoveryRoomByIdQuery implements IQuery {
    private UUID id;
}