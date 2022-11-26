package com.example.productservice.controllers;

import com.example.productservice.exceptions.ValidationException;
import com.example.productservice.models.DTO.ProductDto;
import com.example.productservice.models.DTO.ProductRequest;
import com.example.productservice.models.DTO.ProductSorter;
import com.example.productservice.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {

    ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductDto> createMessage(
            @Valid @RequestBody ProductRequest request,
            UriComponentsBuilder ucb,
            BindingResult result) {
        checkValidationErrors(result);
        ProductDto productDto = productService.saveProduct(request);
        return ResponseEntity
                .created(ucb
                        .path("products").path(productDto.getId().toString())
                        .build().toUri())
                .body(productDto);
    }

    @GetMapping
    public ResponseEntity<List<ProductRequest>> getList() {
        return ResponseEntity.ok().body(productService.getList());
    }

    @GetMapping("/{pageNumber}/{pageSize}")
    public ResponseEntity<List<ProductRequest>> getPages(@PathVariable Integer pageNumber,
                                                         @PathVariable Integer pageSize) {
        return ResponseEntity.ok().body(productService.getPages(pageNumber, pageSize));
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable String id) {
        return ResponseEntity.ok().body(productService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateMessage(
            @Valid @RequestBody ProductRequest productRequest,
            @PathVariable String id,
            BindingResult result) {
        checkValidationErrors(result);
        return ResponseEntity.ok().body(productService.update(productRequest, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable String id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/sort")
    public ResponseEntity<List<ProductRequest>> sortProducts(
            @Valid @RequestBody ProductSorter productSorter, BindingResult result
    ) {
        checkValidationErrors(result);
        return ResponseEntity.ok().body(productService.sort(productSorter));
    }

    private static void checkValidationErrors(BindingResult result) {
        List<ObjectError> errors = result.getAllErrors();
        var builder = new StringBuilder();
        for (int i = 0; i < errors.size(); i++) {
            log.warn(String.valueOf(errors.get(i)));
            builder.append(errors.get(i).getDefaultMessage());
            if (i + 1 != errors.size()) builder.append(", ");
        }
        if (result.hasErrors()) {
            throw new ValidationException("parameter errors: " + builder);
        }
    }
}
