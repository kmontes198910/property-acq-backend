package com.kynsoft.cirugia.application.command.evolution.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class UpdateEvolutionMessage implements ICommandMessage {
    private final UUID id;
}