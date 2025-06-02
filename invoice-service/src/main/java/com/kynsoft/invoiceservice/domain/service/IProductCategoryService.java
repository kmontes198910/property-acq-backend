package com.kynsoft.invoiceservice.domain.service;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.invoiceservice.application.query.productcategory.get.ProductCategoryDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * Interfaz que define las operaciones de servicio para la entidad ProductCategory
 */
public interface IProductCategoryService {
    /**
     * Crea una nueva categoría de producto
     *
     * @param categoryDto Datos de la categoría a crear
     * @return ID de la categoría creada
     */
    UUID create(ProductCategoryDto categoryDto);

    /**
     * Actualiza una categoría existente
     *
     * @param categoryDto Datos actualizados de la categoría
     */
    void update(ProductCategoryDto categoryDto);

    /**
     * Busca una categoría por su ID
     *
     * @param id ID de la categoría a buscar
     * @return Datos de la categoría encontrada
     */
    ProductCategoryDto findById(UUID id);
    
    /**
     * Busca categorías por nombre (búsqueda parcial)
     *
     * @param name Nombre o parte del nombre a buscar
     * @return Lista de categorías que coinciden con el criterio
     */
    List<ProductCategoryDto> searchByName(String name);
    
    /**
     * Obtiene todas las categorías activas
     *
     * @return Lista de categorías activas
     */
    List<ProductCategoryDto> findAllActive();
    
    /**
     * Realiza una búsqueda avanzada con filtros y paginación
     * 
     * @param pageable Configuración de paginación
     * @param filterCriteria Lista de criterios de filtrado
     * @return Respuesta paginada con los resultados de la búsqueda
     */
    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);

    void deleteById(UUID id);
}
