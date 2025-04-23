package com.kynsof.treatments.application.command.result.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.treatments.application.service.ResultServiceImpl;
import com.kynsof.treatments.domain.dto.ResultDto;
import com.kynsof.treatments.domain.service.IResultFileUploadService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DeleteResultCommandHandler implements ICommandHandler<DeleteResultCommand> {

    private final ResultServiceImpl resultService;
    private final IResultFileUploadService resultFileUploadService;
    @Override
    public void handle(DeleteResultCommand command) {
        ResultDto resultDto = resultService.findById(command.getId());
        if (resultDto == null) {
            throw new IllegalArgumentException("Result not found");
        }
        String[] parts = resultDto.getUrl().split("/");
        String id = parts[parts.length - 1];  // El último elemento
        System.out.println("ID: " + id);
        resultFileUploadService.deleteFile(id);
        resultService.delete(command.getId());
    }
}