package com.example.productservice.models.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@Getter
@Setter
@ToString
public class ProductResponse extends RepresentationModel<ProductResponse> {
    @ToString.Exclude
    private UUID id;
    private String title;
    private String description;
    private int price;
}
