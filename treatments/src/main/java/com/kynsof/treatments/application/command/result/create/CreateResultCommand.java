package com.kynsof.treatments.application.command.result.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Getter
@Setter
public class CreateResultCommand implements ICommand {

    private UUID id;
    private String type;
    private MultipartFile base64Content;
    private String fileName;
    private String url;
    private String uploadedById;
    private String uploadedByUsername;
    private String fileType; // Añadido campo para el tipo de archivo
    private final UUID externalConsultationId;

    public CreateResultCommand(String type, MultipartFile base64Content, String fileName, String fileType, String uploadedById,
                               String uploadedByUsername, UUID externalConsultationId) {
        this.type = type;
        this.base64Content = base64Content;
        this.fileName = fileName;
        this.fileType = fileType;
        this.uploadedById = uploadedById;
        this.uploadedByUsername = uploadedByUsername;
        this.externalConsultationId = externalConsultationId;
    }

    public static CreateResultCommand fromRequestWithFile(String type, String externalConsultationId, MultipartFile file, String userId, String username) {
//        String base64Content = null;
//        try {
//            base64Content = Base64.getEncoder().encodeToString(file.getBytes());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        return new CreateResultCommand(
                type,
                file,
                file.getOriginalFilename(), // Usar el nombre original del archivo
                file.getContentType(),      // Capturar el tipo MIME del archivo
                userId,
                username,
                UUID.fromString(externalConsultationId)
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateResultMessage(id);
    }
}