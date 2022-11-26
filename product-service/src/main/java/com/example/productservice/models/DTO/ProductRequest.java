package com.example.productservice.models.DTO;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductRequest {
    @NotNull
    @Size(min = 3, max = 30)
    private String title;

    @NotNull
    private String description;

    @Min(0)
    @Max(Integer.MAX_VALUE)
    private BigDecimal price;
}

