package com.kynsoft.medicaltest.infrastructure.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Entidad JPA que representa un tipo de examen de laboratorio en la base de datos
 */
@Entity
@Table(name = "lab_tests")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LabTestEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "code", nullable = false, unique = true)
    private String code;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @OneToMany(mappedBy = "labTest", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<LabTestParameterEntity> parameters = new ArrayList<>();
    
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    @Column(name = "created_by")
    private UUID createdBy;
    
    @Column(name = "updated_by")
    private UUID updatedBy;
    
    /**
     * Método helper para agregar un parámetro al examen
     * 
     * @param parameter El parámetro a agregar
     */
    public void addParameter(LabTestParameterEntity parameter) {
        parameters.add(parameter);
        parameter.setLabTest(this);
    }
    
    /**
     * Método helper para eliminar un parámetro del examen
     * 
     * @param parameter El parámetro a eliminar
     */
    public void removeParameter(LabTestParameterEntity parameter) {
        parameters.remove(parameter);
        parameter.setLabTest(null);
    }
}
