package com.example.productservice.controllers;

import com.example.productservice.exceptions.ValidationException;
import com.example.productservice.models.DTO.ProductDto;
import com.example.productservice.models.DTO.ProductResponse;
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

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createMessage(
            @Valid @RequestBody ProductDto request,
            BindingResult result,
            UriComponentsBuilder ucb) {
        checkValidationErrors(result);
        ProductResponse productResponse = productService.saveProduct(request);
        return ResponseEntity
                .created(ucb
                        .path("products").path(productResponse.getId().toString())
                        .build().toUri())
                .body(productResponse);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getList() {
        return ResponseEntity.ok().body(productService.getList());
    }

    @GetMapping("/{pageNumber}/{pageSize}")
    public ResponseEntity<List<ProductDto>> getPages(@PathVariable Integer pageNumber,
                                                     @PathVariable Integer pageSize) {
        return ResponseEntity.ok().body(productService.getPages(pageNumber, pageSize));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable String id) {
        return ResponseEntity.ok().body(productService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateMessage(
            @Valid @RequestBody ProductDto productDto,
            BindingResult result,
            @PathVariable String id) {
        checkValidationErrors(result);
        return ResponseEntity.ok().body(productService.update(productDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable String id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    protected static void checkValidationErrors(BindingResult result) {
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
