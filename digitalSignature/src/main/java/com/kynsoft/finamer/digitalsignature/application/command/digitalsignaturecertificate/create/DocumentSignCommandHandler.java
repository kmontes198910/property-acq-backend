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

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentSignCommandHandler implements ICommandHandler<DocumentSignCommand> {

    private final IDigitalSignatureService digitalSignatureService;

    @Override
    @Transactional
    public void handle(DocumentSignCommand command) {

        SignRequestDto dto = new SignRequestDto();
        dto.setDocument(command.getDocument());
        dto.setDocumentName(command.getDocumentName());
        dto.setCertificatePassword(command.getCertificatePassword());
        dto.setCertificateP12Id(command.getCertificateP12Id());
        VisibleSignatureDto visibleSignatureDto = new VisibleSignatureDto();
        visibleSignatureDto.setY(command.getVisibleSignature().getY());
        visibleSignatureDto.setX(command.getVisibleSignature().getX());
        visibleSignatureDto.setPage(command.getVisibleSignature().getPage());
        visibleSignatureDto.setWidth(command.getVisibleSignature().getWidth());
        visibleSignatureDto.setHeight(command.getVisibleSignature().getHeight());
        dto.setVisibleSignature(visibleSignatureDto);
        dto.setReason(command.getReason());
        dto.setLocation(command.getLocation());
        SignResponseDto rsponse = digitalSignatureService.signDocument(dto);
        command.setSignedDocument(rsponse.getSignedDocument());

    }
}