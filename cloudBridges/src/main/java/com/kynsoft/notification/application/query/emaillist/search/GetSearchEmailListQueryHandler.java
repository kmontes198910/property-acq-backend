package com.kynsoft.notification.application.query.emaillist.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.notification.domain.service.EmailListService;
import org.springframework.stereotype.Component;

@Component
public class GetSearchEmailListQueryHandler implements IQueryHandler<GetSearchEmailListQuery, PaginatedResponse> {

    private final EmailListService emailListService;

    public GetSearchEmailListQueryHandler(EmailListService emailListService) {
        this.emailListService = emailListService;
    }

    @Override
    public PaginatedResponse handle(GetSearchEmailListQuery query) {
        return emailListService.search(PageableUtil.createPageable(query.getSearchRequest()),query.getSearchRequest().getFilter());
    }
}
