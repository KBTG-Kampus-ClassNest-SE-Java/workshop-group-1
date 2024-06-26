package com.kampus.kbazaar.cart;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/carts")
    public ResponseEntity getCart() { // NOSONAR
        return ResponseEntity.ok().build();
    }

    @PostMapping("/carts/{username}/items")
    public ResponseEntity<CartResponse> addCart(
            @PathVariable("username") String userName,
            @RequestBody @Valid CartRequest cartRequest) {

        CartResponse cartResponse = cartService.addCart(userName, cartRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartResponse);
    }

    @GetMapping("/carts/{username}")
    public ResponseEntity<?> getUserItemsByUserName(@PathVariable String username) {
        return new ResponseEntity<>(cartService.getCartByUserName(username), HttpStatus.OK);
    }
}
