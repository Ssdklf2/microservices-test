package com.example.productservice.controllers;

import com.example.productservice.exceptions.ValidationException;
import com.example.productservice.DTO.ProductDto;
import com.example.productservice.DTO.ProductResponse;
import com.example.productservice.services.ProductService;
import org.junit.jupiter.api.Disabled;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    private final ModelMapper mapper = new ModelMapper();

    private final ProductDto productDto1 = new ProductDto("product1", "desc1", 100);
    private final ProductDto productDto2 = new ProductDto("product2", "desc2", 200);
    private final ProductDto productDto3 = new ProductDto("product3", "desc3", 300);

    @Disabled
    public void getAllProductsAndGetIsOk() throws Exception {
        List<ProductDto> list = new ArrayList<>(
                Arrays.asList(productDto1, productDto2, productDto3));
        Mockito.when(service.getList()).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[1].title", is("product2")));
    }


    @Disabled
    public void getValidationException() throws Exception {
        Mockito.when(service.getById("1")).thenThrow(ValidationException.class);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof ValidationException));
    }

    @Disabled
    public void getBeIdAndExpectIsOk() throws Exception {
        ProductResponse response = mapper.map(productDto1, ProductResponse.class);
        response.setId(UUID.fromString("2190b5f2-f7f0-460f-9880-8f6a56c3c9fd"));
        Mockito.when(service.getById("2190b5f2-f7f0-460f-9880-8f6a56c3c9fd"))
                .thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/products/2190b5f2-f7f0-460f-9880-8f6a56c3c9fd")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("product1")));
    }
}