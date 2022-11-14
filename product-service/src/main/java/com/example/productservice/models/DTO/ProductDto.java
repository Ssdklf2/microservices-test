package com.example.productservice.models.DTO;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ProductDto {
    private UUID id;
    private String title;
    private String description;
    private BigDecimal price;
}
