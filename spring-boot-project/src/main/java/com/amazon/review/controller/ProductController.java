package com.amazon.review.controller;

import com.amazon.review.dto.ProductResponseDto;
import com.amazon.review.mapper.ProductMapper;
import com.amazon.review.model.Product;
import com.amazon.review.service.ProductService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@Validated
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping
    public List<ProductResponseDto> getMostCommentedProducts(
            @RequestParam(defaultValue = "1000") @Min(1) int limit,
            @RequestParam(defaultValue = "0") @Min(0) int offset) {
        List<Product> products = productService.getMostCommentedProducts(limit, offset);
        return products.stream()
                .map(productMapper::convertToProductResponseDto)
                .collect(Collectors.toList());
    }
}
