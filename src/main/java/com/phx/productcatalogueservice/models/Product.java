package com.phx.productcatalogueservice.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Product extends BaseModel{
    private String name;
    private String description;
    private double price;
    private String imageUrl;
    private Category category;

}
