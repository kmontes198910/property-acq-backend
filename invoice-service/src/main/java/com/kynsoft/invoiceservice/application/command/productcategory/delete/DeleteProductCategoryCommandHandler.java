package com.kynsoft.invoiceservice.application.command.productcategory.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsoft.invoiceservice.domain.service.IProductCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Manejador para eliminar una categoría de producto
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DeleteProductCategoryCommandHandler implements ICommandHandler<DeleteProductCategoryCommand, DeleteProductCategoryMessage> {

    private final IProductCategoryService productCategoryService;

    @Override
    @Transactional
    public DeleteProductCategoryMessage handle(DeleteProductCategoryCommand command) {
        log.info("Eliminando categoría de producto con ID: {}", command.getId());

        // Verificar que la categoría existe antes de eliminar
        if (!productCategoryService.existsById(command.getId())) {
            throw new BusinessNotFoundException(
                new GlobalBusinessException(DomainErrorMessage.BUSINESS_NOT_FOUND,
                new ErrorField("id", "Categoría no encontrada con ID: " + command.getId())));
        }

        // Eliminar la categoría
        productCategoryService.deleteById(command.getId());

        // Construir y devolver el mensaje de respuesta
        return DeleteProductCategoryMessage.builder()
                .id(command.getId())
                .message("Categoría eliminada exitosamente")
                .build();
    }
}
