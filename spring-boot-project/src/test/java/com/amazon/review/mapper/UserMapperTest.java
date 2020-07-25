package com.amazon.review.mapper;

import com.amazon.review.dto.ParsedLineDto;
import com.amazon.review.dto.UserRegistrationDto;
import com.amazon.review.dto.UserResponseDto;
import com.amazon.review.model.User;
import com.amazon.review.model.UserAws;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserMapperTest {
    private final UserMapper userMapper = new UserMapper();
    private UserAws expected;

    @BeforeEach
    public void setUp() {
        expected = new UserAws();
        expected.setId("ABC123JKL4");
        expected.setProfileName("profile name");
    }

    @Test
    public void getUserFromParsedLineDto() {
        ParsedLineDto parsedLineDto = new ParsedLineDto();
        parsedLineDto.setUserId("ABC123JKL4");
        parsedLineDto.setProfileName("profile name");
        UserAws actual = userMapper.getUserAwsFromParsedLineDto(parsedLineDto);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void checkMappingFail() {
        ParsedLineDto parsedLineDto = new ParsedLineDto();
        parsedLineDto.setProfileName("profile name");
        UserAws actual = userMapper.getUserAwsFromParsedLineDto(parsedLineDto);
        Assertions.assertNotEquals(expected, actual);
    }

    @Test
    public void getUserResponseDto() {
        UserResponseDto expected = new UserResponseDto();
        expected.setProfileName("Profile name");
        UserResponseDto actual = userMapper
                .convertToResponseDto(new UserAws("Profile name"));
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getUserFromUserRegistrationDto() {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setLogin("login");
        userRegistrationDto.setPassword("password");
        User expected = new User();
        expected.setLogin(userRegistrationDto.getLogin());
        expected.setPassword(userRegistrationDto.getPassword());
        User actual = userMapper.convertToUser(userRegistrationDto);
        Assertions.assertEquals(expected, actual);
    }
}
