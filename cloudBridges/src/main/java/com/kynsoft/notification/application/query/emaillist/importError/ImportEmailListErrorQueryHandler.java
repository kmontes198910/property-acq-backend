package com.kynsoft.notification.application.query.emaillist.importError;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.notification.domain.service.ImportEmailListService;
import org.springframework.stereotype.Component;

@Component
public class ImportEmailListErrorQueryHandler implements IQueryHandler<ImportEmailListErrorQuery,PaginatedResponse> {
    private final ImportEmailListService importEmailListService;

    public ImportEmailListErrorQueryHandler(ImportEmailListService importEmailListService) {
        this.importEmailListService = importEmailListService;
    }

    @Override
    public PaginatedResponse handle(ImportEmailListErrorQuery query) {
        return importEmailListService.getImportEmailListErrors(query.getRequest());
    }
}
