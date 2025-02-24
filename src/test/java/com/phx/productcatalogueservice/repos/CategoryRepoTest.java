package com.phx.productcatalogueservice.repos;

import com.phx.productcatalogueservice.models.Category;
import com.phx.productcatalogueservice.models.Product;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryRepoTest {

    @Autowired
    CategoryRepo categoryRepo;

    @Test
    @Transactional
    public void demonstrateLoading(){
        Category category = categoryRepo.findById(25L).get();

        for (Product product : category.getProducts()) {
            System.out.println(product.getDescription());
        }
        /*
            In case of lazy loading -- two query will be executed one will be to fetch the category and other using the category product id
            Query's are belows


            Hibernate: select c1_0.id,c1_0.created_at,c1_0.description,c1_0.last_updated_at,c1_0.name,c1_0.status from category c1_0 where c1_0.id=?
            Hibernate: select p1_0.category_id,p1_0.id,p1_0.created_at,p1_0.description,p1_0.image_url,p1_0.last_updated_at,p1_0.name,p1_0.price,p1_0.status from product p1_0 where p1_0.category_id=?
P
         */
    }


}