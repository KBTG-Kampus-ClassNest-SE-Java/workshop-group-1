package com.kampus.kbazaar.product;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySku(String sku);

    @Query(value = "SELECT * FROM product LIMIT :limit OFFSET :page", nativeQuery = true)
    List<Product> findAllByPage(int limit, int page);
}
