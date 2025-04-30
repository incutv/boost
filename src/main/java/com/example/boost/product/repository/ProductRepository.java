package com.example.boost.product.repository;


import com.example.boost.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findTop10ByOrderByLikesDesc();
    List<Product> findTop10ByStatusAndCreatedAtAfterOrderByLikesDesc(
            String status, LocalDateTime createdAt);
}
