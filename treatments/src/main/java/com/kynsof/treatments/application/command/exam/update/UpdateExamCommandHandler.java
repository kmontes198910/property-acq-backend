package com.kynsof.treatments.application.command.exam.update;

import com.kynsof.share.core.domain.RulesChecker;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.rules.ValidateObjectNotNullRule;
import com.kynsof.share.utils.UpdateIfNotNull;
import com.kynsof.treatments.domain.dto.ExamDto;
import com.kynsof.treatments.domain.rules.externalconsultation.ExternalConsultationCreateAtNotEqualsRule;
import com.kynsof.treatments.domain.service.IExamService;
import com.kynsof.treatments.domain.service.IExternalConsultationService;
import org.springframework.stereotype.Component;

@Component
public class UpdateExamCommandHandler implements ICommandHandler<UpdateExamCommand> {

    private final IExamService serviceImpl;

    public UpdateExamCommandHandler(IExamService serviceImpl, IExternalConsultationService externalConsultationService) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public void handle(UpdateExamCommand command) {
        RulesChecker.checkRule(new ValidateObjectNotNullRule<>(command.getId(), "id", "Exam ID cannot be null."));
        ExamDto update = this.serviceImpl.findById(command.getId());
        RulesChecker.checkRule(new ExternalConsultationCreateAtNotEqualsRule(update.getExternalConsultation().getConsultationTime()));
        UpdateIfNotNull.updateIfStringNotNull(update::setDescription, command.getDescription());
        UpdateIfNotNull.updateIfStringNotNull(update::setName, command.getName());
        UpdateIfNotNull.updateIfStringNotNull(update::setResult, command.getResult());
        UpdateIfNotNull.updateIfStringNotNull(update::setCode, command.getCode());


        update.setType(command.getType());
        update.setDatePerformed(update.getDatePerformed());

        serviceImpl.create(update);
    }
}
