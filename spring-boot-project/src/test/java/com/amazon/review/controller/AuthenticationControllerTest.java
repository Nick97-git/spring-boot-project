package com.amazon.review.controller;

import com.amazon.review.dto.UserLoginDto;
import com.amazon.review.dto.UserRegistrationDto;
import com.amazon.review.model.User;
import com.amazon.review.repository.UserRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class AuthenticationControllerTest {
    private static final String AUTH_ERROR_MSG = "Incorrect username or password!!!";
    private static final String LOGIN_ENDPOINT = "/login";
    private static final String PASSWORDS_DONT_MATCH_ERROR =
            "Passwords don't match!";
    private static final String REGISTRATION_ENDPOINT = "/registration";
    @Autowired
    private MockMvc mvc;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    private Gson gson;
    private UserRegistrationDto userRegistrationDto;

    @BeforeEach
    public void setUp() {
        gson = new Gson();
        userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setLogin("login");
        userRegistrationDto.setPassword("password");
        userRegistrationDto.setRepeatPassword("password");
    }

    @SneakyThrows
    @Test
    public void checkIsUserExists() {
        String json = gson.toJson(userRegistrationDto);
        mvc.perform(MockMvcRequestBuilders.post(REGISTRATION_ENDPOINT)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        User expected = new User();
        expected.setLogin(userRegistrationDto.getLogin());
        expected.setPassword(userRegistrationDto.getPassword());
        expected.setId(3L);
        User actual = userRepository.findById(3L).get();
        Assertions.assertEquals(expected.getLogin(), actual.getLogin());
        Assertions.assertTrue(passwordEncoder.matches(expected.getPassword(),
                actual.getPassword()));
    }

    @SneakyThrows
    @Test
    public void checkPasswordsValueMatch() {
        userRegistrationDto.setRepeatPassword(null);
        String json = gson.toJson(userRegistrationDto);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(REGISTRATION_ENDPOINT)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Map<String,Object> map = gson.fromJson(content,
                new TypeToken<HashMap<String, Object>>(){}.getType());
        String error = ((ArrayList<String>) map.get("errors")).get(0);
        Assertions.assertEquals(PASSWORDS_DONT_MATCH_ERROR, error);
    }

    @Test
    public void isLoginSuccessful() {
        ResultMatcher matcher = MockMvcResultMatchers.status().isOk();
        String userName = "admin";
        Map<String,Object> map = getMap(userName, matcher);
        String token = (String) map.get("token");
        Assertions.assertNotNull(token);
    }

    @Test
    public void isLoginFailed() {
        ResultMatcher matcher = MockMvcResultMatchers.status().isBadRequest();
        String userName = "nick";
        Map<String,Object> map = getMap(userName, matcher);
        String error = (String) map.get("error");
        Assertions.assertNotNull(AUTH_ERROR_MSG, error);
    }

    @SneakyThrows
    private Map<String, Object> getMap(String userName, ResultMatcher matcher) {
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setLogin(userName);
        userLoginDto.setPassword("1234");
        String json = gson.toJson(userLoginDto);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(LOGIN_ENDPOINT)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(matcher)
                .andReturn();
        String content = result.getResponse().getContentAsString();
        return gson.fromJson(content, new TypeToken<HashMap<String, Object>>(){}.getType());
    }

}
