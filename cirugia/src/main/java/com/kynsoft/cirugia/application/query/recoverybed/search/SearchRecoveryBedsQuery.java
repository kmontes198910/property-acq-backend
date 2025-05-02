package com.kynsoft.cirugia.application.query.recoverybed.search;

import com.kynsof.share.core.domain.bus.query.IQuery;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
public class SearchRecoveryBedsQuery implements IQuery {
    private final Pageable pageable;
    private final List<FilterCriteria> filterCriteria;
    
    public SearchRecoveryBedsQuery(SearchRequest request) {
        this.pageable = PageableUtil.createPageable(request);
        this.filterCriteria = request.getFilter();
    }
}