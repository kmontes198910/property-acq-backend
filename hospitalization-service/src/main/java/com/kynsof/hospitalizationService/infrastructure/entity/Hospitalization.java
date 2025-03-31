package com.kynsof.hospitalizationService.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "hospitalization")
public class Hospitalization {
    @Id
    @Column(name="id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patients patient;

    @OneToOne
    @JoinColumn(name = "emergency_case_id")
    private EmergencyCase emergencyCase; // Relación si proviene de Emergencia

    @ManyToOne
    @JoinColumn(name = "attending_doctor_id", nullable = false)
    private MedicalStaff attendingDoctor;

    private LocalDate admissionDate;
    private String assignedRoom;
    private String hospitalizationStatus; // Activa, Finalizada, En proceso
}
