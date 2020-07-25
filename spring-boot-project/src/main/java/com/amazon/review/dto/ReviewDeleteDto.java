package com.amazon.review.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewDeleteDto {
    @NotNull(message = "Id can't be null!")
    @Min(value = 0, message = "Id can't be less than 1!")
    private Long id;
}
