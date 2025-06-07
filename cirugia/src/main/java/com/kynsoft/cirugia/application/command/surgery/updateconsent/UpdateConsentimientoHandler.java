package com.kynsoft.cirugia.application.command.surgery.updateconsent;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.cirugia.domain.service.ISurgeryService;
import com.kynsoft.cirugia.domain.dto.Surgery;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
@Slf4j
public class UpdateConsentimientoHandler implements ICommandHandler<UpdateConsentimientoCommand> {

    private final ISurgeryService surgeryService;

    @Override
    public void handle(UpdateConsentimientoCommand command) {
        Optional<Surgery> optionalSurgery = surgeryService.getSurgeryById(command.getSurgeryId());
        
        if (optionalSurgery.isEmpty()) {
            throw new IllegalArgumentException("No se encontró la cirugía con ID: " + command.getSurgeryId());
        }
        
        Surgery surgery = optionalSurgery.get();
        
        // Actualiza solo los campos de consentimiento
        surgery.setConsentimientoInformadoCirugia(command.getConsentimientoInformadoCirugia());
        surgery.setConsentimientoInformadoAnestesia(command.getConsentimientoInformadoAnestesia());
        surgery.setUpdatedBy(command.getUpdatedBy());
        
        // Actualiza la cirugía usando el servicio
        surgeryService.updateSurgery(surgery);
    }
}
