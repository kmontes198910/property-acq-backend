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
    private UUID dbConection;
    private String query;
    private Status status;

    public CreateJasperReportTemplateCommand(String code, String name, String description, JasperReportTemplateType type,
                                             byte[] file,  UUID dbConection) {
        this.id = UUID.randomUUID();
        this.code = code;
        this.name = name;
        this.description = description;
        this.type = type;
        this.file = file;
        this.dbConection = dbConection;
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateJasperReportTemplateMessage(id);
    }
}
