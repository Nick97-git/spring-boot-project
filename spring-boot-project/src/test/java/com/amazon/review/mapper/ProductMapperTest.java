package com.amazon.review.mapper;

import com.amazon.review.dto.ParsedLineDto;
import com.amazon.review.dto.ProductResponseDto;
import com.amazon.review.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductMapperTest {
    private final ProductMapper productMapper = new ProductMapper();

    @Test
    public void getProductFromParsedLineDto() {
        Product expected = new Product();
        expected.setId("ABC12O3JK4");
        ParsedLineDto parsedLineDto = new ParsedLineDto();
        parsedLineDto.setProductId("ABC12O3JK4");
        Product actual = productMapper.convertToProductFromParsedLineDto(parsedLineDto);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void checkMappingFail() {
        ParsedLineDto parsedLineDto = new ParsedLineDto();
        Assertions.assertThrows(NullPointerException.class, () -> {
            productMapper.convertToProductFromParsedLineDto(parsedLineDto);
        });
    }

    @Test
    public void getProductResponseDto() {
        ProductResponseDto expected = new ProductResponseDto();
        expected.setId("ABC123DFG");
        ProductResponseDto actual = productMapper
                .convertToProductResponseDto(new Product("ABC123DFG"));
        Assertions.assertEquals(actual, expected);
    }
}
