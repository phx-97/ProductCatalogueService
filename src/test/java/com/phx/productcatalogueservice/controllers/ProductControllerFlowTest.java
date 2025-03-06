package com.phx.productcatalogueservice.controllers;

import com.phx.productcatalogueservice.dtos.ProductDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProductControllerFlowTest {

    @Autowired
    ProductController productController;

    @Test
    public void Test_Create_Replace_GetProduct_WithStub_RunSuccessfully(){
        // Arrange
        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setName("Test");

        ProductDto response = productController.createProduct(productDto);
        ResponseEntity<ProductDto> productDtoResponseEntity = productController.getProductById(1L);

        productDto.setName("Test2");
        ProductDto response2= productController.replaceProduct(productDto.getId(), productDto);
        ResponseEntity<ProductDto> productDtoResponseEntity2 = productController.getProductById(1L);

        assertEquals("Test",productDtoResponseEntity.getBody().getName());
        assertEquals("Test2",productDtoResponseEntity2.getBody().getName());
    }


}
