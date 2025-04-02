package com.kynsof.hospitalizationService.application.response;

import com.kynsof.hospitalizationService.domain.dto.*;
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
public class BedResponse implements IResponse {
    private UUID id;
    private String code;
    private String name;
    private UbicationDto ubication;

    public BedResponse(BedDto dto) {
        this.id = dto.getId();
        this.code = dto.getCode();
        this.name = dto.getCode();
        this.ubication = dto.getUbication();
    }

}
