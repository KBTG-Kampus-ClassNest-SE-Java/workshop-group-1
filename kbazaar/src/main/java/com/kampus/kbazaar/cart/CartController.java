package com.kampus.kbazaar.cart;

import com.kampus.kbazaar.promotion.Promotion;
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
    public ResponseEntity<String> addCart(
            @PathVariable("username") String userName,
            @RequestBody @Valid CartRequest cartRequest) {
        cartService.addCart(userName, cartRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("create cart success");
    }
    @PostMapping("/carts/{username}/promotions")
    public ResponseEntity<String> addPromotions(
            @PathVariable("username") String userName,
            @RequestBody @Valid Promotion promotion) {
        cartService.addPromotions(userName, promotion);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("promotion cart success");
    }
}
