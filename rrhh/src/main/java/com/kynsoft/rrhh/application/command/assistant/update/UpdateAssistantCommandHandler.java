package com.kynsoft.rrhh.application.command.assistant.update;

import com.kynsof.share.core.domain.RulesChecker;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.utils.ConsumerUpdate;
import com.kynsof.share.utils.UpdateIfNotNull;
import com.kynsoft.rrhh.domain.dto.AssistantDto;
import com.kynsoft.rrhh.domain.interfaces.services.IAssistantService;
import com.kynsoft.rrhh.domain.rules.assistant.UpdateAssistantEmailMustBeUniqueRule;
import com.kynsoft.rrhh.domain.rules.assistant.UpdateAssistantIdentificationMustBeUniqueRule;
import org.springframework.stereotype.Component;

@Component
public class UpdateAssistantCommandHandler implements ICommandHandler<UpdateAssistantCommand> {

    private final IAssistantService service;


    public UpdateAssistantCommandHandler(IAssistantService service) {
        this.service = service;
    }

    @Override
    public void handle(UpdateAssistantCommand command) {

        ConsumerUpdate update = new ConsumerUpdate();
        AssistantDto assistantDto = service.findById(command.getId());
        boolean isUpdated = false;

        if (UpdateIfNotNull.updateIfStringNotNullNotEmptyAndNotEquals(assistantDto::setEmail, command.getEmail(), assistantDto.getEmail(), update::setUpdate)) {
            RulesChecker.checkRule(new UpdateAssistantEmailMustBeUniqueRule(this.service, command.getEmail(), command.getId()));
            isUpdated = true;
        }
        if (UpdateIfNotNull.updateIfStringNotNullNotEmptyAndNotEquals(assistantDto::setIdentification, command.getIdentification(), assistantDto.getIdentification(), update::setUpdate)) {
            RulesChecker.checkRule(new UpdateAssistantIdentificationMustBeUniqueRule(this.service, command.getIdentification(), command.getId()));
            isUpdated = true;
        }
        if (UpdateIfNotNull.updateIfStringNotNullNotEmptyAndNotEquals(assistantDto::setName, command.getName(), assistantDto.getName(), update::setUpdate)) {
            isUpdated = true;
        }
        if (UpdateIfNotNull.updateIfStringNotNullNotEmptyAndNotEquals(assistantDto::setLastName, command.getLastName(), assistantDto.getLastName(), update::setUpdate)) {
            isUpdated = true;
        }
        if (UpdateIfNotNull.updateIfStringNotNullNotEmptyAndNotEquals(assistantDto::setDepartment, command.getDepartment(), assistantDto.getDepartment(), update::setUpdate)) {
            isUpdated = true;
        }
        if (UpdateIfNotNull.updateIfStringNotNullNotEmptyAndNotEquals(assistantDto::setPhoneNumber, command.getPhoneNumber(), assistantDto.getPhoneNumber(), update::setUpdate)) {
            isUpdated = true;
        }
        if (command.getImage() ==null || assistantDto.getImage() == null || !assistantDto.getImage().equals(command.getImage())) {
            assistantDto.setImage(command.getImage());
            isUpdated = true;
        }
        if (UpdateIfNotNull.updateIfStringNotNullNotEmptyAndNotEquals(assistantDto::setCode, command.getCode(), assistantDto.getCode(), update::setUpdate)) {
            isUpdated = true;
        }
        if (command.getStatus() != null && !command.getStatus().equals(assistantDto.getStatus())) {
            assistantDto.setStatus(command.getStatus());
            isUpdated = true;
        }

        if (isUpdated) {
            service.update(assistantDto);
        }
    }
}