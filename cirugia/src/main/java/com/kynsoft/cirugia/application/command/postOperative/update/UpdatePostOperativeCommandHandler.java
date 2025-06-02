package com.kynsoft.cirugia.application.command.postOperative.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.cirugia.domain.dto.PostOperative;
import com.kynsoft.cirugia.domain.service.IPostOperativeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class UpdatePostOperativeCommandHandler implements ICommandHandler<UpdatePostOperativeCommand> {

    private final IPostOperativeRepository postOperativeRepository;

    public UpdatePostOperativeCommandHandler(IPostOperativeRepository postOperativeRepository) {
        this.postOperativeRepository = postOperativeRepository;
    }

    @Override
    public void handle(UpdatePostOperativeCommand command) {
        log.info("Updating PostOperative with ID: {}", command.getId());

        Optional<PostOperative> optionalPostOperative = postOperativeRepository.findById(command.getId().toString());

        if (optionalPostOperative.isPresent()) {
            PostOperative postOperative = optionalPostOperative.get();
            postOperative.setSurgeryId(command.getSurgeryId());
            postOperative.setTreatmentSummary(command.getTreatmentSummary());
            postOperative.setDischargeInstructions(command.getDischargeInstructions());
            postOperative.setLifeStatus(command.getLifeStatus());
            postOperative.setDischargeCondition(command.getDischargeCondition());
            postOperative.setStayDays(command.getStayDays());
            postOperative.setRestDays(command.getRestDays());
            postOperative.setClinicalSummary(command.getClinicalSummary());
            postOperative.setEvolutionSummary(command.getEvolutionSummary());
            postOperative.setDiagnosticFindings(command.getDiagnosticFindings());
            postOperative.setUpdatedAt(LocalDateTime.now());
            postOperative.setUpdatedBy(command.getUpdatedBy());

            postOperativeRepository.save(postOperative);
            log.info("PostOperative updated successfully");
        } else {
            log.error("PostOperative with ID {} not found", command.getId());
            throw new IllegalArgumentException("PostOperative not found with ID: " + command.getId());
        }
    }
}