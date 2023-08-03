package com.ecommerce.repo;

import com.ecommerce.entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepo extends JpaRepository<CartItems,Long> {

    Optional<CartItems> findByProductIdAndUserId(Long productId, Long userId);

    void deleteAllByUserId(Long userId);

    Optional<CartItems> findByProductId(Long productId);

    Optional<CartItems> findByProductIdAndOrderIdAndUserId(Long productId, Long orderId, Long userId);

    void deleteByUserId(Long userId);
}
