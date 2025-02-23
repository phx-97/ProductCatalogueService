package com.phx.productcatalogueservice.services;

import com.phx.productcatalogueservice.clients.FakeStoreApiClient;
import com.phx.productcatalogueservice.dtos.FakeStoreProductDto;
import com.phx.productcatalogueservice.models.Category;
import com.phx.productcatalogueservice.models.Product;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductService implements IProductService {

    RestTemplateBuilder restTemplateBuilder;
    FakeStoreApiClient fakeStoreApiClient;
    public FakeStoreProductService(RestTemplateBuilder restTemplateBuilder, FakeStoreApiClient fakeStoreApiClient) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.fakeStoreApiClient = fakeStoreApiClient;
    }

    @Override
    public Product getProductById(Long id) {
        FakeStoreProductDto fakeStoreProductDto = fakeStoreApiClient.getProductById(id);
        return fakeStoreProductDto != null ? getProduct(fakeStoreProductDto) : null;
    }

    /*
    @Override
    public Product getProductById(Long id) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        //FakeStoreDto fakeStoreDto= restTemplate.getForEntity("https://fakestoreapi.com/products/{id}", FakeStoreDto.class,id).getBody();
        //return getProduct(fakeStoreDto);
        ResponseEntity<FakeStoreProductDto> fakeStoreDtoResponseEntity = restTemplate.getForEntity("https://fakestoreapi.com/products/{id}", FakeStoreProductDto.class,id);
        if(fakeStoreDtoResponseEntity.getStatusCode().is2xxSuccessful() && fakeStoreDtoResponseEntity.getBody() != null){
            return getProduct(fakeStoreDtoResponseEntity.getBody());
        }
        return null;
    }

     */

    @Override
    public List<Product> getProducts() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        // https://angelikalanger.com/GenericsFAQ/FAQSections/ParameterizedTypes.html#FAQ310
        FakeStoreProductDto[] outputDtos = restTemplate.getForEntity("https://fakestoreapi.com/products",FakeStoreProductDto[].class).getBody();
        List<Product> products = new ArrayList<>();
        for(FakeStoreProductDto fakeStoreProductDto : outputDtos){
            products.add(getProduct(fakeStoreProductDto));
        }

        return products;
    }

    @Override
    public Product replaceProduct(Product product, Long id) {
        FakeStoreProductDto input = getFakeStoreProductDto(product);
        ResponseEntity<FakeStoreProductDto> responseEntity = requestForEntity("https://fakestoreapi.com/products/{id}",HttpMethod.PUT,input,FakeStoreProductDto.class, id);
        if(responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null){
            return getProduct(responseEntity.getBody());
        }
        return null;
    }

    public <T> ResponseEntity<T> requestForEntity(String url, HttpMethod httpMethod , @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }

    @Override
    public Product createProduct(Product product) {
        FakeStoreProductDto input = getFakeStoreProductDto(product);
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> response = restTemplate.postForEntity("https://fakestoreapi.com/products", input, FakeStoreProductDto.class);
        if(response.getStatusCode().is2xxSuccessful() && response.getBody() != null){
            return getProduct(response.getBody());
        }
        return null;
    }

    private FakeStoreProductDto getFakeStoreProductDto(Product product){
        FakeStoreProductDto fakeStoreDto = new FakeStoreProductDto();
        fakeStoreDto.setTitle(product.getName());
        fakeStoreDto.setDescription(product.getDescription());
        fakeStoreDto.setPrice(product.getPrice());
        fakeStoreDto.setImage(product.getImageUrl());
        if(product.getCategory() != null){
            fakeStoreDto.setCategory(product.getCategory().getName());
        }
        return fakeStoreDto;
    }
    private Product getProduct(FakeStoreProductDto fakeStoreDto){
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
