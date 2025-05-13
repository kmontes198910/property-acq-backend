package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.query.subCategory.search.GetSearchSubCategoryQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.kynsoft.propertyacqcenter.domain.services.ISubCategoryService;

@RestController
@RequestMapping("/api/sub-category")
public class SubCategoryController {

    private final IMediator mediator;
    private final ISubCategoryService subCategoryConstructionTypeService;

    public SubCategoryController(IMediator mediator, ISubCategoryService subCategoryConstructionTypeService) {

        this.mediator = mediator;
        this.subCategoryConstructionTypeService = subCategoryConstructionTypeService;
    }

    @GetMapping("/create")
    public ResponseEntity<String> createSider() {
        this.subCategoryConstructionTypeService.create();

        return ResponseEntity.ok("ok");
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request) {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchSubCategoryQuery query = new GetSearchSubCategoryQuery(pageable, request.getFilter(), request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
