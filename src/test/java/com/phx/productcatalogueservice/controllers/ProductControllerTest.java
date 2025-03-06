package com.phx.productcatalogueservice.controllers;

import com.phx.productcatalogueservice.dtos.ProductDto;
import com.phx.productcatalogueservice.models.Category;
import com.phx.productcatalogueservice.models.Product;
import com.phx.productcatalogueservice.services.IProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductControllerTest {

    @Autowired
    private ProductController productController;

    @MockBean
    private IProductService productService;

    @Captor
    ArgumentCaptor<Long> idCaptor;

    @Test
    void Test_GetProductById_WithValidId_RunSuccessfully() {
        // Arrange
        Long productId = 10L;
        Product product = new Product();
        product.setId(productId);
        product.setName("IPhone13");
        product.setDescription("IPhone 13");
        product.setPrice(100000D);

        Category category = new Category();
        category.setId(23L);
        category.setName("IPhone");
        product.setCategory(category);

        when(productService.getProductById(productId)).thenReturn(product);

        // Act
        ResponseEntity<ProductDto> response = productController.getProductById(productId);

        assertNotNull(response);
        assertEquals(productId, response.getBody().getId());
        assertEquals("IPhone13", response.getBody().getName());

        verify(productService,times(1)).getProductById(productId);
    }

    @Test
    void Test_GetProductById_WithInvalidId_ThrowsIllegalArgumentException() {
        // Act & Assert
        //assertThrows(IllegalArgumentException.class, ()->productController.getProductById(-1L));
        Exception exception =assertThrows(IllegalArgumentException.class, ()->productController.getProductById(-1L));
        assertEquals("ProductId is invalid", exception.getMessage());

        // check number of time we've interacted with our Mock

        verify(productService,times(0)).getProductById(-1L);
    }

    @Test
    void Teest_GetProductById_ProductServiceThrowsException() {
        Long productId = 10L;
        when(productService.getProductById(productId)).thenThrow(new RuntimeException("Something went wrong"));

        assertThrows(RuntimeException.class, ()->productController.getProductById(productId));
    }

    @Test
    void Test_CreateProduct_WithValidId_RunSuccessfully() {
        //Arrange
        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setName("Iphone12");
        productDto.setPrice(100000D);

        Product product = new Product();
        product.setId(1L);
        product.setName("Iphone12");
        product.setPrice(100000D);

        when(productService.createProduct(any(Product.class))).thenReturn(product);
        //when(productService.createProduct(product)).thenReturn(product);

        ProductDto response = productController.createProduct(productDto);

        assertNotNull(response);
        assertTrue(response.getPrice() == 100000D);
        assertEquals("Iphone12",response.getName());
    }

    @DisplayName("Passing product id as 10 to controller and expect same on product service call as well, if this assert fails, that means value was not 1")
    @Test
    void Test_GetProductById_ServiceCalledWithValidArguments_RunSuccessfully() {
        // Arrange
        Long productId = 10L;
        Product product = new Product();
        product.setId(productId);
        //when(productService.getProductById(productId)).thenReturn(product);
        when(productService.getProductById(any(Long.class))).thenReturn(product);

        // Act
        productController.getProductById(productId);

        // Assert
        verify(productService).getProductById(idCaptor.capture());
        assertEquals(productId, idCaptor.getValue());
    }

}