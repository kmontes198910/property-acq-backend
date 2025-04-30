package com.kynsoft.cirugia.application.command.operatingroom.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.cirugia.domain.service.IOperatingRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeleteOperatingRoomCommandHandler implements ICommandHandler<DeleteOperatingRoomCommand> {

    private final IOperatingRoomService operatingRoomService;

    @Override
    @Transactional
    public void handle(DeleteOperatingRoomCommand command) {
        log.info("Deleting operating room with ID: {}", command.getOperatingRoomId());
        
        operatingRoomService.deleteOperatingRoom(command.getOperatingRoomId());
    }
}