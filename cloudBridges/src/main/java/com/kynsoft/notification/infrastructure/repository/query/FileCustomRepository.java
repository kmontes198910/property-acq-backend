package com.kynsoft.notification.infrastructure.repository.query;

import com.kynsoft.notification.domain.dto.FileMimeTypeCountDto;
import com.kynsoft.notification.domain.dto.FilePathCountDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@Transactional(readOnly = true)
public class FileCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public List<FilePathCountDto> countByPathAndBusinessId(UUID businessId) {
        String jpql = "SELECT a.path as path, COUNT(a) as count, SUM(a.size) as totalSize " +
                      "FROM AFile a " +
                      "WHERE a.businessId = :businessId " +
                      "GROUP BY a.path " +
                      "ORDER BY COUNT(a) DESC";
        
        Query query = entityManager.createQuery(jpql, Tuple.class);
        query.setParameter("businessId", businessId);
        
        List<Tuple> results = query.getResultList();
        List<FilePathCountDto> dtoList = new ArrayList<>();
        
        for (Tuple t : results) {
            String path = (String) t.get("path");
            Long count = ((Number) t.get("count")).longValue();
            Long totalSize = ((Number) t.get("totalSize")).longValue();
            // Convertir a GB dividiendo por 1024^3
            // Convertir bytes a megabits
            Double totalSizeMb = (totalSize * 8.0) / (1024.0 * 1024.0);
            
            dtoList.add(new FilePathCountDto(path, count, totalSizeMb));
        }
        
        return dtoList;
    }
    
    public Long countByBusinessId(UUID businessId) {
        String jpql = "SELECT COUNT(a) FROM AFile a WHERE a.businessId = :businessId";
        
        Query query = entityManager.createQuery(jpql);
        query.setParameter("businessId", businessId);
        
        return (Long) query.getSingleResult();
    }
    
    public Long calculateTotalSizeByBusinessId(UUID businessId) {
        String jpql = "SELECT COALESCE(SUM(a.size), 0) FROM AFile a WHERE a.businessId = :businessId";
        
        Query query = entityManager.createQuery(jpql);
        query.setParameter("businessId", businessId);
        
        return (Long) query.getSingleResult();
    }

    @SuppressWarnings("unchecked")
    public List<FileMimeTypeCountDto> countByMimeTypeAndBusinessId(UUID businessId) {
        String jpql = "SELECT a.mimeType as mimeType, COUNT(a) as count, SUM(a.size) as totalSize " +
                      "FROM AFile a " +
                      "WHERE a.businessId = :businessId " +
                      "GROUP BY a.mimeType " +
                      "ORDER BY COUNT(a) DESC";
        
        Query query = entityManager.createQuery(jpql, Tuple.class);
        query.setParameter("businessId", businessId);
        
        List<Tuple> results = query.getResultList();
        List<FileMimeTypeCountDto> dtoList = new ArrayList<>();
        
        for (Tuple t : results) {
            String mimeType = (String) t.get("mimeType");
            Long count = ((Number) t.get("count")).longValue();
            Long totalSize = ((Number) t.get("totalSize")).longValue();
            // Convertir bytes a megabits
            Double totalSizeMb = (totalSize * 8.0) / (1024.0 * 1024.0);
            
            dtoList.add(new FileMimeTypeCountDto(mimeType, count, totalSizeMb));
        }
        
        return dtoList;
    }
}