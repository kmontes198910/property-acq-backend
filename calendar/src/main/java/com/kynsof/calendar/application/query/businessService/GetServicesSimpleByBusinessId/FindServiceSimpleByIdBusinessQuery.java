package com.kynsof.calendar.application.query.businessService.GetServicesSimpleByBusinessId;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@Getter
public class FindServiceSimpleByIdBusinessQuery implements IQuery {

    private final UUID id;
    private final Pageable pageable;
    private final String serviceName;

    public FindServiceSimpleByIdBusinessQuery(UUID id, Pageable pageable) {
        this.id = id;
        this.pageable = pageable;
        this.serviceName = null;
    }
    
    public FindServiceSimpleByIdBusinessQuery(UUID id, Pageable pageable, String serviceName) {
        this.id = id;
        this.pageable = pageable;
        this.serviceName = serviceName;
    }

}
