package com.example.myselectshop.service;

import com.example.myselectshop.dto.ProductRequestDto;
import com.example.myselectshop.dto.ProductResponseDto;
import com.example.myselectshop.entity.Product;
import com.example.myselectshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
  private final ProductRepository productRepository;
  public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
    Product product = productRepository.save(new Product(productRequestDto));
    return new ProductResponseDto(product);
  }
}
