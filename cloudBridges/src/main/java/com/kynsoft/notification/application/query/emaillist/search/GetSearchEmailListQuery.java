package com.kynsoft.notification.application.query.emaillist.search;

import com.kynsof.share.core.domain.bus.query.IQuery;
import com.kynsof.share.core.domain.request.SearchRequest;
import lombok.Getter;

@Getter
public class GetSearchEmailListQuery implements IQuery {
    private final SearchRequest searchRequest;

    public GetSearchEmailListQuery(SearchRequest searchRequest) {
        this.searchRequest = searchRequest;
    }
}
