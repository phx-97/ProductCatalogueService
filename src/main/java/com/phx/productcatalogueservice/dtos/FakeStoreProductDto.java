package com.phx.productcatalogueservice.dtos;

/*
    3rd party integration using fakestoreapi
 */

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FakeStoreProductDto {
    private Long id;
    private String title;
    private Double price;
    private String description;
    private String category;
    private String image;
}
