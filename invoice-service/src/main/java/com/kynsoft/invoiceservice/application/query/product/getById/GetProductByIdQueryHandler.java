package com.kynsoft.invoiceservice.application.query.product.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsoft.invoiceservice.infrastructure.entities.Product;
import com.kynsoft.invoiceservice.infrastructure.repository.query.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetProductByIdQueryHandler implements IQueryHandler<GetProductByIdQuery, ProductResponse> {

    private final ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public ProductResponse handle(GetProductByIdQuery query) {
        log.info("Finding product with ID: {}", query.getId());
        
        Optional<Product> productOpt = productRepository.findById(query.getId());
        
        if (productOpt.isEmpty()) {
            throw new BusinessNotFoundException(
                new GlobalBusinessException(DomainErrorMessage.BUSINESS_NOT_FOUND, 
                new ErrorField("id", "Producto no encontrado con ID: " + query.getId())));
        }
        
        return ProductResponse.fromEntity(productOpt.get());
    }
}