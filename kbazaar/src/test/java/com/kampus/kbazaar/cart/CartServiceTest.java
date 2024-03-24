package com.kampus.kbazaar.cart;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.kampus.kbazaar.product.ProductResponse;
import com.kampus.kbazaar.product.ProductService;
import com.kampus.kbazaar.shopper.ShopperResponse;
import com.kampus.kbazaar.shopper.ShopperService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {
    @InjectMocks private CartService cartService;
    @Mock private CartRepository cartRepository;
    @Mock private ShopperService shopperService;
    @Mock private ProductService productService;

    @Value("${enabled.shipping.fee:true}")
    boolean enableFeatureFee;

    @Test
    @DisplayName("add cart should return CartResponse")
    public void addNewCartSuccessWithOutFee() {
        CartRequest cartRequest = new CartRequest("MOBILE-LG-VELVET", 6);
        String userName = "GeekChic";
        String sku = "MOBILE-LG-VELVET";

        Cart cart = new Cart(userName, sku, 6);
        ProductResponse productResponse =
                new ProductResponse(1L, "apple", sku, new BigDecimal("1000"), 10);

        when(cartRepository.findByUsernameAndSku(any(), any())).thenReturn(Optional.empty());
        when(shopperService.getByUsername(userName))
                .thenReturn(new ShopperResponse(1L, userName, "email"));
        when(productService.getBySku(sku)).thenReturn(productResponse);
        when(cartRepository.save(any())).thenReturn(cart);
        when(cartRepository.findAllByUsername(userName)).thenReturn(List.of(cart));

        CartResponse cartResponse = cartService.addCart(userName, cartRequest);

        Assertions.assertEquals(cartResponse.totalPrice(), new BigDecimal("6000"));
        Assertions.assertEquals(userName, cartResponse.username());
        Assertions.assertEquals(sku, cartResponse.products().get(0).sku());
        Assertions.assertEquals(0, cartResponse.fee());
        verify(cartRepository, times(1)).save(any());
    }

    @Test
    public void updateOldCartSuccess() {
        CartRequest cartRequest = new CartRequest("MOBILE-LG-VELVET", 6);
        String userName = "GeekChic";
        String sku = "MOBILE-LG-VELVET";

        Cart cart = new Cart(userName, sku, 1);
        ProductResponse productResponse =
                new ProductResponse(1L, "apple", sku, new BigDecimal("1000"), 1);

        when(cartRepository.findByUsernameAndSku(any(), any())).thenReturn(Optional.of(cart));
        when(shopperService.getByUsername(userName))
                .thenReturn(new ShopperResponse(1L, userName, "email"));
        when(productService.getBySku(sku)).thenReturn(productResponse);
        when(cartRepository.save(any())).thenReturn(cart);
        when(cartRepository.findAllByUsername(userName)).thenReturn(List.of(cart));

        CartResponse cartResponse = cartService.addCart(userName, cartRequest);

        Assertions.assertEquals(cartResponse.totalPrice(), new BigDecimal("6000"));
        verify(cartRepository, times(1)).save(any());
    }
}
