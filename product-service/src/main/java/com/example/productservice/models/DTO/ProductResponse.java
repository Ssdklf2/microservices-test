package com.example.productservice.models.DTO;

import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
public class ProductResponse {
    @ToString.Exclude
    private UUID id;
    private String title;
    private String description;
    private int price;
}
