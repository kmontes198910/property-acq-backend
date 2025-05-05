package com.kynsoft.cirugia.application.command.evolution.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class CreateEvolutionMessage implements ICommandMessage {
    private final UUID id;
}