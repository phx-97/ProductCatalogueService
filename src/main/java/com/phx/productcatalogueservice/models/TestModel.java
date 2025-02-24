package com.phx.productcatalogueservice.models;

import jakarta.persistence.Entity;

@Entity
public class TestModel extends BaseModel{
    private Long numField;
    private String testField;
}
