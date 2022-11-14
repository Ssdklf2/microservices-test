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
@RequestMapping("/product")
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
                        .path("product")
                        .path(productDto.getId().toString())
                        .build().toUri())
                .body(productDto);
    }

    @GetMapping
    public ResponseEntity<List<ProductRequest>> getList() {
        return ResponseEntity.ok().body(productService.getList());
    }

    @GetMapping("/{id}")
    public ProductDto getById(@PathVariable String id) {
        return productService.getById(id);
    }

    @PutMapping("/{id}")
    public ProductDto updateMessage(@RequestBody ProductRequest productRequest,
                                    @PathVariable String id) {
        return productService.update(productRequest, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable String id) {
        productService.delete(id);
        return ResponseEntity.accepted().body("Message " + id + " deleted");
    }
}
