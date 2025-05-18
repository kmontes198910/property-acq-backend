package com.kynsoft.invoiceservice.application.command.product.update;

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

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateProductCommandHandler implements ICommandHandler<UpdateProductCommand> {

    private final ProductRepository productRepository;
    private final ProductWriteRepository productWriteRepository;
    private final ProductCategoryRepository productCategoryRepository;

    @Override
    @Transactional
    public void handle(UpdateProductCommand command) {
        log.info("Updating product with ID: {}", command.getProductId());
        
        // Obtener el producto a actualizar
        Product product = productRepository.findById(command.getProductId())
                .orElseThrow(() -> new BusinessException(
                    DomainErrorInvoiceMessage.PRODUCT_NOT_FOUND, 
                    "Producto no encontrado con ID: " + command.getProductId()));
        
        // Verificar si se está cambiando el código principal y si el nuevo código ya existe
        if (!product.getMainCode().equals(command.getMainCode())) {
            Optional<Product> existingProduct = productRepository.findByMainCode(command.getMainCode());
            if (existingProduct.isPresent() && !existingProduct.get().getId().equals(command.getProductId())) {
                throw new BusinessException(DomainErrorInvoiceMessage.PRODUCT_CODE_ALREADY_EXISTS,
                    "Ya existe un producto con el código principal: " + command.getMainCode());
            }
        }
        
        // Actualizar la categoría si se proporciona
        if (command.getCategoryId() != null) {
            ProductCategory category = productCategoryRepository.findById(command.getCategoryId())
                .orElseThrow(() -> new BusinessException(DomainErrorInvoiceMessage.NOT_FOUND,
                    "No se encontró la categoría de producto con ID: " + command.getCategoryId()));
            product.setCategory(category);
        }
        
        // Actualizar los campos del producto
        product.setName(command.getName());
        product.setDescription(command.getDescription());
        product.setMainCode(command.getMainCode());
        product.setAuxiliaryCode(command.getAuxiliaryCode());
        product.setPrice(command.getPrice());
        product.setStock(command.getStock());
        product.setTaxCode(command.getTaxCode());
        product.setTaxPercentage(command.getTaxPercentage());
        product.setIsService(command.getIsService());
        product.setStatus(command.getStatus());
        product.setUpdatedBy(command.getUpdatedBy()); // Agregar campo de auditoría
        
        // Guardar los cambios
        productWriteRepository.save(product);
    }
}