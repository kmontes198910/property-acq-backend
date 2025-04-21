package com.kynsoft.notification.application.query.file.countbypath;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.notification.domain.service.IAFileService;
import org.springframework.stereotype.Component;

@Component
public class GetFileCountByPathQueryHandler implements IQueryHandler<GetFileCountByPathQuery, FileCountByPathResponse> {

    private final IAFileService serviceImpl;

    public GetFileCountByPathQueryHandler(IAFileService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public FileCountByPathResponse handle(GetFileCountByPathQuery query) {
        return this.serviceImpl.countByPath(query.getBusinessId());
    }
}