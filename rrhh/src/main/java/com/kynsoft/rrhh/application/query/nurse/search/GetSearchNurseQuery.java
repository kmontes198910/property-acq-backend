package com.kynsoft.rrhh.application.query.nurse.search;

import com.kynsof.share.core.domain.bus.query.IQuery;
import com.kynsof.share.core.domain.request.FilterCriteria;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@Setter
public class GetSearchNurseQuery implements IQuery {
    private Pageable pageable;
    private List<FilterCriteria> filterCriteria;

    public GetSearchNurseQuery(Pageable pageable, List<FilterCriteria> filterCriteria) {
        this.pageable = pageable;
        this.filterCriteria = filterCriteria;
    }
}