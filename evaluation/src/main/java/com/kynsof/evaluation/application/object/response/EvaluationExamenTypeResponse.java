package com.kynsof.evaluation.application.object.response;

import com.kynsof.evaluation.domain.dto.EvaluationExamenTypeDto;
import com.kynsof.share.core.domain.bus.query.IResponse;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
public class EvaluationExamenTypeResponse implements IResponse {
    private UUID id;
    private String name;
    private LocalDateTime createdAt;

    public EvaluationExamenTypeResponse(EvaluationExamenTypeDto dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.createdAt = dto.getCreatedAt();
    }

}
