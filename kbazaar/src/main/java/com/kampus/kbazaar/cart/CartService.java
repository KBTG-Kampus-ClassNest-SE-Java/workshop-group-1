package com.kampus.kbazaar.cart;

import com.kampus.kbazaar.product.Product;
import com.kampus.kbazaar.product.ProductResponse;
import com.kampus.kbazaar.product.ProductResponseWithDiscount;
import com.kampus.kbazaar.product.ProductService;
import com.kampus.kbazaar.promotion.PromotionResponse;
import com.kampus.kbazaar.shopper.ShopperResponse;
import com.kampus.kbazaar.shopper.ShopperService;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ShopperService shopperService;
    private final ProductService productService;

    @Value("${enabled.shipping.fee:true}")
    private boolean enableFeatureFee;

    @Autowired
    public CartService(
            CartRepository cartRepository,
            ShopperService shopperService,
            ProductService productService) {
        this.cartRepository = cartRepository;
        this.shopperService = shopperService;
        this.productService = productService;
    }

    @Transactional
    public CartResponse addCart(String userName, CartRequest cartRequest) {
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

        List<ProductResponseWithDiscount> productList = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        List<Cart> cartList = cartRepository.findAllByUsername(userName);

        for (Cart cart : cartList) {
            ProductResponse productServiceBySku = productService.getBySku(cart.getSku());
            Product p =
                    new Product(
                            productServiceBySku.id(),
                            productServiceBySku.name(),
                            productServiceBySku.sku(),
                            productServiceBySku.price(),
                            cart.getQuantity());
            BigDecimal finalPrice =
                    productServiceBySku.price().multiply(new BigDecimal(cart.getQuantity()));
            productList.add(
                    new ProductResponseWithDiscount(
                            p.getName(),
                            p.getSku(),
                            p.getPrice(),
                            cart.getQuantity(),
                            0,
                            finalPrice));

            BigDecimal total =
                    productServiceBySku.price().multiply(new BigDecimal(cart.getQuantity()));
            totalPrice = totalPrice.add(total);
        }
        int fee = 0;
        if (enableFeatureFee) {
            fee = 25;
            totalPrice = totalPrice.add(new BigDecimal(fee));
        }

        return new CartResponse(userName, productList, totalPrice, 0, fee);
    }

    public void applyPromotionByCodeAndSku(String username, CartPromotion cartpromotion) {
        cartRepository.findByUsernameAndSku(username, cartpromotion.code());

    }
}
