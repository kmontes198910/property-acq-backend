package com.kynsoft.notification.application.query.file.countbypath;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.Getter;

import java.util.UUID;

@Getter
public class GetFileCountByPathQuery implements IQuery {
    private final UUID businessId;

    public GetFileCountByPathQuery(UUID businessId) {
        this.businessId = businessId;
    }
}