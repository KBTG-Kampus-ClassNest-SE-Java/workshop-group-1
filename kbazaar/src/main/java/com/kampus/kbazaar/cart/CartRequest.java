package com.kampus.kbazaar.cart;

import jakarta.validation.constraints.*;

public record CartRequest(@NotEmpty String productSku, @Positive int quantity) {}
