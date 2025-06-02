package com.kynsoft.cirugia.application.command.operatingroom.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.cirugia.domain.service.IOperatingRoomService;
import com.kynsoft.cirugia.domain.dto.OperatingRoom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateOperatingRoomCommandHandler implements ICommandHandler<UpdateOperatingRoomCommand> {

    private final IOperatingRoomService operatingRoomService;

    @Override
    @Transactional
    public void handle(UpdateOperatingRoomCommand command) {
        log.info("Updating operating room with ID: {}", command.getOperatingRoomId());
        
        Optional<OperatingRoom> existingRoomOpt = operatingRoomService.getOperatingRoomById(command.getOperatingRoomId());
        
        if (existingRoomOpt.isEmpty()) {
            throw new RuntimeException("Operating Room not found with ID: " + command.getOperatingRoomId());
        }
        
        OperatingRoom existingRoom = existingRoomOpt.get();
        
        OperatingRoom operatingRoom = OperatingRoom.builder()
                .id(command.getOperatingRoomId())
                .roomNumber(command.getRoomNumber())
                .name(command.getName())
                .location(command.getLocation())
                .floor(command.getFloor())
                .wing(command.getWing())
                .type(command.getType())
                .size(command.getSize())
                .capacity(command.getCapacity())
                .hasVentilationSystem(command.getHasVentilationSystem())
                .hasAnesthesiaEquipment(command.getHasAnesthesiaEquipment())
                .hasSurgicalLights(command.getHasSurgicalLights())
                .hasMonitoringSystems(command.getHasMonitoringSystems())
                .hasOxygenSupply(command.getHasOxygenSupply())
                .hasXRayCapability(command.getHasXRayCapability())
                .hasLaserEquipment(command.getHasLaserEquipment())
                .hasRoboticsSystem(command.getHasRoboticsSystem())
                .specialFeatures(command.getSpecialFeatures())
                .status(command.getStatus())
                .lastMaintenanceDate(command.getLastMaintenanceDate())
                .nextMaintenanceDate(command.getNextMaintenanceDate())
                // Mantener los campos que no se actualizan
                .businessId(existingRoom.getBusinessId())
                .createdAt(existingRoom.getCreatedAt())
                .createdBy(existingRoom.getCreatedBy())
                .updatedBy(command.getUpdatedBy())
                .build();

        operatingRoomService.updateOperatingRoom(operatingRoom);
    }
}