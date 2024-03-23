package com.kampus.kbazaar.cart;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Entity(name = "cart")
@Data
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @NotEmpty private String username;

    @NotBlank @NotEmpty private String sku;

    @Positive private int quantity;

    public Cart() {}

    public Cart(String username, String sku, int quantity) {
        this.username = username;
        this.sku = sku;
        this.quantity = quantity;
    }
}
