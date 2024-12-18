package com.kynsof.treatments.application.command.exam.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.treatments.domain.dto.ExamDto;
import com.kynsof.treatments.domain.service.IExamService;
import org.springframework.stereotype.Component;

@Component
public class DeleteExamCommandHandler implements ICommandHandler<DeleteExamCommand> {

    private final IExamService serviceImpl;

    public DeleteExamCommandHandler(IExamService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public void handle(DeleteExamCommand command) {
        ExamDto delete = this.serviceImpl.findById(command.getId());

        serviceImpl.delete(delete);
    }

}
