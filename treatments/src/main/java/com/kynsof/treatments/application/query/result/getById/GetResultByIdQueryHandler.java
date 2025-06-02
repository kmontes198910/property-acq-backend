package com.kynsof.treatments.application.query.result.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.treatments.application.query.result.ResultResponse;
import com.kynsof.treatments.application.service.ResultServiceImpl;
import com.kynsof.treatments.domain.dto.ResultDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GetResultByIdQueryHandler implements IQueryHandler<GetResultByIdQuery, ResultResponse> {

    private final ResultServiceImpl resultService;

    @Override
    public ResultResponse handle(GetResultByIdQuery query) {
        ResultDto resultDto = resultService.findById(query.getId());
        
        return new ResultResponse(
                resultDto.getId(),
                resultDto.getType(),
                resultDto.getUrl(),
                resultDto.getUploadedById(),
                resultDto.getUploadedByUsername(),
                resultDto.getExternalConsultation() != null ? resultDto.getExternalConsultation().getId() : null,
                null, // createdAt - se obtendrá del servicio
                null  // updatedAt - se obtendrá del servicio,
                ,resultDto.getFileName(),
                resultDto.getFileType() // Mapeo del tipo de archivo
        );
    }
}