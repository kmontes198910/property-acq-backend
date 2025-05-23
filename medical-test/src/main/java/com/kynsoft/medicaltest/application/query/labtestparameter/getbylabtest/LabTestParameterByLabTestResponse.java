package com.kynsoft.medicaltest.application.query.labtestparameter.getbylabtest;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.medicaltest.application.query.labtestparameter.getbyid.LabTestParameterResponse;
import com.kynsoft.medicaltest.domain.dto.LabTestParameterDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Respuesta para la consulta de un parámetro de examen de laboratorio por ID
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LabTestParameterByLabTestResponse implements IResponse {

   private List<LabTestParameterResponse> labTestParameters;
}
