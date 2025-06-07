package com.kynsoft.cirugia.application.command.surgery.updateconsent;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class UpdateConsentimientoCommand implements ICommand {
    private UUID id;
    private UUID surgeryId;
    private String consentimientoInformadoCirugia;
    private String consentimientoInformadoAnestesia;
    private UUID updatedBy;

    public UpdateConsentimientoCommand(UUID surgeryId, String consentimientoInformadoCirugia, 
                                      String consentimientoInformadoAnestesia, UUID updatedBy) {
        this.id = UUID.randomUUID();
        this.surgeryId = surgeryId;
        this.consentimientoInformadoCirugia = consentimientoInformadoCirugia;
        this.consentimientoInformadoAnestesia = consentimientoInformadoAnestesia;
        this.updatedBy = updatedBy;
    }

    public static UpdateConsentimientoCommand fromRequest(UUID surgeryId, UpdateConsentimientoRequest request, UUID updatedBy) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        
        return new UpdateConsentimientoCommand(
            surgeryId,
            request.getConsentimientoInformadoCirugia(),
            request.getConsentimientoInformadoAnestesia(),
            updatedBy
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateConsentimientoMessage(id);
    }
}
