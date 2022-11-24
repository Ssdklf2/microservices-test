package com.example.productservice.controllers;

import com.example.productservice.models.DTO.ProductDto;
import com.example.productservice.models.DTO.ProductRequest;
import com.example.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductDto> createMessage(@RequestBody ProductRequest request,
                                                    UriComponentsBuilder ucb) {
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

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable String id) {
        return ResponseEntity.ok().body(productService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateMessage(
            @RequestBody ProductRequest productRequest,
            @PathVariable String id) {
        return ResponseEntity.ok().body(productService.update(productRequest, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable String id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
