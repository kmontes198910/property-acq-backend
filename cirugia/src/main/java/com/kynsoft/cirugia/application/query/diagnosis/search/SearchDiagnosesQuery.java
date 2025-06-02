package com.kynsoft.cirugia.application.query.diagnosis.search;

import com.kynsof.share.core.domain.bus.query.IQuery;
import com.kynsof.share.core.domain.request.FilterCriteria;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
public class SearchDiagnosesQuery implements IQuery {
    private final Pageable pageable;
    private final List<FilterCriteria> filter;
    private final String query;

    public SearchDiagnosesQuery(Pageable pageable, List<FilterCriteria> filter, String query) {
        this.pageable = pageable;
        this.filter = filter;
        this.query = query;
    }
}