package com.kynsof.treatments.application.command.result.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.treatments.domain.dto.ExternalConsultationDto;
import com.kynsof.treatments.domain.dto.ResultDto;
import com.kynsof.treatments.domain.service.IExternalConsultationService;
import com.kynsof.treatments.domain.service.IResultFileUploadService;
import com.kynsof.treatments.domain.service.IResultService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
public class CreateResultCommandHandler implements ICommandHandler<CreateResultCommand> {

    private final IResultService resultService;
    private final IExternalConsultationService externalConsultationService;
    private final IResultFileUploadService resultFileUploadService;

    @Override
    public void handle(CreateResultCommand command) {
        UUID resultId = UUID.randomUUID();
        command.setId(resultId);
        
        ExternalConsultationDto externalConsultation = externalConsultationService
                .findById(command.getExternalConsultationId());
        
        // Si hay contenido base64 para cargar, llamamos al servicio de carga de archivos
        if (command.getBase64Content() != null && !command.getBase64Content().isEmpty()) {
            String fileUrl = resultFileUploadService.uploadFile(
                command.getBase64Content(),
                command.getFileName(),
                resultId.toString(),
                "resultados/" + command.getExternalConsultationId(),
                command.getUploadedById(),
                command.getUploadedByUsername()
            );
            command.setUrl(fileUrl);
        }
        
        ResultDto resultDto = new ResultDto();
        resultDto.setId(resultId);
        resultDto.setType(command.getType());
        resultDto.setUrl(command.getUrl());
        resultDto.setUploadedById(command.getUploadedById());
        resultDto.setUploadedByUsername(command.getUploadedByUsername());
        
        resultService.save(resultDto, externalConsultation);
    }
}