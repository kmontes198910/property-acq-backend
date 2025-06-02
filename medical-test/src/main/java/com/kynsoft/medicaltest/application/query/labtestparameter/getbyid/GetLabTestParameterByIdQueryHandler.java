package com.kynsoft.medicaltest.application.query.labtestparameter.getbyid;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.medicaltest.domain.dto.LabTestParameterDto;
import com.kynsoft.medicaltest.domain.service.ILabTestParameterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Manejador de la consulta para obtener un parámetro de examen de laboratorio por su ID
 */
@Component
@RequiredArgsConstructor
public class GetLabTestParameterByIdQueryHandler implements IQueryHandler<GetLabTestParameterByIdQuery, LabTestParameterResponse> {

    private final ILabTestParameterService labTestParameterService;

    @Override
    public LabTestParameterResponse handle(GetLabTestParameterByIdQuery query) {
        LabTestParameterDto dto = labTestParameterService.getById(query.getId());
        return new LabTestParameterResponse(dto);
    }
}
