package com.example.boost.product.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 상품 ID

    @Column(nullable = false)
    private String name; // 상품 이름

    @Column(columnDefinition = "TEXT")
    private String description; // 상품 설명

    @Column(name = "category_id")
    private Long categoryId; // 카테고리 ID (FK)

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price; // 상품 가격

    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity = 0; // 재고 수량 (기본값 0)

    @Column(nullable = false, length = 50)
    private String status = "AVAILABLE"; // 상품 상태 (기본값 AVAILABLE)

    @Column(name = "likes", nullable = false)
    private Integer likes = 0; // 좋아요 수 (기본값 0)

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; // 생성 날짜

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt; // 수정 날짜

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}

