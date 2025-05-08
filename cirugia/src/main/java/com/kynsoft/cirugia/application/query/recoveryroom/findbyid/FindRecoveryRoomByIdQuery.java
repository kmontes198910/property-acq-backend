package com.kynsoft.cirugia.application.query.recoveryroom.findbyid;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class FindRecoveryRoomByIdQuery implements IQuery {
    private final UUID id;
}