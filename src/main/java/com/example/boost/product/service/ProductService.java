package com.example.boost.product.service;

import com.example.boost.product.entity.Product;
import com.example.boost.product.repository.ProductRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class ProductService {

    private static final String BEST_PRODUCTS_CACHE_KEY = "bestProducts";

    private final ProductRepository productRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    private final ProductCacheService productCacheService;

    @Autowired
    public ProductService(ProductRepository productRepository, RedisTemplate<String, Object> redisTemplate,ProductCacheService productCacheService) {
        this.productRepository = productRepository;
        this.redisTemplate = redisTemplate;
        this.productCacheService = productCacheService;
    }

    /**
     * 모든 상품 조회
     * @return 모든 Product 엔티티 리스트
     */
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public List<Product> getBestProductOriginal() {
        List<Product> cachedProducts = productCacheService.getBestProductsFromCache();
        if (cachedProducts != null) {
            return cachedProducts;
        }

        List<Product> bestProducts = loadBestProductsFromDb();

        redisTemplate.opsForValue().set(BEST_PRODUCTS_CACHE_KEY, bestProducts, Duration.ofSeconds(120));

        return bestProducts;
    }

    private List<Product> loadBestProductsFromDb() {
        return productRepository.findTop10ByStatusAndCreatedAtAfterOrderByLikesDesc(
                "AVAILABLE", LocalDateTime.now().minusMonths(3));
    }


}
