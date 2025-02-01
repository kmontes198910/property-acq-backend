package com.kynsoft.report.applications.command.jasperReportTemplate.update;

import com.kynsof.share.core.domain.RulesChecker;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.rules.ValidateObjectNotNullRule;
import com.kynsof.share.utils.UpdateIfNotNull;
import com.kynsoft.report.domain.dto.DBConectionDto;
import com.kynsoft.report.domain.dto.JasperReportTemplateDto;
import com.kynsoft.report.domain.dto.status.Status;
import com.kynsoft.report.domain.services.IDBConectionService;
import com.kynsoft.report.domain.services.IJasperReportTemplateService;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.function.Consumer;

@Component
public class UpdateJasperReportTemplateCommandHandler implements ICommandHandler<UpdateJasperReportTemplateCommand> {

    private final IJasperReportTemplateService service;;
    private final IDBConectionService conectionService;

    public UpdateJasperReportTemplateCommandHandler(IJasperReportTemplateService service, IDBConectionService conectionService) {
        this.service = service;
        this.conectionService = conectionService;
    }

    @Override
    public void handle(UpdateJasperReportTemplateCommand command) {
        RulesChecker.checkRule(new ValidateObjectNotNullRule<>(command.getId(), "id", "JasperReportTemplate ID cannot be null."));
        JasperReportTemplateDto update = this.service.findById(command.getId());
        update.setTemplateContentUrl(command.getFile());
        UpdateIfNotNull.updateIfNotNull(update::setTemplateCode, command.getCode());
        UpdateIfNotNull.updateIfNotNull(update::setTemplateDescription, command.getDescription());
        UpdateIfNotNull.updateIfNotNull(update::setTemplateName, command.getName());
        UpdateIfNotNull.updateIfNotNull(update::setParameters, command.getParameters());
        update.setType(command.getType());
        this.updateConection(update::setDbConection, command.getDbConection(), update.getDbConection() != null ? update.getDbConection().getId() : null);
        UpdateIfNotNull.updateIfNotNull(update::setQuery, command.getQuery());
        updateStatus(update::setStatus, command.getStatus(), update.getStatus());

        this.service.update(update);
    }

    private void updateConection(Consumer<DBConectionDto> setter, UUID newValue, UUID oldValue) {
        if (newValue != null && !newValue.equals(oldValue)) {
            DBConectionDto conectionDto = this.conectionService.findById(newValue);
            setter.accept(conectionDto);
        }
    }

    private void updateStatus(Consumer<Status> setter, Status newValue, Status oldValue) {
        if (newValue != null && !newValue.equals(oldValue)) {
            setter.accept(newValue);
        }
    }
}
