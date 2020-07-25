package com.amazon.review.mapper;

import com.amazon.review.dto.ParsedLineDto;
import com.amazon.review.dto.UserRegistrationDto;
import com.amazon.review.dto.UserResponseDto;
import com.amazon.review.model.User;
import com.amazon.review.model.UserAws;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserAws getUserAwsFromParsedLineDto(ParsedLineDto parsedLineDto) {
        UserAws user = new UserAws();
        user.setId(parsedLineDto.getUserId());
        user.setProfileName(parsedLineDto.getProfileName());
        return user;
    }

    public UserResponseDto convertToResponseDto(UserAws userAws) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setProfileName(userAws.getProfileName());
        return userResponseDto;
    }

    public User convertToUser(UserRegistrationDto userRegistrationDto) {
        User user = new User();
        user.setLogin(userRegistrationDto.getLogin());
        user.setPassword(userRegistrationDto.getPassword());
        return user;
    }
}
