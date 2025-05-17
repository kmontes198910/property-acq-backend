package com.kynsoft.invoiceservice.controller;

import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.invoiceservice.application.command.productcategory.create.CreateProductCategoryCommand;
import com.kynsoft.invoiceservice.application.command.productcategory.create.CreateProductCategoryMessage;
import com.kynsoft.invoiceservice.application.command.productcategory.create.CreateProductCategoryRequest;
import com.kynsoft.invoiceservice.application.command.productcategory.update.UpdateProductCategoryCommand;
import com.kynsoft.invoiceservice.application.command.productcategory.update.UpdateProductCategoryMessage;
import com.kynsoft.invoiceservice.application.command.productcategory.update.UpdateProductCategoryRequest;
import com.kynsoft.invoiceservice.application.query.productcategory.get.GetProductCategoryByIdQuery;
import com.kynsoft.invoiceservice.application.query.productcategory.get.ProductCategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/product-categories")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Categorías de Productos", description = "API para la gestión de categorías de productos")
public class ProductCategoryController {

    private final IMediator mediator;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear una nueva categoría de producto", 
               description = "Registra una nueva categoría de producto en el sistema")
    @ApiResponse(responseCode = "201", 
                 description = "Categoría creada correctamente",
                 content = @Content(mediaType = "application/json", 
                                   schema = @Schema(implementation = CreateProductCategoryMessage.class)))
    public ResponseEntity<CreateProductCategoryMessage> createProductCategory(
            @Parameter(description = "Datos de la categoría a crear", required = true) 
            @RequestBody CreateProductCategoryRequest request) {
        
        log.info("Creando nueva categoría de producto: {}", request.getName());
        
        CreateProductCategoryCommand command = CreateProductCategoryCommand.fromRequest(request);
        CreateProductCategoryMessage response = mediator.send(command);
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener categoría de producto por ID", 
               description = "Recupera una categoría de producto específica por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                     description = "Categoría encontrada",
                     content = @Content(mediaType = "application/json", 
                                       schema = @Schema(implementation = ProductCategoryResponse.class))),
        @ApiResponse(responseCode = "404", 
                     description = "Categoría no encontrada",
                     content = @Content)
    })
    public ResponseEntity<ProductCategoryResponse> getProductCategoryById(
            @Parameter(description = "ID de la categoría a buscar", required = true) 
            @PathVariable UUID id) {
        
        log.info("Buscando categoría de producto con ID: {}", id);
        
        GetProductCategoryByIdQuery query = new GetProductCategoryByIdQuery(id);
        ProductCategoryResponse response = mediator.send(query);
        
        return ResponseEntity.ok(response);
    }

    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una categoría de producto", 
               description = "Actualiza los datos de una categoría de producto existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                     description = "Categoría actualizada correctamente",
                     content = @Content(mediaType = "application/json", 
                                       schema = @Schema(implementation = UpdateProductCategoryMessage.class))),
        @ApiResponse(responseCode = "404", 
                     description = "Categoría no encontrada",
                     content = @Content)
    })
    public ResponseEntity<UpdateProductCategoryMessage> updateProductCategory(
            @Parameter(description = "ID de la categoría a actualizar", required = true) 
            @PathVariable UUID id, 
            @Parameter(description = "Datos actualizados de la categoría", required = true) 
            @RequestBody UpdateProductCategoryRequest request) {
        
        log.info("Actualizando categoría de producto con ID: {}", id);
        
        request.setId(id);
        UpdateProductCategoryCommand command = UpdateProductCategoryCommand.fromRequest(request);
        UpdateProductCategoryMessage response = mediator.send(command);
        
        return ResponseEntity.ok(response);
    }
}
