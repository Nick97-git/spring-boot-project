package com.amazon.review.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReviewUpdateDto {
    @NotBlank(message = "Summary can't be null or blank!")
    private String summary;
    @NotBlank(message = "Text can't be null or blank!")
    private String text;
}
