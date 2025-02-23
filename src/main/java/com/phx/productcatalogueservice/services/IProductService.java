package com.phx.productcatalogueservice.services;

import com.phx.productcatalogueservice.models.Product;

import java.util.List;

public interface IProductService {
    public Product getProductById(Long id);
    public List<Product> getProducts();
    public Product replaceProduct(Product product, Long id);
    public Product createProduct(Product product);
}
