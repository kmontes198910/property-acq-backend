package com.kynsoft.cirugia.application.command.treatment.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.cirugia.domain.service.ITreatmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeleteTreatmentCommandHandler implements ICommandHandler<DeleteTreatmentCommand> {

    private final ITreatmentRepository treatmentRepository;

    @Override
    @Transactional
    public void handle(DeleteTreatmentCommand command) {
        log.info("Eliminando tratamiento con ID: {}", command.getTreatmentId());
        
        treatmentRepository.deleteById(command.getTreatmentId().toString());
        
        log.info("Tratamiento eliminado correctamente");
    }
}