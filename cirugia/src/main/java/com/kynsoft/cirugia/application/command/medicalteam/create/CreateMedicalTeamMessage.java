package com.kynsoft.cirugia.application.command.medicalteam.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class CreateMedicalTeamMessage implements ICommandMessage {
    private UUID id;
}