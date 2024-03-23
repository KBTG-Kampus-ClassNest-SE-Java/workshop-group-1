package com.kampus.kbazaar.product;

import java.math.BigDecimal;

public record ProductResponseWithDiscount(
        String name,
        String sku,
        BigDecimal price,
        int quantity,
        int discount,
        BigDecimal finalPrice) {}
