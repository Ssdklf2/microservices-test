package com.example.productservice.models.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {
    private String title;
    private String description;
    private BigDecimal price;
}

