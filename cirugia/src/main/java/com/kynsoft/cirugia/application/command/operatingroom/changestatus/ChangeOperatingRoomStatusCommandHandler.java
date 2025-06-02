package com.kynsoft.cirugia.application.command.operatingroom.changestatus;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.cirugia.domain.service.IOperatingRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChangeOperatingRoomStatusCommandHandler implements ICommandHandler<ChangeOperatingRoomStatusCommand> {

    private final IOperatingRoomService operatingRoomService;

    @Override
    @Transactional
    public void handle(ChangeOperatingRoomStatusCommand command) {
        log.info("Changing operating room {} status to: {}", command.getOperatingRoomId(), command.getStatus());
        
        operatingRoomService.updateStatus(command.getOperatingRoomId(), command.getStatus(), command.getUpdatedBy());
    }
}