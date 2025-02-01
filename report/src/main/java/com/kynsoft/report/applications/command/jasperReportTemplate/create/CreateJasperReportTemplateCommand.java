package com.kynsoft.report.applications.command.jasperReportTemplate.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.report.domain.dto.JasperReportTemplateType;
import com.kynsoft.report.domain.dto.status.Status;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateJasperReportTemplateCommand implements ICommand {

    private UUID id;
    private String code;
    private String name;
    private String description;
    private JasperReportTemplateType type;
    private byte[] file;
    private String parameters;
    private UUID dbConection;
    private String query;
    private Status status;

    public CreateJasperReportTemplateCommand(String code, String name, String description, JasperReportTemplateType type,
                                             byte[] file, String parameters, UUID dbConection, String query, Status status) {
        this.id = UUID.randomUUID();
        this.code = code;
        this.name = name;
        this.description = description;
        this.type = type;
        this.file = file;
        this.parameters = parameters;
        this.dbConection = dbConection;
        this.query = query;
        this.status = status;
    }

    public static CreateJasperReportTemplateCommand fromRequest(CreateJasperReportTemplateRequest request) {
        return new CreateJasperReportTemplateCommand(
                request.getCode(), 
                request.getName(), 
                request.getDescription(), 
                request.getType(), 
               null,
                request.getParameters(),
                request.getDbConection(),
                request.getQuery(),
                request.getStatus()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateJasperReportTemplateMessage(id);
    }
}
