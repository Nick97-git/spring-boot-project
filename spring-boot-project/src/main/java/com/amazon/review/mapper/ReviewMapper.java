package com.amazon.review.mapper;

import com.amazon.review.dto.ParsedLineDto;
import com.amazon.review.dto.ReviewRequestDto;
import com.amazon.review.dto.ReviewResponseDto;
import com.amazon.review.model.Product;
import com.amazon.review.model.Review;
import com.amazon.review.model.User;
import com.amazon.review.model.UserAws;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public Review getReviewFromParsedLineDto(ParsedLineDto parsedLineDto,
                                             UserAws userAws, Product product) {
        Review review = new Review();
        review.setUserAws(userAws);
        review.setProduct(product);
        review.setScore(parsedLineDto.getScore());
        review.setHelpfulnessDenominator(parsedLineDto.getHelpfulnessDenominator());
        review.setHelpfulnessNumerator(parsedLineDto.getHelpfulnessNumerator());
        review.setTime(parsedLineDto.getTime());
        review.setSummary(parsedLineDto.getSummary());
        review.setText(parsedLineDto.getText());
        return review;
    }

    public Review convertToReview(ReviewRequestDto reviewRequestDto,
                                  User user, Product product) {
        Review review = new Review();
        review.setUser(user);
        review.setText(reviewRequestDto.getText());
        review.setSummary(reviewRequestDto.getSummary());
        review.setProduct(product);
        return review;
    }

    public ReviewResponseDto convertToReviewResponseDto(Review review) {
        ReviewResponseDto reviewResponseDto = new ReviewResponseDto();
        reviewResponseDto.setId(review.getId());
        reviewResponseDto.setText(review.getText());
        reviewResponseDto.setSummary(review.getSummary());
        return reviewResponseDto;
    }
}
