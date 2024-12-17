package com.kynsof.treatments.application.command.externalConsultation.update;

import com.kynsof.treatments.application.command.externalConsultation.create.ExamAllRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UpdateExamOrderAllRequest {
    private UUID id;
    private String reason;
    private List<ExamAllRequest> exams;
}
