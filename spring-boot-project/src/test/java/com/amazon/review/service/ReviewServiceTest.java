package com.amazon.review.service;

import com.amazon.review.repository.ReviewRepository;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class ReviewServiceTest {
    @Autowired
    private ReviewService reviewService;
    @MockBean
    private ReviewRepository reviewRepository;

    @BeforeEach
    public void setUp() {
        List<String> texts = List.of("My first text is about dog food",
                "My first text is about cat food");
        Mockito.when(reviewRepository.findAllTexts()).thenReturn(texts);
    }

    @Test
    public void checkGetMostCommentedWords() {
        int limit = 1000;
        int offset = 0;
        List<String> expected = reviewService.getMostUsedWords(limit, offset);
        List<String> actual = List.of("about", "is", "text", "my", "food", "first", "cat", "dog");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void checkWordsLimit() {
        int limit = 2;
        int offset = 0;
        List<String> expected = reviewService.getMostUsedWords(limit, offset);
        List<String> actual = List.of("about", "is");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void checkWordsOffset() {
        int limit = 2;
        int offset = 1;
        List<String> expected = reviewService.getMostUsedWords(limit, offset);
        List<String> actual = List.of("text", "my");
        Assertions.assertEquals(expected, actual);
    }
}
