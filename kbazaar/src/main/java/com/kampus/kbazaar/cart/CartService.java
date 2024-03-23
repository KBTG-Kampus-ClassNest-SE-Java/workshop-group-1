package com.kampus.kbazaar.cart;

import com.kampus.kbazaar.product.Product;
import com.kampus.kbazaar.product.ProductResponse;
import com.kampus.kbazaar.product.ProductService;
import com.kampus.kbazaar.promotion.Promotion;
import com.kampus.kbazaar.promotion.PromotionResponse;
import com.kampus.kbazaar.promotion.PromotionService;
import com.kampus.kbazaar.shopper.ShopperResponse;
import com.kampus.kbazaar.shopper.ShopperService;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ShopperService shopperService;
    private final ProductService productService;

    private final PromotionService promotionService;
    @Autowired
    public CartService(
            CartRepository cartRepository,
            ShopperService shopperService,
            ProductService productService,
            PromotionService promotionService
            ) {
        this.cartRepository = cartRepository;
        this.shopperService = shopperService;
        this.productService = productService;
        this.promotionService = promotionService;
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
    public void addPromotions(String userName, Promotion promotion) {
        List<Cart> cartResponse = cartRepository.findAllByUsername(userName);
        String promotionCode = promotion.toResponse().code();
        BigDecimal totalPrice = BigDecimal.ZERO;
        PromotionResponse promotionResponse = promotionService.getPromotionByCode(promotionCode);
        if(promotionResponse.applicableTo().equals("ENTIRE_CART")){
            for (int i = 0; i < cartResponse.size(); i++)
            {
                Cart cart = cartResponse.get(i);
                ProductResponse product = productService.getBySku(cart.getSku());
                totalPrice = product.price().add(totalPrice);
            }
        }
        if(promotionResponse.applicableTo().equals("ENTIRE_CART") && promotionResponse.discountType().equals("FIXED_AMOUNT")){
            totalPrice = totalPrice.subtract(promotionResponse.discountAmount());
        }


        System.out.println(totalPrice);

    }
}
