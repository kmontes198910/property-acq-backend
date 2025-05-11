package com.kynsoft.invoiceservice.application.command.product.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.invoiceservice.domain.exception.BusinessException;
import com.kynsoft.invoiceservice.domain.exception.DomainErrorInvoiceMessage;
import com.kynsoft.invoiceservice.infrastructure.entities.Product;
import com.kynsoft.invoiceservice.infrastructure.entities.ProductCategory;
import com.kynsoft.invoiceservice.infrastructure.repository.command.ProductWriteRepository;
import com.kynsoft.invoiceservice.infrastructure.repository.query.ProductCategoryRepository;
import com.kynsoft.invoiceservice.infrastructure.repository.query.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateProductCommandHandler implements ICommandHandler<CreateProductCommand> {

    private final ProductWriteRepository productWriteRepository;
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;

    @Override
    @Transactional
    public void handle(CreateProductCommand command) {
        log.info("Creating new product: {}", command.getName());
        
        // Verificar si ya existe un producto con el mismo código principal
        Optional<Product> existingProduct = productRepository.findByMainCode(command.getMainCode());
        if (existingProduct.isPresent()) {
            throw new BusinessException(DomainErrorInvoiceMessage.PRODUCT_CODE_ALREADY_EXISTS,
                "Ya existe un producto con el código principal: " + command.getMainCode());
        }
        
        // Verificar si existe la categoría
        ProductCategory category = null;
        if (command.getCategoryId() != null) {
            category = productCategoryRepository.findById(command.getCategoryId())
                .orElseThrow(() -> new BusinessException(DomainErrorInvoiceMessage.NOT_FOUND,
                    "No se encontró la categoría de producto con ID: " + command.getCategoryId()));
        }
        
        // Crear el nuevo producto
        Product product = Product.builder()
                .id(UUID.randomUUID())
                .name(command.getName())
                .description(command.getDescription())
                .mainCode(command.getMainCode())
                .auxiliaryCode(command.getAuxiliaryCode())
                .price(command.getPrice())
                .stock(command.getStock())
                .taxCode(command.getTaxCode())
                .taxPercentage(command.getTaxPercentage())
                .isService(command.getIsService())
                .status(true) // Por defecto, un nuevo producto está activo
                .category(category)
                .build();
        
        Product savedProduct = productWriteRepository.save(product);
        
        // Asignamos el ID generado al comando para que esté disponible en el mensaje de respuesta
        command.setId(savedProduct.getId());
    }
}