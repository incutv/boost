package com.example.boost.product.controller;

import com.example.boost.product.entity.Product;
import com.example.boost.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * 모든 결제 내역 조회
     *
     * @return 모든 Payment 엔티티 리스트
     */
    @GetMapping("")
    public ResponseEntity<List<Product>> getAllPayments() {
        List<Product> products = productService.getAllProduct();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/bests")
    public ResponseEntity<List<Product>> getBestProduct() {
        List<Product> products = productService.getBestProductOriginal();
        return ResponseEntity.ok(products);
    }

}
