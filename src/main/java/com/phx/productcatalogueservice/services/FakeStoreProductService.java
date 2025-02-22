package com.phx.productcatalogueservice.services;

import com.phx.productcatalogueservice.dtos.FakeStoreDto;
import com.phx.productcatalogueservice.models.Category;
import com.phx.productcatalogueservice.models.Product;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class FakeStoreProductService implements IProductService {

    RestTemplateBuilder restTemplateBuilder;
    public FakeStoreProductService(RestTemplateBuilder restTemplateBuilder){
        this.restTemplateBuilder = restTemplateBuilder;
    }

    @Override
    public Product getProductById(Long id) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        //FakeStoreDto fakeStoreDto= restTemplate.getForEntity("https://fakestoreapi.com/products/{id}", FakeStoreDto.class,id).getBody();
        //return getProduct(fakeStoreDto);
        ResponseEntity<FakeStoreDto> fakeStoreDtoResponseEntity = restTemplate.getForEntity("https://fakestoreapi.com/products/{id}", FakeStoreDto.class,id);
        if(fakeStoreDtoResponseEntity.getStatusCode().is2xxSuccessful() && fakeStoreDtoResponseEntity.getBody() != null){
            return getProduct(fakeStoreDtoResponseEntity.getBody());
        }
        return null;
    }

    @Override
    public List<Product> getProducts() {
        return List.of();
    }

    @Override
    public Product createProduct(Product product) {
        return null;
    }

    private Product getProduct(FakeStoreDto fakeStoreDto){
        Product product = new Product();
        product.setId(fakeStoreDto.getId());
        product.setName(fakeStoreDto.getTitle());
        product.setDescription(fakeStoreDto.getDescription());
        product.setPrice(fakeStoreDto.getPrice());
        product.setImageUrl(fakeStoreDto.getImage());

        if(fakeStoreDto.getCategory() != null){
            Category category = new Category();
            category.setName(fakeStoreDto.getCategory());
            product.setCategory(category);
        }

        return product;
    }

}
