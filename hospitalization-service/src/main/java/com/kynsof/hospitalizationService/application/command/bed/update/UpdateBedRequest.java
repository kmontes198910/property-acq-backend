package com.kynsof.hospitalizationService.application.command.bed.update;

import com.kynsof.hospitalizationService.domain.dto.enun.BedStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateBedRequest {

    private UUID ubication;
    private String code;
    private String name;
    private BedStatus status;
}
