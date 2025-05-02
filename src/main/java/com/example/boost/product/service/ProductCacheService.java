package com.example.boost.product.service;

import com.example.boost.product.entity.Product;
import com.example.boost.product.repository.ProductRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductCacheService {

    private static final String BEST_PRODUCTS_CACHE_KEY = "bestProducts";

    private final ProductRepository productRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public ProductCacheService(ProductRepository productRepository, RedisTemplate<String, Object> redisTemplate) {
        this.productRepository = productRepository;
        this.redisTemplate = redisTemplate;
    }

    // Resilience4j의 AOP는 단위 테스트에서 작동하지 않습니다
    @CircuitBreaker(name = "redisCB", fallbackMethod = "fallbackToNull")
    public List<Product> getBestProductsFromCache() {
        return (List<Product>) redisTemplate.opsForValue().get(BEST_PRODUCTS_CACHE_KEY);
    }

    public List<Product> fallbackToNull(Throwable t) {
        return productRepository.findTop10ByStatusAndCreatedAtAfterOrderByLikesDesc(
                "AVAILABLE", LocalDateTime.now().minusMonths(3));
    }
}
