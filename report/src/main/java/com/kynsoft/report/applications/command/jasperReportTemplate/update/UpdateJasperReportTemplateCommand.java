package com.kynsoft.report.applications.command.jasperReportTemplate.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.report.domain.dto.JasperReportTemplateType;
import com.kynsoft.report.domain.dto.status.Status;
import lombok.Getter;
import lombok.Setter;

import java.util.Base64;
import java.util.UUID;

@Getter
@Setter
public class UpdateJasperReportTemplateCommand implements ICommand {

    private UUID id;
    private String code;
    private String name;
    private String description;
    private JasperReportTemplateType type;
    private String file;
    private String fileBase64; // Nuevo campo para el archivo en base64
    private byte[] fileBytes; // Bytes del archivo para compilación
    private String parameters;
    private UUID dbConection;
    private String query;
    private Status status;

    public UpdateJasperReportTemplateCommand(UUID id, String code, String name, String description,
                                             JasperReportTemplateType type, String file, String fileBase64, String parameters,
                                             UUID dbConection, String query, Status status) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.type = type;
        this.file = file;
        this.fileBase64 = fileBase64;
        if (fileBase64 != null && !fileBase64.isEmpty()) {
            this.fileBytes = Base64.getDecoder().decode(fileBase64);
        }
        this.parameters = parameters;
        this.dbConection = dbConection;
        this.query = query;
        this.status = status;
    }

    public static UpdateJasperReportTemplateCommand fromRequest(UpdateJasperReportTemplateRequest request, UUID id) {
        return new UpdateJasperReportTemplateCommand(
                id,
                request.getCode(),
                request.getName(),
                request.getDescription(),
                request.getType(),
                request.getFile(),
                request.getFileBase64(),
                request.getParameters(),
                request.getDbConection(),
                request.getQuery(),
                request.getStatus()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateJasperReportTemplateMessage(id);
    }
}
