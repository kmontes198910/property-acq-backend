package com.kynsoft.cirugia.application.query.evolution.search;

import com.kynsof.share.core.domain.bus.query.IQuery;
import com.kynsof.share.core.domain.request.FilterCriteria;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@Setter
public class SearchEvolutionsQuery implements IQuery {
    private final Pageable pageable;
    private final List<FilterCriteria> filterCriteria;
    
    public SearchEvolutionsQuery(Pageable pageable, List<FilterCriteria> filterCriteria) {
        this.pageable = pageable;
        this.filterCriteria = filterCriteria;
    }
}