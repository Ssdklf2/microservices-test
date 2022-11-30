package com.example.productservice.controllers;

import com.example.productservice.models.DTO.ProductDto;
import com.example.productservice.models.DTO.ProductSorter;
import com.example.productservice.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static com.example.productservice.controllers.ProductController.checkValidationErrors;

@RestController
@RequestMapping("/sorter")
@Slf4j
public class SorterController {

    private final ProductService productService;

    @Autowired
    public SorterController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> sortProducts(
            @Valid @RequestBody ProductSorter productSorter,
            BindingResult result) {
        checkValidationErrors(result);
        return ResponseEntity.ok().body(productService.sort(productSorter));
    }
}
