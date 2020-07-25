package com.amazon.review.dto;

import lombok.Data;

@Data
public class ReviewResponseDto {
    private Long id;
    private String summary;
    private String text;
}
