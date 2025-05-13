package com.kynsoft.finamer.digitalsignature.application.command.digitalsignaturecertificate.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.finamer.digitalsignature.domain.dto.SignRequestDto;
import com.kynsoft.finamer.digitalsignature.domain.dto.SignResponseDto;
import com.kynsoft.finamer.digitalsignature.domain.dto.VisibleSignatureDto;
import com.kynsoft.finamer.digitalsignature.domain.service.IDigitalSignatureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentSignCommandHandler implements ICommandHandler<DocumentSignCommand> {

    private final IDigitalSignatureService digitalSignatureService;

    @Override
    @Transactional
    public void handle(DocumentSignCommand command) {
        // Construir el DTO de solicitud a partir del comando
        SignRequestDto dto = new SignRequestDto();
        dto.setDocument(command.getDocument());
        dto.setDocumentName(command.getDocumentName());
        dto.setCertificatePassword(command.getCertificatePassword());
        dto.setCertificateP12Id(command.getCertificateP12Id());
        
        // Configurar la firma visible si está presente
        if (command.getVisibleSignature() != null) {
            VisibleSignatureDto visibleSignatureDto = new VisibleSignatureDto();
            visibleSignatureDto.setY(command.getVisibleSignature().getY());
            visibleSignatureDto.setX(command.getVisibleSignature().getX());
            visibleSignatureDto.setPage(command.getVisibleSignature().getPage());
            visibleSignatureDto.setWidth(command.getVisibleSignature().getWidth());
            visibleSignatureDto.setHeight(command.getVisibleSignature().getHeight());
            dto.setVisibleSignature(visibleSignatureDto);
        }
        
        dto.setReason(command.getReason());
        dto.setLocation(command.getLocation());
        
        // Firmar el documento
        SignResponseDto response = digitalSignatureService.signDocument(dto);
        
        // Establecer el documento firmado en el comando
        command.setSignedDocument(response.getSignedDocument());
        
        // Determinar el tipo de contenido basado en el nombre del documento
        String contentType = "application/pdf";
        if (command.getDocumentName() != null) {
            if (command.getDocumentName().toLowerCase().endsWith(".xml")) {
                contentType = "application/xml";
            } else if (command.getDocumentName().toLowerCase().endsWith(".json")) {
                contentType = "application/json";
            }
            // Se pueden agregar más tipos según sea necesario
        }
        
        // Crear el mensaje de respuesta con información completa
        command.setResponseMessage(DocumentSignMessage.builder()
                .signedDocument(response.getSignedDocument())
                .signedDocumentBase64(Base64.getEncoder().encodeToString(response.getSignedDocument()))
                .signerName(response.getSignerName())
                .signatureDate(response.getSignatureDate())
                .filePath(response.getFilePath())
                .documentName(command.getDocumentName())
                .contentType(contentType)
                .build());
    }
}