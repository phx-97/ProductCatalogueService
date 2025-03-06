package com.phx.productcatalogueservice.controllers;
/*
    Api testing
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phx.productcatalogueservice.dtos.CategoryDto;
import com.phx.productcatalogueservice.dtos.ProductDto;
import com.phx.productcatalogueservice.models.Product;
import com.phx.productcatalogueservice.services.IProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IProductService productService;


    //object <-> json <-> string
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void Test_GetAllProductsAPI_TestsStatusOnly() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk());
    }

    @Test
    public void Test_GetAllProductsAPI_TestsContentAndStatus() throws Exception {

        //Arrange
        Product product1 = new Product();
        product1.setName("Iphone12");
        List<Product> productList = new ArrayList<>();
        productList.add(product1);

        ProductDto productDto1 = new ProductDto();
        productDto1.setName("Iphone12");
        List<ProductDto> productDtos = new ArrayList<>();
        productDtos.add(productDto1);

        Product product2 = new Product();
        product2.setName("Iphone15");

        when(productService.getProducts()).thenReturn(productList);

        //Act and Assert
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(productDtos)));
    }

    @Test
    void Test_CreateProduct_ProductCreatedSuccessfully() throws Exception {
        ProductDto productDto = new ProductDto();
        productDto.setId(10L);
        productDto.setName("macbook");

        Product product = new Product();
        product.setName("macbook");
        product.setId(10L);
        when(productService.createProduct(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/products")
                        .content(objectMapper.writeValueAsString(productDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(productDto)));

         MvcResult mvcResult = mockMvc.perform(post("/products").content(objectMapper.writeValueAsString(productDto)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
         String responseDto = mvcResult.getResponse().getContentAsString();
         System.out.println("Sallu: " +responseDto);
    }

    @Test
    void Test_CreateProduct_ProductCreatedSuccessfully_AssertAsJSON() throws Exception {
        ProductDto productDto = new ProductDto();
        productDto.setId(10L);
        productDto.setName("macbook");

        Product product = new Product();
        product.setName("macbook");
        product.setId(10L);

        when(productService.createProduct(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/products")
                        .content(objectMapper.writeValueAsString(productDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(productDto)))
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.name").value("macbook"));
    }

    @Test
    void Test_GetProductsAPI_TestContentAndStatus_AssertAsJSON() throws Exception {
        Product product1 = new Product();
        product1.setName("macbook");
        product1.setId(10L);

        Product product2 = new Product();
        product2.setName("microsoft");
        product2.setId(20L);

        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);

        List<ProductDto> productDtos = new ArrayList<>();
        for(Product product : productList){
            productDtos.add(getProductDto(product));
        }

        when(productService.getProducts()).thenReturn(productList);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(productDtos)))
                .andExpect(jsonPath("$[0].id").value(10L))
                .andExpect(jsonPath("$[0].name").value("macbook"))
                .andExpect(jsonPath("$[1].id").value(20L))
                .andExpect(jsonPath("$[1].name").value("microsoft"));
    }

    private ProductDto getProductDto(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setDescription(product.getDescription());
        productDto.setImageUrl(product.getImageUrl());

        if(product.getCategory() != null){
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(product.getCategory().getId());
            categoryDto.setName(product.getCategory().getName());
            productDto.setCategory(categoryDto);
        }

        return productDto;
    }
}