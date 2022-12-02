package com.example.productservice.services;

import com.example.productservice.exceptions.InvalidUUIDException;
import com.example.productservice.exceptions.NoDataException;
import com.example.productservice.exceptions.NotFoundException;
import com.example.productservice.models.DTO.ProductDto;
import com.example.productservice.models.DTO.ProductResponse;
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
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper mapper = new ModelMapper();

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductResponse saveProduct(ProductDto request) {
        Product product = mapper.map(request, Product.class);
        product.setId(UUID.randomUUID());
        product = productRepository.save(product);
        return mapper.map(product, ProductResponse.class);
    }

    public List<ProductDto> getPages(Integer pageNumber, Integer pageSize) {
        return productRepository.findAll(PageRequest.of(pageNumber, pageSize))
                .stream().map(product -> mapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    public List<ProductDto> getList() {
        List<ProductDto> list = productRepository.findAll()
                .stream().map(product -> mapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
        if (list.isEmpty()) throw new NoDataException("Products");
        return list;
    }

    public ProductResponse getById(String id) {
        UUID uuid = getValidUUID(id);
        Product product = getProduct(uuid);
        return mapper.map(product, ProductResponse.class);
    }

    @Transactional
    public ProductResponse update(ProductDto productDto, String id) {
        UUID uuid = getValidUUID(id);
        Product product = getProduct(uuid);
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        productRepository.save(product);
        return mapper.map(product, ProductResponse.class);
    }

    private Product getProduct(UUID uuid) {
        return productRepository.findById(uuid)
                .orElseThrow(() -> new NotFoundException("Product", uuid));
    }

    @Transactional
    public void delete(String id) {
        UUID uuid = getValidUUID(id);
        Product product = getProduct(uuid);
        productRepository.delete(product);
    }

    public List<ProductDto> sort(ProductSorter productSorter) {
        var specification = new ProductSpecification();
        var title = productSorter.getTitle();
        var priceFrom = productSorter.getPriceFrom();
        var priceTo = productSorter.getPriceTo();
        log.info("\nSorting:" +
                "\nPrice:  " + priceFrom + " - " + priceTo +
                "\nTitle:  " + title);
        addCriteriaToSpecification(specification, title, priceFrom, priceTo);
        return productRepository.findAll(specification).stream()
                .map(product -> mapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    private static void addCriteriaToSpecification(ProductSpecification specification,
                                                   String title, BigDecimal priceFrom,
                                                   Integer priceTo) {
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
