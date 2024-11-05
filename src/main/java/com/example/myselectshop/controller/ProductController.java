package com.example.myselectshop.controller;

import com.example.myselectshop.dto.ProductMypriceRequestDto;
import com.example.myselectshop.dto.ProductRequestDto;
import com.example.myselectshop.dto.ProductResponseDto;
import com.example.myselectshop.security.UserDetailsImpl;
import com.example.myselectshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {
  private final ProductService productService;

  @PostMapping("/products")
  public ProductResponseDto createProduct(@RequestBody ProductRequestDto productRequestDto,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return productService.createProduct(productRequestDto, userDetails.getUser());
  }

  @PutMapping("/products/{id}")
  public ProductResponseDto updateProduct(@PathVariable Long id, @RequestBody ProductMypriceRequestDto productMypriceRequestDto) {
    return productService.updateProduct(id, productMypriceRequestDto);
  }

  @GetMapping("/products")
  public Page<ProductResponseDto> getProducts(
      @RequestParam("page") int page,
      @RequestParam("size") int size,
      @RequestParam("sortBy") String sortBy,
      @RequestParam("isAsc") boolean isAsc,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return productService.getProducts(
        userDetails.getUser(), page - 1, size, sortBy, isAsc);
  }

  @GetMapping("/admin/products")
  public List<ProductResponseDto> getAllProducts() {
    return productService.getAllProducts();
  }

  @PostMapping("/products/{productId}/folder")
  public void addFolder(@PathVariable Long productId,
                        @RequestParam Long folderId,
                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
    productService.addFolder(productId, folderId, userDetails.getUser());
  }
}
