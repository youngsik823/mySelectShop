package com.example.myselectshop.service;

import com.example.myselectshop.dto.ProductMypriceRequestDto;
import com.example.myselectshop.dto.ProductRequestDto;
import com.example.myselectshop.dto.ProductResponseDto;
import com.example.myselectshop.entity.Product;
import com.example.myselectshop.entity.User;
import com.example.myselectshop.entity.UserRoleEnum;
import com.example.myselectshop.naver.dto.ItemDto;
import com.example.myselectshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
  private final ProductRepository productRepository;
  public static final int MIN_MY_PRICE = 100;

  public ProductResponseDto createProduct(ProductRequestDto productRequestDto, User user) {
    Product product = productRepository.save(new Product(productRequestDto, user));
    return new ProductResponseDto(product);
  }

  @Transactional
  public ProductResponseDto updateProduct(Long id, ProductMypriceRequestDto productMypriceRequestDto) {
    int myPrice = productMypriceRequestDto.getMyprice();
    if (myPrice < MIN_MY_PRICE) {
      throw new IllegalArgumentException("유효하지 않은 관심 가격입니다. 최소 " + MIN_MY_PRICE + "원 이상으로 설정해 주세요.");
    }
    Product product = productRepository.findById(id).orElseThrow(
        () -> new NullPointerException("해당 상품을 찾을 수 없습니다.")
    );
    product.update(productMypriceRequestDto);
    return new ProductResponseDto(product);
  }

  @Transactional(readOnly = true)
  public Page<ProductResponseDto> getProducts(User user, int page, int size, String sortBy, boolean isAsc) {
    Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
    Sort sort = Sort.by(direction, sortBy);
    Pageable pageable = PageRequest.of(page, size, sort);

    UserRoleEnum userRoleEnum = user.getRole();

    Page<Product> productList;
    if (userRoleEnum == UserRoleEnum.USER) {
      productList = productRepository.findAllByUser(user, pageable);
    }else  {
      productList = productRepository.findAll(pageable);
    }

    return productList.map(ProductResponseDto::new);
  }

  @Transactional
  public void updateBySearch(Long id, ItemDto itemDto) {
    Product product = productRepository.findById(id).orElseThrow(
        () -> new NullPointerException("해당 상품은 존재하지 않습니다.")
    );
    product.updateByItemDto(itemDto);
  }

  public List<ProductResponseDto> getAllProducts() {
    List<Product> productList = productRepository.findAll();
    List<ProductResponseDto> productResponseDtoList =  new ArrayList<>();
    for (Product product : productList) {
      productResponseDtoList.add(new ProductResponseDto(product));
    }
    return productResponseDtoList;
  }
}
