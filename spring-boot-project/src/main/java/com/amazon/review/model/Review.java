package com.amazon.review.model;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int helpfulnessNumerator;
    private int helpfulnessDenominator;
    private int score;
    private LocalDateTime time;
    private String summary;
    @Column(length = 50000, columnDefinition = "text")
    private String text;
    @ManyToOne(fetch = FetchType.LAZY)
    private UserAws userAws;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Review review = (Review) o;
        return helpfulnessNumerator == review.helpfulnessNumerator
                && helpfulnessDenominator == review.helpfulnessDenominator
                && score == review.score
                && Objects.equals(id, review.id)
                && Objects.equals(time, review.time)
                && Objects.equals(summary, review.summary)
                && Objects.equals(text, review.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, helpfulnessNumerator,
                helpfulnessDenominator, score, time, summary, text);
    }
}
