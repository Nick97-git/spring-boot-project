package com.amazon.review.repository;

import com.amazon.review.model.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select r.text from Review r")
    List<String> findAllTexts();

    void deleteById(Long id);

    @Query("from Review r where r.summary = :summary and r.user.login = :login")
    Review findByUserLoginAndSummary(@Param("login") String login,
                                     @Param("summary") String summary);
}
