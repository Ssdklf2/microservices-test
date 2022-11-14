package com.example.productservice.services;

import com.example.productservice.models.DTO.ProductDto;
import com.example.productservice.models.DTO.ProductRequest;
import com.example.productservice.models.Product;
import com.example.productservice.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    ProductRepository productRepository;
    ModelMapper mapper = new ModelMapper();

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDto saveProduct(ProductRequest request) {
        Product product = mapper.map(request, Product.class);
        product.setId(UUID.randomUUID());
        product = productRepository.save(product);
        return mapper.map(product, ProductDto.class);
    }

    public List<ProductRequest> getList() {
        return productRepository.findAll()
                .stream().map(product -> mapper.map(product, ProductRequest.class))
                .toList();
    }

    public ProductDto getById(String id) {
        Product product = productRepository.findById(UUID.fromString(id)).orElse(null);
        if (product != null) {
            return mapper.map(product, ProductDto.class);
        }
        return null; //exc
    }

    public ProductDto update(ProductRequest productRequest, String id) {
        Product product = productRepository.findById(UUID.fromString(id)).orElse(null);
        if (product == null) return null;
        product.setTitle(productRequest.getTitle());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        productRepository.save(product);
        return mapper.map(product, ProductDto.class);
    }

    public void delete(String id) {
        productRepository.deleteById(UUID.fromString(id));
    }
}
