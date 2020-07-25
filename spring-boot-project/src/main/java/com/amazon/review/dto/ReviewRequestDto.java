package com.amazon.review.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReviewRequestDto {
    @NotBlank(message = "Summary can't be null or blank!")
    private String summary;
    @NotBlank(message = "Text can't be null or blank!")
    private String text;
    @NotBlank(message = "Product id can't be null or blank!")
    private String productId;
}
