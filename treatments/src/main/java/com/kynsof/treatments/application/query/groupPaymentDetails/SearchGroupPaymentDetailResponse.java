package com.kynsof.treatments.application.query.groupPaymentDetails;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsof.treatments.domain.dto.GroupPaymentDetailDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class SearchGroupPaymentDetailResponse implements IResponse {
    private UUID id;
    private String code;
    private String description;
    private Double cost;
    private LocalDateTime createdAt;


    public SearchGroupPaymentDetailResponse(GroupPaymentDetailDto aggregate) {
        this.id = aggregate.getId();
        this.code = aggregate.getCode();
        this.description = aggregate.getDescription();
        this.cost = aggregate.getCost();
        this.createdAt = aggregate.getCreatedAt();
    }
}
