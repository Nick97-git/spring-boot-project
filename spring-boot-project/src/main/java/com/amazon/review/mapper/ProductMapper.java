package com.amazon.review.mapper;

import com.amazon.review.dto.ParsedLineDto;
import com.amazon.review.dto.ProductResponseDto;
import com.amazon.review.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product convertToProductFromParsedLineDto(ParsedLineDto parsedLineDto) {
        Product product = new Product();
        product.setId(parsedLineDto.getProductId());
        return product;
    }

    public ProductResponseDto convertToProductResponseDto(Product product) {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(product.getId());
        return productResponseDto;
    }
}
