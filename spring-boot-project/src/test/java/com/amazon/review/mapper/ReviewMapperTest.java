package com.amazon.review.mapper;

import com.amazon.review.dto.ParsedLineDto;
import com.amazon.review.dto.ReviewRequestDto;
import com.amazon.review.dto.ReviewResponseDto;
import com.amazon.review.model.Product;
import com.amazon.review.model.Review;
import com.amazon.review.model.User;
import com.amazon.review.model.UserAws;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReviewMapperTest {
    private final ReviewMapper reviewMapper = new ReviewMapper();
    private Review expected;

    @BeforeEach
    public void setUp() {
        expected = new Review();
        expected.setSummary("some summary");
        expected.setText("some text");
        expected.setScore(100);
        UserAws userAws = new UserAws();
        userAws.setProfileName("profile");
        expected.setUserAws(userAws);
        Product product = new Product();
        product.setId("ABC123");
        expected.setProduct(product);
    }

    @Test
    public void getReviewFromParsedLineDto() {
        ParsedLineDto parsedLineDto = new ParsedLineDto();
        parsedLineDto.setSummary("some summary");
        parsedLineDto.setText("some text");
        parsedLineDto.setScore(100);
        UserAws userAws = new UserAws();
        userAws.setProfileName("profile");
        Product product = new Product();
        product.setId("ABC123");
        Review actual = reviewMapper.getReviewFromParsedLineDto(parsedLineDto, userAws, product);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void checkMappingToReviewFromParsedLineDtoFail() {
        Review actual = reviewMapper.getReviewFromParsedLineDto(new ParsedLineDto(),
                null, null);
        Assertions.assertNotEquals(expected, actual);
    }

    @Test
    public void getReviewResponseDto() {
        ReviewResponseDto expected = new ReviewResponseDto();
        expected.setId(1L);
        expected.setText("Text");
        expected.setSummary("Summary");
        Review review = new Review();
        review.setId(expected.getId());
        review.setText(expected.getText());
        review.setSummary(expected.getSummary());
        ReviewResponseDto actual = reviewMapper.convertToReviewResponseDto(review);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void checkConvertToReview() {
        User user = new User();
        user.setLogin("login");
        Product product = new Product();
        product.setId("ABC123LAP");
        ReviewRequestDto reviewRequestDto = new ReviewRequestDto();
        reviewRequestDto.setText("Text");
        reviewRequestDto.setSummary("Summary");
        Review expected = new Review();
        expected.setUser(user);
        expected.setProduct(product);
        expected.setText(reviewRequestDto.getText());
        expected.setSummary(reviewRequestDto.getSummary());
        Review actual = reviewMapper.convertToReview(reviewRequestDto, user, product);
        Assertions.assertEquals(expected, actual);
    }
}
