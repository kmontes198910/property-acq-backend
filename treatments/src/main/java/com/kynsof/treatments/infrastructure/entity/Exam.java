package com.kynsof.treatments.infrastructure.entity;

import com.kynsof.treatments.domain.dto.ExamDto;
import com.kynsof.treatments.domain.dto.enumDto.MedicalExamCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Exam {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "external_consultation_id")
    private ExternalConsultation externalConsultation;

    @Column(name = "exam_number", updatable = false, insertable = false)
    private int examNumber;

    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private MedicalExamCategory type;
    private String result;
    @Temporal(TemporalType.TIMESTAMP)
    private Date datePerformed;

    private String code;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        datePerformed = new Date();
    }

    public Exam(ExamDto examDto) {
        this.id = examDto.getId();
        this.name = examDto.getName();
        this.description = examDto.getDescription();
        this.type = examDto.getType();
        this.result = examDto.getResult();
        this.datePerformed = examDto.getDatePerformed() != null ? examDto.getDatePerformed() : null;
        this.code = examDto.getCode();
    }

    public ExamDto toAggregate() {
        return new ExamDto(id, name, description, type, result, datePerformed, code);
    }

}
