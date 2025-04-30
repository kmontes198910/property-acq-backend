package com.kynsoft.cirugia.application.command.operatingroom.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.cirugia.domain.service.IOperatingRoomService;
import com.kynsoft.cirugia.domain.dto.OperatingRoom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateOperatingRoomCommandHandler implements ICommandHandler<CreateOperatingRoomCommand> {

    private final IOperatingRoomService operatingRoomService;

    @Override
    @Transactional
    public void handle(CreateOperatingRoomCommand command) {
        log.info("Creating new operating room: {}", command.getRoomNumber());

        OperatingRoom operatingRoom = OperatingRoom.builder()
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
                .status(command.getStatus() != null ? command.getStatus() : "AVAILABLE") // Estado predeterminado
                .lastMaintenanceDate(command.getLastMaintenanceDate())
                .nextMaintenanceDate(command.getNextMaintenanceDate())
                .businessId(command.getBusinessId())
                .createdBy(command.getCreatedBy())
                .build();

        UUID operatingRoomId = operatingRoomService.createOperatingRoom(operatingRoom);

        // Asignamos el ID generado al mensaje de respuesta
        command.setId(operatingRoomId);
    }
}