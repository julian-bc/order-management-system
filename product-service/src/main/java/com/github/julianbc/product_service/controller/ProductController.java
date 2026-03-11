package com.github.julianbc.product_service.controller;

import com.github.julianbc.product_service.dtos.ProductRequest;
import com.github.julianbc.product_service.dtos.ProductResponse;
import com.github.julianbc.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private ProductService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveProduct(@RequestBody ProductRequest productRequest) {
        service.createProduct(productRequest);
    }

    @GetMapping
    public List<ProductResponse> findProducts() {
        return service.getAllProducts();
    }
}
