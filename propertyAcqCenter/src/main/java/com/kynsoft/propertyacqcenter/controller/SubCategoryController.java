package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.subCategory.create.CreateSubCategoryCommand;
import com.kynsoft.propertyacqcenter.application.command.subCategory.create.CreateSubCategoryMessage;
import com.kynsoft.propertyacqcenter.application.command.subCategory.create.CreateSubCategoryRequest;
import com.kynsoft.propertyacqcenter.application.command.subCategory.update.UpdateSubCategoryCommand;
import com.kynsoft.propertyacqcenter.application.command.subCategory.update.UpdateSubCategoryMessage;
import com.kynsoft.propertyacqcenter.application.command.subCategory.update.UpdateSubCategoryRequest;
import com.kynsoft.propertyacqcenter.application.query.subCategory.search.GetSearchSubCategoryQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.kynsoft.propertyacqcenter.domain.services.ISubCategoryService;
import java.util.UUID;

@RestController
@RequestMapping("/api/sub-category")
public class SubCategoryController {

    private final IMediator mediator;
    private final ISubCategoryService subCategoryConstructionTypeService;

    public SubCategoryController(IMediator mediator, ISubCategoryService subCategoryConstructionTypeService) {

        this.mediator = mediator;
        this.subCategoryConstructionTypeService = subCategoryConstructionTypeService;
    }

    @PostMapping("")
    public ResponseEntity<CreateSubCategoryMessage> create(@RequestBody CreateSubCategoryRequest request) {
        CreateSubCategoryCommand createCommand = CreateSubCategoryCommand.fromRequest(request);
        CreateSubCategoryMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/create")
    public ResponseEntity<String> createSider() {
        this.subCategoryConstructionTypeService.create();

        return ResponseEntity.ok("ok");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateSubCategoryRequest request) {

        UpdateSubCategoryCommand command = UpdateSubCategoryCommand.fromRequest(request, id);
        UpdateSubCategoryMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request) {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchSubCategoryQuery query = new GetSearchSubCategoryQuery(pageable, request.getFilter(), request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
