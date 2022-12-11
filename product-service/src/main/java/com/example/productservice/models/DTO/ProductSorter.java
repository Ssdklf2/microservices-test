package com.example.productservice.models.DTO;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

@Data
public class ProductSorter {
    @Size(max = 30)
    private String title;

    @Max(Integer.MAX_VALUE)
    private Integer priceFrom;

    @Max(Integer.MAX_VALUE)
    private Integer priceTo;
}
