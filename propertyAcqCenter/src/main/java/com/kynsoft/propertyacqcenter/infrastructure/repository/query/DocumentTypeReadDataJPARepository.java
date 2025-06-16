package com.kynsoft.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.propertyacqcenter.infrastructure.entity.DocumentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
@Repository
public interface DocumentTypeReadDataJPARepository extends JpaRepository<DocumentType, UUID>, JpaSpecificationExecutor<DocumentType> {
    @Override
    Page<DocumentType> findAll(Specification<DocumentType> specification, Pageable pageable);

    @Query("SELECT COUNT(dt) FROM DocumentType dt WHERE dt.code = :code AND dt.id <> :id")
    int countByCode(@Param("code") String code, @Param("id") UUID id);
}