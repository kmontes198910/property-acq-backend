package com.kynsoft.propertyacqcenter.application.command.propertyImages.update;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePropertyImagesRequest {

    private String property;
    private String url;
    private Boolean main;
}
