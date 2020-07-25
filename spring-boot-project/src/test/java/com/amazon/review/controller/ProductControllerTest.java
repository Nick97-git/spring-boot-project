package com.amazon.review.controller;

import com.amazon.review.dto.ProductResponseDto;
import com.amazon.review.mapper.ProductMapper;
import com.amazon.review.model.Product;
import com.amazon.review.service.ProductService;
import java.util.List;
import lombok.SneakyThrows;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProductControllerTest {
    private static final String ENDPOINT = "/products";
    @Autowired
    private MockMvc mvc;
    @MockBean
    private ProductService productService;
    @MockBean
    private ProductMapper productMapper;

    @SneakyThrows
    @Test
    @WithMockUser(username = "admin", password = "1234", roles = "ADMIN")
    public void testGetMostCommentedProducts() {
        Product firstProduct = new Product("ABC123AVC");
        Product secondProduct = new Product("ABC456AVC");
        Product thirdProduct = new Product("ABC789AVC");
        ProductResponseDto firstProductDto = new ProductResponseDto("ABC123AVC");
        ProductResponseDto secondProductDto = new ProductResponseDto("ABC456AVC");
        ProductResponseDto thirdProductDto = new ProductResponseDto("ABC789AVC");
        int limit = 1000;
        int offset = 0;
        Mockito.when(productService.getMostCommentedProducts(limit, offset))
                .thenReturn(List.of(firstProduct, secondProduct, thirdProduct));
        Mockito.when(productMapper.convertToProductResponseDto(firstProduct))
                .thenReturn(firstProductDto);
        Mockito.when(productMapper.convertToProductResponseDto(secondProduct))
                .thenReturn(secondProductDto);
        Mockito.when(productMapper.convertToProductResponseDto(thirdProduct))
                .thenReturn(thirdProductDto);
        mvc.perform(MockMvcRequestBuilders.get(ENDPOINT))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is("ABC456AVC")));
    }

    @SneakyThrows
    @Test
    @WithMockUser
    public void isForbidden() {
        mvc.perform(MockMvcRequestBuilders.get(ENDPOINT))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}
