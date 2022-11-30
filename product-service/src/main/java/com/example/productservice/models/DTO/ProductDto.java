package com.example.productservice.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    @NotBlank
    @Size(min = 3, max = 30)
    private String title;

    @NotBlank
    private String description;

    @Min(0)
    @Max(Integer.MAX_VALUE)
    private int price;
}

