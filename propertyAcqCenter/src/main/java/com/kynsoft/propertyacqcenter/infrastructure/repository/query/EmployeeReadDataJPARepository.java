package com.kynsoft.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.propertyacqcenter.infrastructure.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;

@Repository
public interface EmployeeReadDataJPARepository extends JpaRepository<Employee, UUID>, JpaSpecificationExecutor<Employee> {
    @EntityGraph(attributePaths = {"business", "roles"})
    @Override
    Page<Employee> findAll(Specification<Employee> specification, Pageable pageable);

    Optional<Employee> findByEmail(String email);

    @Query("SELECT COUNT(b) FROM Employee b WHERE b.email = :email AND b.id <> :id")
    Long countByEmailAndNotId(@Param("email") String email, @Param("id") UUID id);

    @EntityGraph(attributePaths = {"business", "roles"})
    @Override
    Optional<Employee> findById(UUID id);

    long countByEmail(String email);
}
