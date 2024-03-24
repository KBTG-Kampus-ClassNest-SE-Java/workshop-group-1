package com.kampus.kbazaar.cart;

import com.kampus.kbazaar.product.ProductResponseWithDiscount;
import java.math.BigDecimal;
import java.util.List;

public record CartResponse(
        String username,
        List<ProductResponseWithDiscount> products,
        BigDecimal totalPrice,
        int totalDiscount, int fee) {}



