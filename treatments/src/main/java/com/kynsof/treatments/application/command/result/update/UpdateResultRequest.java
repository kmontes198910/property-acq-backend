package com.kynsof.treatments.application.command.result.update;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateResultRequest {
    private UUID id;
    private String type;
    private String url;
    private String uploadedById;
    private String uploadedByUsername;
    private UUID externalConsultationId;
}