package com.kynsoft.cirugia.application.query.recoveryroom.search;

import com.kynsof.share.core.domain.bus.query.IQuery;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
public class SearchRecoveryRoomsQuery implements IQuery{
    private final Pageable pageable;
    private final List<FilterCriteria> filter;
    private final String query;

    public SearchRecoveryRoomsQuery(SearchRequest searchRequest) {
        this.pageable = PageableUtil.createPageable(searchRequest);
        this.filter = searchRequest.getFilter();
        this.query = searchRequest.getQuery();
    }
}