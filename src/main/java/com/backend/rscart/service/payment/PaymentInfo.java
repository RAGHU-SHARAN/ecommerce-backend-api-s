package com.backend.rscart.service.payment;

import lombok.Data;

@Data
public class PaymentInfo {
    private int amount;
    private String currency;
}
