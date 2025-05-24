package com.kynsoft.medicaltest.application.query.labtestparameter.search;

import com.kynsof.share.core.domain.bus.query.IQuery;
import com.kynsof.share.core.domain.request.FilterCriteria;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Consulta para buscar parámetros de exámenes de laboratorio con filtros
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchLabTestParameterQuery implements IQuery {
    private Pageable pageable;
    private List<FilterCriteria> filter;
    private String query;
}
