package com.kynsof.hospitalizationService.application.command.bed.create;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateBedRequest {

    private UUID ubication;
    private String code;
    private String name;
}
