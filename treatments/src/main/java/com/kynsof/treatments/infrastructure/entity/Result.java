package com.kynsof.treatments.infrastructure.entity;

import com.kynsof.treatments.domain.dto.ResultDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Result {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "external_consultation_id")
    private ExternalConsultation externalConsultation;

    private String type;
    
    private String url;
    
    @Column(nullable = false)
    private String uploadedById;
    
    @Column(nullable = false)
    private String uploadedByUsername;
    
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    // Constructor para crear un Result a partir de un DTO
    public Result(ResultDto resultDto) {
        this.id = resultDto.getId();
        this.type = resultDto.getType();
        this.url = resultDto.getUrl();
        this.uploadedById = resultDto.getUploadedById();
        this.uploadedByUsername = resultDto.getUploadedByUsername();
        this.externalConsultation = resultDto.getExternalConsultation() != null ? 
                new ExternalConsultation(resultDto.getExternalConsultation()) : null;
    }
    
    // Método para convertir la entidad a DTO
    public ResultDto toAggregate() {
        return new ResultDto(
                id,
                type,
                url,
                uploadedById,
                uploadedByUsername,
                null
        );
    }
}