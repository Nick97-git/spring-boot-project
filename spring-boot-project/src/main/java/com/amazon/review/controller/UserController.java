package com.amazon.review.controller;

import com.amazon.review.dto.UserResponseDto;
import com.amazon.review.mapper.UserMapper;
import com.amazon.review.model.UserAws;
import com.amazon.review.service.UserAwsService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Validated
@AllArgsConstructor
public class UserController {
    private final UserAwsService userService;
    private final UserMapper userMapper;

    @GetMapping
    public List<UserResponseDto> getMostActiveUsers(
            @RequestParam(defaultValue = "1000") @Min(1) int limit,
            @RequestParam(defaultValue = "0") @Min(0) int offset) {
        List<UserAws> mostActiveUsers = userService.getMostActiveUsers(limit, offset);
        return mostActiveUsers.stream()
                .map(userMapper::convertToResponseDto)
                .collect(Collectors.toList());
    }
}
