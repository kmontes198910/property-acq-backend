package com.kynsoft.invoiceservice.controller;

import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.invoiceservice.application.command.product.create.CreateProductCommand;
import com.kynsoft.invoiceservice.application.command.product.create.CreateProductMessage;
import com.kynsoft.invoiceservice.application.command.product.create.CreateProductRequest;
import com.kynsoft.invoiceservice.application.command.product.update.UpdateProductCommand;
import com.kynsoft.invoiceservice.application.command.product.update.UpdateProductMessage;
import com.kynsoft.invoiceservice.application.command.product.update.UpdateProductRequest;
import com.kynsoft.invoiceservice.application.query.product.getById.GetProductByIdQuery;
import com.kynsoft.invoiceservice.application.query.product.getById.ProductResponse;

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
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Productos", description = "API para la gestión de productos")
public class ProductController {

    private final IMediator mediator;
    private static final String USER_ID_HEADER = "X-User-ID";

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear un nuevo producto", 
               description = "Registra un nuevo producto en el sistema")
    @ApiResponse(responseCode = "201", 
                 description = "Producto creado correctamente",
                 content = @Content(mediaType = "application/json", 
                                   schema = @Schema(implementation = CreateProductMessage.class)))
    public ResponseEntity<CreateProductMessage> createProduct(
            @Parameter(description = "Datos del producto a crear", required = true) 
            @RequestBody CreateProductRequest request,
            @RequestHeader(value = USER_ID_HEADER, required = false) String userId) {
        
        log.info("Creando nuevo producto: {}", request.getName());
        
        UUID userUuid = userId != null ? UUID.fromString(userId) : null;
        CreateProductCommand command = CreateProductCommand.fromRequest(request, userUuid);
        CreateProductMessage response = mediator.send(command);
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar un producto", 
               description = "Actualiza los datos de un producto existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                     description = "Producto actualizado correctamente",
                     content = @Content(mediaType = "application/json", 
                                       schema = @Schema(implementation = UpdateProductMessage.class))),
        @ApiResponse(responseCode = "404", 
                     description = "Producto no encontrado",
                     content = @Content)
    })
    public ResponseEntity<UpdateProductMessage> updateProduct(
            @Parameter(description = "ID del producto a actualizar", required = true) 
            @PathVariable UUID id,
            @Parameter(description = "Datos actualizados del producto", required = true) 
            @RequestBody UpdateProductRequest request,
            @RequestHeader(value = USER_ID_HEADER, required = false) String userId) {
        
        log.info("Actualizando producto con ID: {}", id);
        
        UUID userUuid = userId != null ? UUID.fromString(userId) : null;
        UpdateProductCommand command = UpdateProductCommand.fromRequest(request, id, userUuid);
        UpdateProductMessage response = mediator.send(command);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID", 
               description = "Recupera un producto específico por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                     description = "Producto encontrado",
                     content = @Content(mediaType = "application/json", 
                                       schema = @Schema(implementation = ProductResponse.class))),
        @ApiResponse(responseCode = "404", 
                     description = "Producto no encontrado",
                     content = @Content)
    })
    public ResponseEntity<ProductResponse> getProductById(
            @Parameter(description = "ID del producto a buscar", required = true) 
            @PathVariable UUID id) {
        
        log.info("Buscando producto con ID: {}", id);
        
        GetProductByIdQuery query = new GetProductByIdQuery(id);
        ProductResponse response = mediator.send(query);
        
        return ResponseEntity.ok(response);
    }
}