package com.kynsof.evaluation.application.query.evaluation.getById;

import com.kynsof.share.core.domain.bus.query.IQuery;
import com.kynsof.share.core.domain.request.FilterCriteria;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class GetByIdEvaluationQuery implements IQuery {
    private UUID id;
}
