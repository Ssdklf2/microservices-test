package com.example.productservice.services;

import com.example.productservice.exceptions.InvalidUUIDException;
import com.example.productservice.exceptions.NoDataException;
import com.example.productservice.exceptions.NotFoundException;
import com.example.productservice.models.DTO.ProductDto;
import com.example.productservice.models.DTO.ProductRequest;
import com.example.productservice.models.DTO.ProductSorter;
import com.example.productservice.models.Product;
import com.example.productservice.repositories.ProductRepository;
import com.example.productservice.specifications.ProductSpecification;
import com.example.productservice.specifications.SearchCriteria;
import com.example.productservice.specifications.SearchOperation;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
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

    public List<ProductRequest> getPages(Integer pageNumber, Integer pageSize) {
        return productRepository.findAll(PageRequest.of(pageNumber, pageSize))
                .stream().map(product -> mapper.map(product, ProductRequest.class))
                .collect(Collectors.toList());
    }

    public List<ProductRequest> getList() {
        List<ProductRequest> list = productRepository.findAll()
                .stream().map(product -> mapper.map(product, ProductRequest.class))
                .collect(Collectors.toList());
        if (list.isEmpty()) throw new NoDataException("Products");
        return list;
    }

    public ProductDto getById(String id) {
        UUID uuid = getValidUUID(id);
        Product product = getProduct(id, uuid);
        return mapper.map(product, ProductDto.class);
    }

    public ProductDto update(ProductRequest productRequest, String id) {
        UUID uuid = getValidUUID(id);
        Product product = getProduct(id, uuid);
        product.setTitle(productRequest.getTitle());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        productRepository.save(product);
        return mapper.map(product, ProductDto.class);
    }

    private Product getProduct(String id, UUID uuid) {
        return productRepository.findById(uuid)
                .orElseThrow(() -> new NotFoundException("Product", id));
    }

    public void delete(String id) {
        UUID uuid = getValidUUID(id);
        Product product = getProduct(id, uuid);
        productRepository.delete(product);
    }

    public List<ProductRequest> sort(ProductSorter productSorter) {
        var specification = new ProductSpecification();
        var title = productSorter.getTitle();
        var priceFrom = productSorter.getPriceFrom();
        var priceTo = productSorter.getPriceTo();
        log.info("\nSorting:" +
                "\nPrice:  " + priceFrom + " - " + priceTo +
                "\nTitle:  " + title);
        addCriteriaToSpecification(specification, title, priceFrom, priceTo);
        return productRepository.findAll(specification).stream()
                .map(product -> mapper.map(product, ProductRequest.class))
                .collect(Collectors.toList());
    }

    private static void addCriteriaToSpecification(ProductSpecification specification,
                                                   String title, BigDecimal priceFrom,
                                                   BigDecimal priceTo) {
        if (title != null && !title.isBlank()) {
            specification.addCriteria(
                    new SearchCriteria("title", title, SearchOperation.LIKE));
        }
        if (priceFrom != null && priceTo != null) {
            specification.addCriteria(
                    new SearchCriteria("price",
                            String.valueOf(priceFrom),
                            String.valueOf(priceTo),
                            SearchOperation.BETWEEN));
        }
        if (priceFrom != null && priceTo == null) {
            specification.addCriteria(
                    new SearchCriteria("price",
                            String.valueOf(priceFrom),
                            SearchOperation.GREATER_THAN));
        }
        if (priceFrom == null && priceTo != null) {
            specification.addCriteria(
                    new SearchCriteria("price",
                            String.valueOf(priceTo),
                            SearchOperation.LESS_THAN));
        }
    }

    private static UUID getValidUUID(String id) {
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            log.warn("Invalid uuid: " + id);
            throw new InvalidUUIDException(id);
        }
        return uuid;
    }
}
