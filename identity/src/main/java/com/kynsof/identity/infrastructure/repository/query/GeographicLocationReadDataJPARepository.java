package com.kynsof.identity.infrastructure.repository.query;

import com.kynsof.identity.infrastructure.identity.GeographicLocation;
import com.kynsof.identity.infrastructure.identity.projection.ProvinceCantonParishProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface GeographicLocationReadDataJPARepository extends JpaRepository<GeographicLocation, UUID>, JpaSpecificationExecutor<GeographicLocation> {
    Page<GeographicLocation> findAll(Specification specification, Pageable pageable);
    @Query(value = """
        SELECT
            p.id   AS provinceId,
            p.name AS provinceName,
            p.type AS provinceType,
        
            c.id   AS cantonId,
            c.name AS cantonName,
            c.type AS cantonType,
        
            pa.id  AS parishId,
            pa.name AS parishName,
            pa.type AS parishType
        FROM geographiclocation p
        LEFT JOIN geographiclocation c
               ON c.fk_pk_geographic_location = p.id
        LEFT JOIN geographiclocation pa
               ON pa.fk_pk_geographic_location = c.id
        WHERE p.type = 'PROVINCE';
        """,
            nativeQuery = true)
    List<ProvinceCantonParishProjection> findProvincesWithCantonsAndParishes();
}
