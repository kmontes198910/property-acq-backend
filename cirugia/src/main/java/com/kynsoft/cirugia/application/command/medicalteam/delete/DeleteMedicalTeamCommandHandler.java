package com.kynsoft.cirugia.application.command.medicalteam.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.cirugia.domain.service.IMedicalTeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeleteMedicalTeamCommandHandler implements ICommandHandler<DeleteMedicalTeamCommand> {

    private final IMedicalTeamService medicalTeamService;

    @Override
    @Transactional
    public void handle(DeleteMedicalTeamCommand command) {
        log.info("Deleting medical team member with ID: {}", command.getId());
        medicalTeamService.deleteMedicalTeam(command.getId());
    }
}