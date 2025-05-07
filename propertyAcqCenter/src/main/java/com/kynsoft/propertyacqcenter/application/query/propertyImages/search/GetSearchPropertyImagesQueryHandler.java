package com.kynsoft.propertyacqcenter.application.query.propertyImages.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyImagesService;
import org.springframework.stereotype.Component;

@Component
public class GetSearchPropertyImagesQueryHandler implements IQueryHandler<GetSearchPropertyImagesQuery, PaginatedResponse>{

    private final IPropertyImagesService propertyImagesService;

    public GetSearchPropertyImagesQueryHandler(IPropertyImagesService propertyImagesService) {
        this.propertyImagesService = propertyImagesService;
    }

    @Override
    public PaginatedResponse handle(GetSearchPropertyImagesQuery query) {

        return this.propertyImagesService.search(query.getPageable(),query.getFilter());
    }
}
