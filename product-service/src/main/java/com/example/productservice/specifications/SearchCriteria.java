package com.example.productservice.specifications;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchCriteria {
    private String key;
    private String value1;
    private String value2;
    private SearchOperation operation;

    public SearchCriteria(String key, String value1, String value2, SearchOperation operation) {
        this.key = key;
        this.value1 = value1;
        this.value2 = value2;
        this.operation = operation;
    }

    public SearchCriteria(String key, String value1, SearchOperation operation) {
        this.key = key;
        this.value1 = value1;
        this.operation = operation;
    }
}
