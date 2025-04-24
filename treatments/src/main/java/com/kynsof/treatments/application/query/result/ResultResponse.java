package com.kynsof.treatments.application.query.result;

import com.kynsof.share.core.domain.bus.query.IResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultResponse implements IResponse {
    private UUID id;
    private String type;
    private String url;
    private String uploadedById;
    private String uploadedByUsername;
    private UUID externalConsultationId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}