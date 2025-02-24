package com.phx.productcatalogueservice.repos;

import com.phx.productcatalogueservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    // Product save(Product product);

    // Optional<Product> findById(Long id);
}
