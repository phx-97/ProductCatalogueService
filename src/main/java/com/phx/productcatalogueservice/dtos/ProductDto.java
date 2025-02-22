package com.phx.productcatalogueservice.dtos;

import com.phx.productcatalogueservice.models.Category;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private double price;
    private String imageUrl;
    private CategoryDto category;
}
