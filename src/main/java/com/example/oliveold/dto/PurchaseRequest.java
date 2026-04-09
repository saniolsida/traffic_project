package com.example.oliveold.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PurchaseRequest {
    private Long productId;

    public PurchaseRequest() {}

    public PurchaseRequest(Long productId) {
        this.productId = productId;
    }
}
