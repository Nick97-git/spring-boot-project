package com.amazon.review.dto;

import com.amazon.review.annotation.PasswordsValueMatch;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
@PasswordsValueMatch
public class UserRegistrationDto {
    @NotBlank(message = "Login can't be null or blank!")
    private String login;
    private String password;
    private String repeatPassword;
}
