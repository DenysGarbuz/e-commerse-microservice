package com.denysgarbuz.microservice.service;


import com.denysgarbuz.microservice.dto.ProductRequest;
import com.denysgarbuz.microservice.dto.ProductResponse;
import com.denysgarbuz.microservice.model.Product;
import com.denysgarbuz.microservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;


    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .build();

        this.productRepository.save(product);
        log.info("Product {} created", product.getId());
    }


    public List<ProductResponse> getAllProducts() {
        List<Product> products = this.productRepository.findAll();
        return products.stream()
                .map(this::toProductResponse)
                .toList();
    }

    private ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
    }
}
