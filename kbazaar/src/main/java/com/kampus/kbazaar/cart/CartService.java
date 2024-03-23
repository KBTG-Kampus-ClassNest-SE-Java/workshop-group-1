package com.kampus.kbazaar.cart;

import com.kampus.kbazaar.product.ProductResponse;
import com.kampus.kbazaar.product.ProductService;
import com.kampus.kbazaar.shopper.ShopperResponse;
import com.kampus.kbazaar.shopper.ShopperService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ShopperService shopperService;
    private final ProductService productService;

    @Autowired
    public CartService(
            CartRepository cartRepository,
            ShopperService shopperService,
            ProductService productService) {
        this.cartRepository = cartRepository;
        this.shopperService = shopperService;
        this.productService = productService;
    }

    public void addCart(String userName, CartRequest cartRequest) {
        ShopperResponse shopperResponse = shopperService.getByUsername(userName);
        ProductResponse productResponse = productService.getBySku(cartRequest.productSku());

        Optional<Cart> cartOptional =
                cartRepository.findByUsernameAndSku(
                        shopperResponse.username(), productResponse.sku());

        if (cartOptional.isEmpty()) {
            cartRepository.save(
                    new Cart(
                            shopperResponse.username(),
                            productResponse.sku(),
                            cartRequest.quantity()));
        } else {
            Cart cart = cartOptional.get();
            cart.setQuantity(cartRequest.quantity());
            cartRepository.save(cart);
        }
    }
}
