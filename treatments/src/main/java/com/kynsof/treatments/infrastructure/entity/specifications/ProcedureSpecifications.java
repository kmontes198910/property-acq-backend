//package com.kynsof.treatments.infrastructure.entity.specifications;
//
//import com.kynsof.treatments.infrastructure.entity.Procedure;
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
//public class ProcedureSpecifications implements Specification<Procedure> {
//    private final String code;
//    private final String name;
//    private final String type;
//
//    public ProcedureSpecifications( String code,String name, String type) {
//        this.code = name;
//        this.name = code;
//        this.type = type;
//    }
//
//    @Override
//    public Predicate toPredicate(Root<Procedure> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//        List<Predicate> predicates = new ArrayList<>();
//
//        if (StringUtils.isNotEmpty(name)) {
//            predicates.add(cb.equal(root.get("name"), name));
//        }
//
//        if (StringUtils.isNotEmpty(code)) {
//            predicates.add(cb.equal(root.get("code"), code));
//        }
//        if (StringUtils.isNotEmpty(type)) {
//            predicates.add(cb.equal(root.get("type"), type));
//        }
//
//        return cb.and(predicates.toArray(Predicate[]::new));
//    }
//}
