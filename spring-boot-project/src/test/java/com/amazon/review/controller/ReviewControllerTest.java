package com.amazon.review.controller;

import com.amazon.review.dto.ReviewDeleteDto;
import com.amazon.review.dto.ReviewRequestDto;
import com.amazon.review.dto.ReviewUpdateDto;
import com.amazon.review.model.Product;
import com.amazon.review.model.Review;
import com.amazon.review.repository.ProductRepository;
import com.amazon.review.repository.ReviewRepository;
import com.amazon.review.repository.UserRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.SneakyThrows;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ReviewControllerTest {
    private static final String ENDPOINT = "/reviews";
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    private Gson gson;

    @BeforeEach
    public void setUp() {
        gson = new Gson();
    }

    @SneakyThrows
    @Test
    @WithMockUser(username = "admin", password = "1234", roles = "ADMIN")
    public void testGetMostUsedProducts() {
        List<String> words = List.of("the", "i", "and", "a");
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(ENDPOINT).param("limit", "4")
                .param("offset", "0"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(words.size())))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        List<String> actual = gson.fromJson(content,
                new TypeToken<List<String>>() {}.getType());
        Assertions.assertEquals(words, actual);
    }

    @SneakyThrows
    @Test
    @WithMockUser
    public void isForbidden() {
        mvc.perform(MockMvcRequestBuilders.get(ENDPOINT))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @SneakyThrows
    @Test
    @WithMockUser(username = "user", password = "1234", roles = "USER")
    public void testUpdateReview() {
        Review review = new Review();
        review.setUser(userRepository.findByLogin("user"));
        review.setText("Old text");
        review.setSummary("Summary");
        reviewRepository.save(review);
        ReviewUpdateDto reviewUpdateDto = new ReviewUpdateDto();
        reviewUpdateDto.setSummary("Summary");
        reviewUpdateDto.setText("Text");
        String json = gson.toJson(reviewUpdateDto);
        mvc.perform(MockMvcRequestBuilders.put(ENDPOINT)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Review actual = reviewRepository.findById(500L).get();
        Assertions.assertEquals(reviewUpdateDto.getText(), actual.getText());
    }

    @SneakyThrows
    @Test
    @WithMockUser(username = "user", password = "1234", roles = "USER")
    public void testAddReview() {
        Product product = new Product();
        product.setId("ID");
        productRepository.save(product);
        ReviewRequestDto reviewRequestDto = new ReviewRequestDto();
        reviewRequestDto.setProductId(product.getId());
        reviewRequestDto.setText("Text");
        reviewRequestDto.setSummary("Summary");
        String json = gson.toJson(reviewRequestDto);
        mvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Review review = reviewRepository.findById(500L).get();
        Assertions.assertEquals(reviewRequestDto.getSummary(), review.getSummary());
        Assertions.assertEquals(reviewRequestDto.getText(), review.getText());
    }

    @SneakyThrows
    @Test
    @WithMockUser(username = "admin", password = "1234", roles = "ADMIN")
    public void testDeleteReview() {
        Review review = reviewRepository.findById(1L).get();
        Assertions.assertEquals(1L, review.getId());
        ReviewDeleteDto reviewDeleteDto = new ReviewDeleteDto();
        reviewDeleteDto.setId(1L);
        String json = gson.toJson(reviewDeleteDto);
        mvc.perform(MockMvcRequestBuilders.delete(ENDPOINT)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Assertions.assertThrows(NoSuchElementException.class,
                () -> reviewRepository.findById(1L).get());
    }
}
