package com.kynsoft.cirugia.application.command.postOperative.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.cirugia.domain.dto.PostOperative;
import com.kynsoft.cirugia.domain.service.IPostOperativeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class CreatePostOperativeCommandHandler implements ICommandHandler<CreatePostOperativeCommand> {

    private final IPostOperativeRepository postOperativeRepository;

    public CreatePostOperativeCommandHandler(IPostOperativeRepository postOperativeRepository) {
        this.postOperativeRepository = postOperativeRepository;
    }

    @Override
    public void handle(CreatePostOperativeCommand command) {
        log.info("Creating new PostOperative for surgery ID: {}", command.getSurgeryId());

        PostOperative postOperative = PostOperative.builder()
                .id(command.getId())
                .surgeryId(command.getSurgeryId())
                .treatmentSummary(command.getTreatmentSummary())
                .dischargeInstructions(command.getDischargeInstructions())
                .lifeStatus(command.getLifeStatus())
                .dischargeCondition(command.getDischargeCondition())
                .stayDays(command.getStayDays())
                .restDays(command.getRestDays())
                .clinicalSummary(command.getClinicalSummary())
                .evolutionSummary(command.getEvolutionSummary())
                .diagnosticFindings(command.getDiagnosticFindings())
                .createdAt(LocalDateTime.now())
                .createdBy(command.getCreatedBy())
                .build();

        postOperativeRepository.save(postOperative);
        log.info("PostOperative created successfully with ID: {}", postOperative.getId());
    }
}