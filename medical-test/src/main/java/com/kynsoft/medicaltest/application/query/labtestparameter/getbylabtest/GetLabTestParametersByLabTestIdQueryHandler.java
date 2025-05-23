package com.kynsoft.medicaltest.application.query.labtestparameter.getbylabtest;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.medicaltest.application.query.labtestparameter.getbyid.LabTestParameterResponse;
import com.kynsoft.medicaltest.domain.dto.LabTestParameterDto;
import com.kynsoft.medicaltest.domain.service.ILabTestParameterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Manejador de la consulta para obtener todos los parámetros de un examen de laboratorio
 */
@Component
@RequiredArgsConstructor
public class GetLabTestParametersByLabTestIdQueryHandler implements IQueryHandler<GetLabTestParametersByLabTestIdQuery, LabTestParameterByLabTestResponse> {

    private final ILabTestParameterService labTestParameterService;

    @Override
    public LabTestParameterByLabTestResponse handle(GetLabTestParametersByLabTestIdQuery query) {
        List<LabTestParameterDto> dtos = labTestParameterService.getByLabTestId(query.getLabTestId());
        List<LabTestParameterResponse> responses = dtos.stream()
                .map(LabTestParameterResponse::new)
                .collect(Collectors.toList());
        
        return new LabTestParameterByLabTestResponse(responses);
    }
}
