package com.kynsoft.cirugia.application.command.admision.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAdmisionMessage implements ICommandMessage {
 private UUID id;

}
