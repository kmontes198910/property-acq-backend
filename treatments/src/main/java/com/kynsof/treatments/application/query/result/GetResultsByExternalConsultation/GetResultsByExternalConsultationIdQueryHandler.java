package com.kynsof.treatments.application.query.result.GetResultsByExternalConsultation;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.treatments.application.query.result.ResultResponse;
import com.kynsof.treatments.application.service.ResultServiceImpl;
import com.kynsof.treatments.domain.dto.ResultDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class GetResultsByExternalConsultationIdQueryHandler implements IQueryHandler<GetResultsByExternalConsultationIdQuery, ResultResponseByExternalConsult> {

    private final ResultServiceImpl resultService;

    @Override
    public ResultResponseByExternalConsult handle(GetResultsByExternalConsultationIdQuery query) {
        List<ResultDto> resultDtos = resultService.findByExternalConsultationId(query.getExternalConsultationId());
        
        return new ResultResponseByExternalConsult(
                resultDtos.stream()
                        .map(resultDto -> new ResultResponse(
                                resultDto.getId(),
                                resultDto.getType(),
                                resultDto.getUrl(),
                                resultDto.getUploadedById(),
                                resultDto.getUploadedByUsername(),
                                resultDto.getExternalConsultation().getId(),
                                null,
                                null,
                                resultDto.getFileName(),
                                resultDto.getFileType() // Mapeo del tipo de archivo
                        ))
                        .collect(Collectors.toList())
        );
    }
}