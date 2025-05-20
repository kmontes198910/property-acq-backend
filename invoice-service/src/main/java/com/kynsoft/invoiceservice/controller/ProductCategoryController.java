package com.kynsoft.invoiceservice.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.invoiceservice.application.command.productcategory.create.CreateProductCategoryCommand;
import com.kynsoft.invoiceservice.application.command.productcategory.create.CreateProductCategoryMessage;
import com.kynsoft.invoiceservice.application.command.productcategory.create.CreateProductCategoryRequest;
import com.kynsoft.invoiceservice.application.command.productcategory.update.UpdateProductCategoryCommand;
import com.kynsoft.invoiceservice.application.command.productcategory.update.UpdateProductCategoryMessage;
import com.kynsoft.invoiceservice.application.command.productcategory.update.UpdateProductCategoryRequest;
import com.kynsoft.invoiceservice.application.command.productcategory.delete.DeleteProductCategoryCommand;
import com.kynsoft.invoiceservice.application.command.productcategory.delete.DeleteProductCategoryMessage;
import com.kynsoft.invoiceservice.application.query.productcategory.get.GetProductCategoryByIdQuery;
import com.kynsoft.invoiceservice.application.query.productcategory.get.ProductCategoryResponse;
import com.kynsoft.invoiceservice.application.query.productcategory.search.SearchProductCategoryAdvancedQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/product-categories")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Categorías de Productos", description = "API para la gestión de categorías de productos")
public class ProductCategoryController {

    private final IMediator mediator;
    private static final String USER_ID_HEADER = "X-User-ID";

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
            @RequestBody CreateProductCategoryRequest request,
            @RequestHeader(value = USER_ID_HEADER, required = false) String userId) {
        
        log.info("Creando nueva categoría de producto: {}", request.getName());
        
        UUID userUuid = userId != null ? UUID.fromString(userId) : null;
        CreateProductCategoryCommand command = CreateProductCategoryCommand.fromRequest(request, userUuid);
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

    
    @PatchMapping("/{id}")
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
            @RequestBody UpdateProductCategoryRequest request,
            @RequestHeader(value = USER_ID_HEADER, required = false) String userId) {
        
        log.info("Actualizando categoría de producto con ID: {}", id);
        
        UUID userUuid = userId != null ? UUID.fromString(userId) : null;
        request.setId(id);
        UpdateProductCategoryCommand command = UpdateProductCategoryCommand.fromRequest(request, userUuid);
        UpdateProductCategoryMessage response = mediator.send(command);
        
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una categoría de producto", 
               description = "Elimina una categoría de producto existente por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                     description = "Categoría eliminada correctamente",
                     content = @Content(mediaType = "application/json", 
                                       schema = @Schema(implementation = DeleteProductCategoryMessage.class))),
        @ApiResponse(responseCode = "404", 
                     description = "Categoría no encontrada",
                     content = @Content)
    })
    public ResponseEntity<DeleteProductCategoryMessage> deleteProductCategory(
            @Parameter(description = "ID de la categoría a eliminar", required = true) 
            @PathVariable UUID id,
            @RequestHeader(value = USER_ID_HEADER, required = false) String userId) {
        
        log.info("Eliminando categoría de producto con ID: {}", id);
        
        UUID userUuid = userId != null ? UUID.fromString(userId) : null;
        DeleteProductCategoryCommand command = DeleteProductCategoryCommand.fromRequest(id, userUuid);
        DeleteProductCategoryMessage response = mediator.send(command);
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    @Operation(summary = "Búsqueda avanzada de categorías de productos", 
               description = "Busca categorías de productos con filtros avanzados y paginación")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                     description = "Búsqueda exitosa",
                     content = @Content(mediaType = "application/json", 
                                       schema = @Schema(implementation = PaginatedResponse.class)))
    })
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request) {
        log.info("Realizando búsqueda avanzada de categorías de productos");
        
        Pageable pageable = PageableUtil.createPageable(request);
        SearchProductCategoryAdvancedQuery query = new SearchProductCategoryAdvancedQuery(
                pageable, 
                request.getFilter(), 
                request.getQuery()
        );
        
        PaginatedResponse response = mediator.send(query);
        return ResponseEntity.ok(response);
    }
}
