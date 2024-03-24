package com.kampus.kbazaar.product;

import com.kampus.kbazaar.exceptions.NotFoundException;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.Min;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Validated
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @ApiResponse(
            responseCode = "200",
            description = "list all products",
            content = {
                @Content(
                        mediaType = "application/json",
                        array =
                                @ArraySchema(
                                        schema = @Schema(implementation = ProductResponse.class)))
            })
    @ApiResponse(
            responseCode = "500",
            description = "internal server error",
            content =
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NotFoundException.class)))
    @GetMapping("/products")
    public List<ProductResponse> getProducts() {
        return productService.getAll();
    }

    @ApiResponse(
            responseCode = "200",
            description = "get product by sku",
            content = {
                @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ProductResponse.class))
            })
    @ApiResponse(
            responseCode = "404",
            description = "product not found",
            content =
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NotFoundException.class)))
    @GetMapping("/products/{sku}")
    public ProductResponse getProductById(@PathVariable String sku) {
        return productService.getBySku(sku);
    }

    @GetMapping("/getProducts")
    public ResponseEntity<?> getProductsWithPage(
            @RequestParam(name = "limit", required = false, defaultValue = "10")
                    @Min(value = 0, message = "Limit size must be positive number")
                    int limit,
            @RequestParam(name = "page", required = false, defaultValue = "0")
                    @Min(value = 0, message = "Page size must be positive number")
                    int page) {

        List<ProductResponse> productResponses =
                productService.listAllProductByPage(limit, page * limit);

        HttpHeaders headers = new HttpHeaders();
        headers.set("page", Integer.toString(page));
        headers.set("limit", Integer.toString(limit));

        return new ResponseEntity<>(productResponses, headers, HttpStatus.OK);
    }
}
