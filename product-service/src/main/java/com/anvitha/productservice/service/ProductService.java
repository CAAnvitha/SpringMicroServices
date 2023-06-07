package com.anvitha.productservice.service;

import com.anvitha.productservice.dto.ProductRequest;
import com.anvitha.productservice.dto.ProductResponse;
import com.anvitha.productservice.model.Product;
import com.anvitha.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
//This will create constructor at run time
public class ProductService {

    private final ProductRepository productRepository;



    public void createProduct(ProductRequest productRequest){
        Product product= Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price((productRequest.getPrice()))
                .build();
        productRepository.save(product);

        log.info("Product {} is saved",product.getId());

    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapToProductResponse).toList();

    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price((product.getPrice()))
                .build();
    }


}
