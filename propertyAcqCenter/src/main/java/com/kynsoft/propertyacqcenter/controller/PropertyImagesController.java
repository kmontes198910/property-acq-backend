package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.propertyImages.create.CreatePropertyImagesCommand;
import com.kynsoft.propertyacqcenter.application.command.propertyImages.create.CreatePropertyImagesMessage;
import com.kynsoft.propertyacqcenter.application.command.propertyImages.create.CreatePropertyImagesRequest;
import com.kynsoft.propertyacqcenter.application.command.propertyImages.delete.DeletePropertyImagesCommand;
import com.kynsoft.propertyacqcenter.application.command.propertyImages.delete.DeletePropertyImagesMessage;
import com.kynsoft.propertyacqcenter.application.command.propertyImages.update.UpdatePropertyImagesCommand;
import com.kynsoft.propertyacqcenter.application.command.propertyImages.update.UpdatePropertyImagesMessage;
import com.kynsoft.propertyacqcenter.application.command.propertyImages.update.UpdatePropertyImagesRequest;
import com.kynsoft.propertyacqcenter.application.query.propertyImages.getById.GetByIdPropertyImagesQuery;
import com.kynsoft.propertyacqcenter.application.query.propertyImages.search.GetSearchPropertyImagesQuery;
import com.kynsoft.propertyacqcenter.application.response.PropertyImagesResponse;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/property-images")
public class PropertyImagesController {

    private final IMediator mediator;

    public PropertyImagesController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<CreatePropertyImagesMessage> createAllergy(@RequestBody CreatePropertyImagesRequest request) {
        CreatePropertyImagesCommand createCommand = CreatePropertyImagesCommand.fromRequest(request);
        CreatePropertyImagesMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdatePropertyImagesRequest request) {

        UpdatePropertyImagesCommand command = UpdatePropertyImagesCommand.fromRequest(request, id);
        UpdatePropertyImagesMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeletePropertyImagesCommand command = new DeletePropertyImagesCommand(id);
        DeletePropertyImagesMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        GetByIdPropertyImagesQuery query = new GetByIdPropertyImagesQuery(id);
        PropertyImagesResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchPropertyImagesQuery query = new GetSearchPropertyImagesQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
