package com.amazon.review.controller;

import com.amazon.review.dto.ReviewDeleteDto;
import com.amazon.review.dto.ReviewRequestDto;
import com.amazon.review.dto.ReviewResponseDto;
import com.amazon.review.dto.ReviewUpdateDto;
import com.amazon.review.mapper.ReviewMapper;
import com.amazon.review.model.Product;
import com.amazon.review.model.Review;
import com.amazon.review.model.User;
import com.amazon.review.service.ProductService;
import com.amazon.review.service.ReviewService;
import com.amazon.review.service.UserService;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reviews")
@Validated
@AllArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;
    private final UserService userService;
    private final ProductService productService;

    @GetMapping
    public List<String> getMostUsedWords(
            @RequestParam(defaultValue = "1000") @Min(1) int limit,
            @RequestParam(defaultValue = "0") @Min(0) int offset) {
        return reviewService.getMostUsedWords(limit, offset);
    }

    @DeleteMapping
    public void deleteReview(@RequestBody @Valid ReviewDeleteDto reviewDeleteDto) {
        reviewService.deleteById(reviewDeleteDto.getId());
    }

    @PostMapping
    public ReviewResponseDto addReview(@RequestBody @Valid ReviewRequestDto reviewRequestDto,
                                       Authentication authentication) {
        User user = userService.findByLogin(authentication.getName());
        Product product = productService.findById(reviewRequestDto.getProductId());
        Review review = reviewMapper.convertToReview(reviewRequestDto, user, product);
        return reviewMapper.convertToReviewResponseDto(reviewService.save(review));
    }

    @PutMapping
    public ReviewResponseDto updateReview(@RequestBody @Valid ReviewUpdateDto reviewUpdateDto,
                                          Authentication authentication) {
        Review review = reviewService.findByUserLoginAndSummary(authentication.getName(),
                reviewUpdateDto.getSummary());
        review.setText(reviewUpdateDto.getText());
        return reviewMapper.convertToReviewResponseDto(reviewService.save(review));
    }
}
