package com.kynsoft.cirugia.application.command.admision.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Message for updating an admission
 */
@Component
@RequiredArgsConstructor
@AllArgsConstructor
public class UpdateAdmisionMessage implements ICommandMessage {
 private UUID id;

}
