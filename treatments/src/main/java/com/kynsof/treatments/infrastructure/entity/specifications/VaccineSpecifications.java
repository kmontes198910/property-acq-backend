//package com.kynsof.treatments.infrastructure.entity.specifications;
//
//import com.kynsof.treatments.infrastructure.entity.Cie10;
//import jakarta.persistence.criteria.CriteriaBuilder;
//import jakarta.persistence.criteria.CriteriaQuery;
//import jakarta.persistence.criteria.Predicate;
//import jakarta.persistence.criteria.Root;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.data.jpa.domain.Specification;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class VaccineSpecifications implements Specification<Cie10> {
//    private final String code;
//    private final String description;
//
//    public VaccineSpecifications(String name, String code) {
//        this.code = name;
//        this.description = code;
//    }
//
//    @Override
//    public Predicate toPredicate(Root<Cie10> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//        List<Predicate> predicates = new ArrayList<>();
//
//        if (StringUtils.isNotEmpty(description)) {
//            predicates.add(cb.equal(root.get("name"), description));
//        }
//
//        if (StringUtils.isNotEmpty(code)) {
//            predicates.add(cb.equal(root.get("description"), code));
//        }
//
//        return cb.and(predicates.toArray(Predicate[]::new));
//    }
//}
