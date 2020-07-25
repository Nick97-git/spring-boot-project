package com.amazon.review.controller;

import com.amazon.review.dto.UserLoginDto;
import com.amazon.review.dto.UserRegistrationDto;
import com.amazon.review.mapper.UserMapper;
import com.amazon.review.model.User;
import com.amazon.review.security.AuthenticationService;
import com.amazon.review.security.jwt.JwtTokenProvider;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/registration")
    public void register(@RequestBody @Valid UserRegistrationDto userRegistrationDto) {
        User user = userMapper.convertToUser(userRegistrationDto);
        authenticationService.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid UserLoginDto userLoginDto) {
        User user = authenticationService.login(userLoginDto.getLogin(),
                userLoginDto.getPassword());
        String token = jwtTokenProvider.createToken(user.getLogin(), user.getRoles().stream()
                .map(role -> role.getRoleName().name())
                .collect(Collectors.toList()));
        return new ResponseEntity<>(Map.of("token", token), HttpStatus.OK);
    }
}
