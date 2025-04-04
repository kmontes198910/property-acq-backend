package com.kynsof.hospitalizationService.infrastructure.entity;

import com.kynsof.hospitalizationService.domain.dto.HospitalizationDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patients patient;

    @OneToOne
    @JoinColumn(name = "emergency_case_id")
    private EmergencyCase emergencyCase; // Relación si proviene de Emergencia

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attending_doctor_id", nullable = false)
    private MedicalStaff attendingDoctor;

    private LocalDate admissionDate;
    private String assignedRoom;

    private String hospitalizationStatus;

    public Hospitalization(HospitalizationDto dto) {
        this.id = dto.getId();
        this.patient = dto.getPatient() != null ? new Patients(dto.getPatient()) : null;
        this.emergencyCase = dto.getEmergencyCase() != null ? new EmergencyCase(dto.getEmergencyCase()) : null;
        this.attendingDoctor = dto.getAttendingDoctor() != null ? new MedicalStaff(dto.getAttendingDoctor()) : null;
        this.admissionDate = dto.getAdmissionDate();
        this.assignedRoom = dto.getAssignedRoom();
        this.hospitalizationStatus = dto.getHospitalizationStatus();
    }

    public HospitalizationDto toAggregate() {
        return new HospitalizationDto(
                id, 
                patient.toAggregate(), 
                emergencyCase.toAggregate(), 
                attendingDoctor.toAggregate(), 
                admissionDate, 
                assignedRoom, 
                hospitalizationStatus
        );
    }


}
