package com.kampus.kbazaar.cart;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {
    @InjectMocks private CartService cartService;
    @MockBean private CartRepository cartRepository;

    @Test
    public void addNewCartSuccess() {
        when(cartRepository.findByUsernameAndSku(any(), any())).thenReturn(Optional.empty());
    }
}
