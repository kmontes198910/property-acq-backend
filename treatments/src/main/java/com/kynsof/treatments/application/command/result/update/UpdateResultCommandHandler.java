package com.kynsof.treatments.application.command.result.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.treatments.domain.dto.ResultDto;
import com.kynsof.treatments.domain.service.IResultService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UpdateResultCommandHandler implements ICommandHandler<UpdateResultCommand> {

    private final IResultService resultService;

    @Override
    public void handle(UpdateResultCommand command) {

        ResultDto resultDto = resultService.findById(command.getId());
        resultDto.setType(command.getType());
        resultDto.setUrl(command.getUrl());
        resultDto.setUploadedById(command.getUploadedById());
        resultDto.setUploadedByUsername(command.getUploadedByUsername());
        
        resultService.update(resultDto);
    }
}