//package com.kynsof.treatments.infrastructure.entity.specifications;
//
//import com.kynsof.treatments.infrastructure.entity.PatientVaccine;
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
//public class PatientVaccineSpecifications implements Specification<PatientVaccine> {
//    private final UUID patientId;
//
//    public PatientVaccineSpecifications(UUID patientId) {
//        this.patientId = patientId;
//    }
//
//    @Override
//    public Predicate toPredicate(Root<PatientVaccine> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//        List<Predicate> predicates = new ArrayList<>();
//
//        if (patientId != null) {
//            predicates.add(cb.equal(root.get("patient_id"), patientId));
//        }
//
//        return cb.and(predicates.toArray(Predicate[]::new));
//    }
//}
