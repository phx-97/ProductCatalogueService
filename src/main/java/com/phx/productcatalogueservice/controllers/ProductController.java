package com.phx.productcatalogueservice.controllers;

import com.phx.productcatalogueservice.dtos.CategoryDto;
import com.phx.productcatalogueservice.dtos.ProductDto;
import com.phx.productcatalogueservice.models.Product;
import com.phx.productcatalogueservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping("/products")
    private List<ProductDto> getProducts(){
        return null;
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long productId){
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("called by","Mohammad Salman");

        try {
            if (productId <= 0) {
                throw  new IllegalArgumentException("ProductId is invalid");
            }

            Product product = productService.getProductById(productId);
            ProductDto productDto = getProduct(product);

            return new ResponseEntity<>(productDto, headers, HttpStatus.OK);
        } catch(IllegalArgumentException illegalArgumentException){
            return new ResponseEntity<>(null,headers,HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/products")
    public ProductDto createProduct(@RequestBody ProductDto product){
        return null;
    }


    private ProductDto getProduct(Product product){
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
