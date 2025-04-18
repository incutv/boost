package com.example.boost.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demo")
public class DemoController {
    @GetMapping("/")
    public ResponseEntity<String> getAllPayments() {
        return ResponseEntity.ok("demo");
    }

    @GetMapping("/1")
    public ResponseEntity<String> demo1() {
        return ResponseEntity.ok("demo1");
    }
}
