package com.phx.productcatalogueservice.controllers;

import com.phx.productcatalogueservice.dtos.CategoryDto;
import com.phx.productcatalogueservice.dtos.ProductDto;
import com.phx.productcatalogueservice.models.Category;
import com.phx.productcatalogueservice.models.Product;
import com.phx.productcatalogueservice.services.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping("{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long productId){
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("called by","Mohammad Salman");

        try {
            if (productId <= 0) {
                throw  new IllegalArgumentException("ProductId is invalid");
            }

            Product product = productService.getProductById(productId);
            ProductDto productDto = getProductDto(product);

            return new ResponseEntity<>(productDto, headers, HttpStatus.OK);
        } catch(IllegalArgumentException illegalArgumentException){
            //return new ResponseEntity<>(null,headers,HttpStatus.BAD_REQUEST);
            throw illegalArgumentException;
        }
    }

    @GetMapping
    private List<ProductDto> getProducts(){
        List<Product> products = productService.getProducts();
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            productDtos.add(getProductDto(product));
        }

        return productDtos;
    }


    @PutMapping("{id}")
    public ProductDto replaceProduct(@PathVariable("id") Long productId, @RequestBody ProductDto productDto){
        log.info("MSG[replaceProduct] requestBody: "+ productDto);
        Product response = productService.replaceProduct(getProduct(productDto), productId);
        log.info("MSG[replaceProduct] response: "+ response);
        return getProductDto(response);
    }

    @PostMapping
    public ProductDto createProduct(@RequestBody ProductDto productDto){
        Product response = productService.createProduct(getProduct(productDto));
        return getProductDto(response);
    }

    private Product getProduct(ProductDto productDto){
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());
        if(productDto.getCategory() != null){
            Category category = new Category();
            category.setName(productDto.getCategory().getName());
            product.setCategory(category);
        }

        return product;
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

    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<String> exceptionHandler(Exception exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
