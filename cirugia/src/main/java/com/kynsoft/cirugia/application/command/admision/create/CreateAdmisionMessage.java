package com.kynsoft.cirugia.application.command.admision.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Mensaje para la creación de admisión
 */
@Component
@RequiredArgsConstructor
public class CreateAdmisionMessage implements ICommandMessage {

    private final UUID id;


}
