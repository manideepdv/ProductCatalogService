package org.projects.productcatalogservice.models;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Product extends BaseModel {
    private String name;
    private String description;
    private Double price;
    private Category category;
    private String imageUrl;
}
