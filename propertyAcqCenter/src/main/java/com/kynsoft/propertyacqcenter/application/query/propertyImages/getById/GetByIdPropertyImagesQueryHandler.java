package com.kynsoft.propertyacqcenter.application.query.propertyImages.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.PropertyImagesResponse;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyImagesService;
import org.springframework.stereotype.Component;

@Component
public class GetByIdPropertyImagesQueryHandler implements IQueryHandler<GetByIdPropertyImagesQuery, PropertyImagesResponse>{

    private final IPropertyImagesService propertyImagesService;

    public GetByIdPropertyImagesQueryHandler(IPropertyImagesService propertyImagesService) {
        this.propertyImagesService = propertyImagesService;
    }

    @Override
    public PropertyImagesResponse handle(GetByIdPropertyImagesQuery query) {

        return new PropertyImagesResponse(this.propertyImagesService.getById(query.getId()));
    }
}
