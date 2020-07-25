package com.amazon.review.service;

import com.amazon.review.model.Review;
import java.util.List;

public interface ReviewService extends GenericService<Review> {

    List<String> getMostUsedWords(int limit, int offset);

    void deleteById(Long id);

    Review findByUserLoginAndSummary(String login, String summary);
}
