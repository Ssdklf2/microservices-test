package com.example.productservice.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;

@Table
@Entity
@Getter
@Setter
@ToString
public class Product extends RepresentationModel<Product> {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;
    private String title;
    private String description;
    private BigDecimal price;
}
