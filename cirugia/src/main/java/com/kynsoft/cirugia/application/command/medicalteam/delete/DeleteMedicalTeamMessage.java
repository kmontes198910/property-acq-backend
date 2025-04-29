package com.kynsoft.cirugia.application.command.medicalteam.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class DeleteMedicalTeamMessage implements ICommandMessage {
    private UUID id;
}