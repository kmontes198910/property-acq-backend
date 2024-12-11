package com.kynsof.treatments.infrastructure.entity;

import com.kynsof.treatments.domain.dto.OptometryExamDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "optometry_exam")
public class OptometryExam {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "external_consultation_id")
    private ExternalConsultation externalConsultation;

    @Column(name = "sphere_od")
    private String sphereOd;

    @Column(name = "cylinder_od")
    private String cylinderOd;

    @Column(name = "cylinder_add")
    private String cylinderAdd;

    @Column(name = "sphere_add")
    private String sphereAdd;

    @Column(name = "avcc_add")
    private String avccAdd;

    @Column(name = "axis_od")
    private String axisOd;

    @Column(name = "avsc_od")
    private String avscOd;

    @Column(name = "avcc_od")
    private String avccOd;

    @Column(name = "sphere_oi")
    private String sphereOi;

    @Column(name = "cylinder_oi")
    private String cylinderOi;

    @Column(name = "axis_oi")
    private String axisOi;

    @Column(name = "avsc_oi")
    private String avscOi;

    @Column(name = "avcc_oi")
    private String avccOi;

    @Column(name = "add_power")
    private String addPower;

    @Column(name = "dp")
    private String dp;

    @Column(name = "dv")
    private String dv;

    @Column(name = "filter")
    private String filter;

    @Column(name = "is_current", nullable = false)
    private boolean isCurrent;
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public OptometryExam(OptometryExamDto dto) {
        this.sphereOd = dto.getSphereOd();
        this.cylinderOd = dto.getCylinderOd();
        this.axisOd = dto.getAxisOd();
        this.avscOd = dto.getAvscOd();
        this.avccOd = dto.getAvccOd();
        this.sphereOi = dto.getSphereOi();
        this.cylinderOi = dto.getCylinderOi();
        this.axisOi = dto.getAxisOi();
        this.avscOi = dto.getAvscOi();
        this.avccOi = dto.getAvccOi();
        this.addPower = dto.getAddPower();
        this.dp = dto.getDp();
        this.dv = dto.getDv();
        this.filter = dto.getFilter();
    }

    // Método para convertir a DTO (si es necesario)
    public OptometryExamDto toAggregate() {
        return new OptometryExamDto(
                this.id,
                this.sphereOd,
                this.cylinderOd,
                this.axisOd,
                this.avscOd,
                this.avccOd,
                this.sphereOi,
                this.cylinderOi,
                this.axisOi,
                this.avscOi,
                this.avccOi,
                this.addPower,
                this.dp,
                this.dv,
                this.filter,
                this.isCurrent,
                this.avccAdd,
                this.sphereAdd,
                this.cylinderAdd
        );
    }
}