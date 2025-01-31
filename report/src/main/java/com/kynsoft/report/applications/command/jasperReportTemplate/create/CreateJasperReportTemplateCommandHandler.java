package com.kynsoft.report.applications.command.jasperReportTemplate.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.report.domain.dto.DBConectionDto;
import com.kynsoft.report.domain.dto.JasperReportTemplateDto;
import com.kynsoft.report.domain.services.IAmazonClient;
import com.kynsoft.report.domain.services.IDBConectionService;
import com.kynsoft.report.domain.services.IJasperReportTemplateService;
import org.springframework.stereotype.Component;

@Component
public class CreateJasperReportTemplateCommandHandler implements ICommandHandler<CreateJasperReportTemplateCommand> {

    private final IJasperReportTemplateService service;
    private final IDBConectionService conectionService;

    public CreateJasperReportTemplateCommandHandler(IJasperReportTemplateService service, IDBConectionService conectionService) {
        this.service = service;
        this.conectionService = conectionService;
    }

    @Override
    public void handle(CreateJasperReportTemplateCommand command) {
        DBConectionDto conectionDto = command.getDbConection() != null ? this.conectionService.findById(command.getDbConection()) : null;
        this.service.create(new JasperReportTemplateDto(
                command.getId(), 
                command.getCode(), 
                command.getName(), 
                command.getDescription(), 
                command.getFile(),
                command.getType(),
                command.getParameters(),
                conectionDto,
                command.getQuery(),
                command.getStatus()
        ));
    }
}
