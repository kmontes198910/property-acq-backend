package com.kynsof.hospitalizationService.application.response;

import com.kynsof.hospitalizationService.domain.dto.UbicationDto;
import com.kynsof.share.core.domain.bus.query.IResponse;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UbicationResponse implements IResponse {
    private UUID id;
    private String code;
    private String name;

    public UbicationResponse(UbicationDto dto) {
        this.id = dto.getId();
        this.code = dto.getCode();
        this.name = dto.getName();
    }

}
