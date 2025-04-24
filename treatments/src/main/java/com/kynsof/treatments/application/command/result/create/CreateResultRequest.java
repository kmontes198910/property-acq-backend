package com.kynsof.treatments.application.command.result.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateResultRequest {
    private String type;
    private UUID externalConsultationId;
    private String base64Content;
    private String fileName;
}