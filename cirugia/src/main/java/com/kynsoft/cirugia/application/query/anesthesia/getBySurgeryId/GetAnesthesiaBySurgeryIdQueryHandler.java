package com.kynsoft.cirugia.application.query.anesthesia.getBySurgeryId;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.cirugia.domain.dto.Anesthesia;
import com.kynsoft.cirugia.domain.service.IAnesthesiaService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Manejador de la consulta para obtener una anestesia por el ID de cirugía.
 */
@Slf4j
@Service
public class GetAnesthesiaBySurgeryIdQueryHandler implements IQueryHandler<GetAnesthesiaBySurgeryIdQuery, GetAnesthesiaBySurgeryIdResponse> {

    private final IAnesthesiaService anesthesiaService;

    public GetAnesthesiaBySurgeryIdQueryHandler(IAnesthesiaService anesthesiaService) {
        this.anesthesiaService = anesthesiaService;
    }

    @Override
    public GetAnesthesiaBySurgeryIdResponse handle(GetAnesthesiaBySurgeryIdQuery query) {
        log.info("Obteniendo registro de Anestesia para la cirugía ID: {}", query.getSurgeryId());
        
        Optional<Anesthesia> anesthesia = anesthesiaService.getAnesthesiaBySurgeryId(query.getSurgeryId());
        
        log.info("Registro de Anestesia para la cirugía ID: {} - encontrado: {}", query.getSurgeryId(), anesthesia.isPresent());
        
        return new GetAnesthesiaBySurgeryIdResponse(anesthesia.get());
    }
}