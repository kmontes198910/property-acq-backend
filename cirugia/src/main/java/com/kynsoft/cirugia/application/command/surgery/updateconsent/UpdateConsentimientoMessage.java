package com.kynsoft.cirugia.application.command.surgery.updateconsent;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateConsentimientoMessage implements ICommandMessage {
    private UUID id;
}
