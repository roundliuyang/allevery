package com.yly.dddhexagonalspring.application.request;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yly.dddhexagonalspring.domain.Product;

import javax.validation.constraints.NotNull;

public class AddProductRequest {
    @NotNull private Product product;

    @JsonCreator
    public AddProductRequest(@JsonProperty("product") final Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }
}
