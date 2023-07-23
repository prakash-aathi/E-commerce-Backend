package com.shoppingcart.productservice;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shoppingcart.dto.ProductRequest;
import com.shoppingcart.dto.ProductResponse;
import com.shoppingcart.model.Product;
import com.shoppingcart.repository.ProductRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service @RequiredArgsConstructor @Slf4j
public class ProductService {
    
    private final ProductRepo productRepo;

    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepo.save(product);
        log.info("product {} saved successfully", product.getName());
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepo.findAll();
        return products.stream().map(product -> convertProductToProductResponse(product)).toList();
    }

    public ProductResponse convertProductToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .description(product.getDescription())
                .name(product.getName())
                .price(product.getPrice())
            .build();
    }
}
