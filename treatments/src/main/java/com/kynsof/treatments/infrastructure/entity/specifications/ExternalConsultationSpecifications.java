//package com.kynsof.treatments.infrastructure.entity.specifications;
//
//import com.kynsof.treatments.infrastructure.entity.ExternalConsultation;
//import jakarta.persistence.criteria.CriteriaBuilder;
//import jakarta.persistence.criteria.CriteriaQuery;
//import jakarta.persistence.criteria.Predicate;
//import jakarta.persistence.criteria.Root;
//import org.springframework.data.jpa.domain.Specification;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//public class ExternalConsultationSpecifications implements Specification<ExternalConsultation> {
//    private final UUID patientId;
//    private final UUID doctorId;
//
//    public ExternalConsultationSpecifications(UUID patientId, UUID doctorId) {
//        this.patientId = patientId;
//        this.doctorId = doctorId;
//    }
//
//    @Override
//    public Predicate toPredicate(Root<ExternalConsultation> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//        List<Predicate> predicates = new ArrayList<>();
//
//        if (patientId != null) {
//            predicates.add(cb.equal(root.get("patient_id"), patientId));
//        }
//
//        if (doctorId != null) {
//            predicates.add(cb.equal(root.get("doctor_id"), doctorId));
//        }
//
//        return cb.and(predicates.toArray(Predicate[]::new));
//    }
//}
