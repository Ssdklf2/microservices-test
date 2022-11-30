package com.example.productservice.specifications;

import com.example.productservice.models.Product;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ProductSpecification implements Specification<Product> {

    private final List<SearchCriteria> list;

    public ProductSpecification() {
        this.list = new ArrayList<>();
    }

    public void addCriteria(SearchCriteria searchCriteria) {
        list.add(searchCriteria);
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        for (SearchCriteria criteria : list) {
            SearchOperation operation = criteria.getOperation();
            switchOperation(root, builder, predicates, criteria, operation);
        }
        return builder.and(predicates.toArray(new Predicate[0]));
    }

    private static void switchOperation(Root<Product> root, CriteriaBuilder builder,
                                        List<Predicate> predicates, SearchCriteria criteria,
                                        SearchOperation operation) {
        String key = criteria.getKey();
        switch (operation) {
            case LIKE:
                predicates.add(builder.like(
                        root.get(key), "%" + criteria.getValue1() + "%"));
                break;
            case BETWEEN:
                predicates.add(builder.between(root.get(key),
                        Integer.valueOf(criteria.getValue1()),
                        Integer.valueOf(criteria.getValue2())));
                break;
            case GREATER_THAN:
                predicates.add(builder.greaterThanOrEqualTo(
                        root.get(key), Integer.valueOf(criteria.getValue1())));
                break;
            case LESS_THAN:
                predicates.add(builder.lessThanOrEqualTo(
                        root.get(key), Integer.valueOf(criteria.getValue1())));
                break;
        }
    }
}
