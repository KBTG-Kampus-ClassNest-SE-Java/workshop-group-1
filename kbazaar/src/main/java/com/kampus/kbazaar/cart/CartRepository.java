package com.kampus.kbazaar.cart;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUsernameAndSku(String username, String sku);

    List<Cart> findAllByUsername(String username);
}
