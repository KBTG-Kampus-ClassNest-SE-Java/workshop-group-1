package com.kampus.kbazaar.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kampus.kbazaar.security.JwtAuthFilter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(
        controllers = ProductController.class,
        excludeFilters =
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = JwtAuthFilter.class))
public class ProductControllerTest {

    @Autowired private MockMvc mockMvc;

    @Autowired private ProductController productController;

    @MockBean private ProductService productService;

    private static final int LIMIT = 5;
    private static final int OFFSET = 0;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("should return all product")
    public void shouldReturnAllProduct() throws Exception {
        // Given

        // When & Then
        when(productService.getAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/v1/products").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(productService, times(1)).getAll();
    }

    @Test
    @DisplayName("should return product")
    public void shouldReturnProduct() throws Exception {
        // Given
        String sku = "PROMO-1";

        // When & Then
        when(productService.getBySku(sku)).thenReturn(null);

        mockMvc.perform(get("/api/v1/products/" + sku).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(productService, times(1)).getBySku(sku);
    }

    @Test
    @DisplayName("should return product with pagination")
    void testGetProductsWithPage() {
        // Mocking productService's behavior
        List<ProductResponse> mockProducts = new ArrayList<>();
        mockProducts.add(
                new Product(
                                1L,
                                "Apple iPhone 12 Pro",
                                "MOBILE-APPLE-IPHONE-12-PRO",
                                new BigDecimal("20990.25"),
                                50)
                        .toResponse());
        mockProducts.add(
                new Product(
                                2L,
                                "Samsung Galaxy S21 Ultra",
                                "MOBILE-SAMSUNG-GALAXY-S21-ULTRA",
                                new BigDecimal("18990.00"),
                                70)
                        .toResponse());
        mockProducts.add(
                new Product(
                                3L,
                                "Google Pixel 5",
                                "MOBILE-GOOGLE-PIXEL-5",
                                new BigDecimal("12990.75"),
                                40)
                        .toResponse());
        mockProducts.add(
                new Product(
                                4L,
                                "OnePlus 9 Pro",
                                "MOBILE-ONEPLUS-9-PRO",
                                new BigDecimal("14990.00"),
                                60)
                        .toResponse());
        mockProducts.add(
                new Product(
                                5L,
                                "Xiaomi Mi 11",
                                "MOBILE-XIAOMI-MI-11",
                                new BigDecimal("8990.75"),
                                80)
                        .toResponse());

        when(productService.listAllProductByPage(anyInt(), anyInt())).thenReturn(mockProducts);

        // Actual
        ResponseEntity<?> actual = productController.getProductsWithPage(LIMIT, OFFSET);

        // Expected
        HttpHeaders headers = new HttpHeaders();
        headers.set("page", Integer.toString(OFFSET));
        headers.set("limit", Integer.toString(LIMIT));
        ResponseEntity<?> expected = new ResponseEntity<>(mockProducts, headers, HttpStatus.OK);

        assertEquals(expected, actual);
    }
}
