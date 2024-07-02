package com.denysgarbuz.microservice.controller;


import com.denysgarbuz.microservice.dto.ProductRequest;
import com.denysgarbuz.microservice.dto.ProductResponse;
import com.denysgarbuz.microservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest product){
        this.productService.createProduct(product);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProduct(){
        return this.productService.getAllProducts();
    }

}
