package com.kynsoft.cirugia.application.command.surgery.updateconsent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateConsentimientoRequest {
    private String consentimientoInformadoCirugia;
    private String consentimientoInformadoAnestesia;
}
