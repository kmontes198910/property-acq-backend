package com.kynsoft.cirugia.application.command.evolution.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class DeleteEvolutionMessage implements ICommandMessage {
    private final UUID id;
}