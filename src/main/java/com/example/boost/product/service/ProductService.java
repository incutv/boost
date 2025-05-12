package com.example.boost.product.service;

import com.example.boost.product.entity.Product;
import com.example.boost.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;


@Service
@Slf4j
public class ProductService {

    private static final String BEST_PRODUCTS_CACHE_KEY = "bestProducts";

    private final ProductRepository productRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    private final ProductCacheService productCacheService;

    private final FcmService fcmService;

    @Autowired
    public ProductService(ProductRepository productRepository, RedisTemplate<String, Object> redisTemplate,ProductCacheService productCacheService,FcmService fcmService) {
        this.productRepository = productRepository;
        this.redisTemplate = redisTemplate;
        this.productCacheService = productCacheService;
        this.fcmService = fcmService;
    }

    public void alarm() {
        List<Product> products = productRepository.findAll().subList(0,10);
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        AtomicInteger counter = new AtomicInteger(0); // 호출 횟수 카운터

        for (Product product : products) {
            fcmService.sendNotification("e9Ap2NGtx_Ezf0Lb7-XqOK:APA91bHfij6-wBMqDFOKD1e99IgUnds7U_z7XINBF52McNXF_CNeqMmj4YZ4GDgzdRFEHyDCg7QD9PKSeT5znyqOYTIbAD7AEi8zRfgvyqvNNmQCu7kGQAA",product.getName(),product.getDescription());
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        log.info("총 sendAlarm 호출 수: {}", counter.get());
    }

    public CompletableFuture<Void> sendAlarm(Product product, AtomicInteger counter) {
        try {
            Thread.sleep(1L);
            log.info("sendAlarm #{}", product.getName());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return CompletableFuture.completedFuture(null);
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

        redisTemplate.opsForValue().set(BEST_PRODUCTS_CACHE_KEY, bestProducts, Duration.ofSeconds(20));

        return bestProducts;
    }

    private List<Product> loadBestProductsFromDb() {
        return productRepository.findTop10ByStatusAndCreatedAtAfterOrderByLikesDesc(
                "AVAILABLE", LocalDateTime.now().minusMonths(3));
    }


}
