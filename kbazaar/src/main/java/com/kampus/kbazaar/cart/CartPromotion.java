package com.kampus.kbazaar.cart;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record CartPromotion(@NotEmpty String code, @Positive List<String> productSkus) {}
