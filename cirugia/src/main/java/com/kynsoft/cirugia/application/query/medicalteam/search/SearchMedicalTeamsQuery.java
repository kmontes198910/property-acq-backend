package com.kynsoft.cirugia.application.query.medicalteam.search;

import com.kynsof.share.core.domain.bus.query.IQuery;
import com.kynsof.share.core.domain.request.FilterCriteria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchMedicalTeamsQuery implements IQuery {
    private Pageable pageable;
    private List<FilterCriteria> filters;
    private String query;
}