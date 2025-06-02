package com.kynsoft.cirugia.application.query.treatment.search;

import com.kynsof.share.core.domain.bus.query.IQuery;
import com.kynsof.share.core.domain.request.FilterCriteria;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@Setter
public class SearchTreatmentsQuery implements IQuery {
    private final Pageable pageable;
    private final List<FilterCriteria> filterCriteria;
    
    public SearchTreatmentsQuery(Pageable pageable, List<FilterCriteria> filterCriteria) {
        this.pageable = pageable;
        this.filterCriteria = filterCriteria;
    }
}