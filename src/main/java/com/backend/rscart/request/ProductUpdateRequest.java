package com.backend.rscart.request;

import lombok.Data;

import java.math.BigDecimal;

import com.backend.rscart.model.Category;

@Data
public class ProductUpdateRequest {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
}
