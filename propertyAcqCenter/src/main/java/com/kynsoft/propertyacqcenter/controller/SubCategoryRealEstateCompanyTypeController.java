package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.query.subCategoryRealEstateCompanyType.search.GetSearchSubCategoryRealEstateCompanyTypeQuery;
import com.kynsoft.propertyacqcenter.domain.services.ISubCategoryRealEstateCompanyTypeService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sub-category-real-estate-company-type")
public class SubCategoryRealEstateCompanyTypeController {

    private final IMediator mediator;
    private final ISubCategoryRealEstateCompanyTypeService subCategoryRealEstateCompanyTypeService;

    public SubCategoryRealEstateCompanyTypeController(IMediator mediator, ISubCategoryRealEstateCompanyTypeService subCategoryRealEstateCompanyTypeService) {

        this.mediator = mediator;
        this.subCategoryRealEstateCompanyTypeService = subCategoryRealEstateCompanyTypeService;
    }

    @GetMapping("/create")
    public ResponseEntity<String> createSider() {
        this.subCategoryRealEstateCompanyTypeService.create();

        return ResponseEntity.ok("ok");
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchSubCategoryRealEstateCompanyTypeQuery query = new GetSearchSubCategoryRealEstateCompanyTypeQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
