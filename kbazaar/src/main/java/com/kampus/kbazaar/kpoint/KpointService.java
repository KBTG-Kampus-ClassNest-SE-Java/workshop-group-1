package com.kampus.kbazaar.kpoint;

import com.kampus.kbazaar.exceptions.NotFoundException;
import com.kampus.kbazaar.product.Product;
import com.kampus.kbazaar.product.ProductRepository;
import com.kampus.kbazaar.product.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KpointService {




    public KpointResponse getKpoint(String username,KpointRequest kpointRequest) {
//        Optional<Product> product = productRepository.findBySku(sku);
//        if (product.isEmpty()) {
//            throw new NotFoundException("Product not found");
//        }

        return new KpointResponse(1);
    }
}
