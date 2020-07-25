package com.amazon.review.controller;

import com.amazon.review.dto.UserResponseDto;
import com.amazon.review.mapper.UserMapper;
import com.amazon.review.model.UserAws;
import com.amazon.review.service.UserAwsService;
import java.util.List;
import lombok.SneakyThrows;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {
    private static final String ENDPOINT = "/users";
    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserAwsService userService;
    @MockBean
    private UserMapper userMapper;

    @SneakyThrows
    @Test
    @WithMockUser(username = "admin", password = "1234", roles = "ADMIN")
    public void testGetMostActiveUsers() {
        UserAws firstUser = new UserAws("User A");
        UserAws secondUser = new UserAws("User B");
        UserResponseDto firstUserDto = new UserResponseDto(firstUser.getProfileName());
        UserResponseDto secondUserDto = new UserResponseDto(secondUser.getProfileName());
        int limit = 1000;
        int offset = 0;
        Mockito.when(userService.getMostActiveUsers(limit, offset))
                .thenReturn(List.of(firstUser, secondUser));
        Mockito.when(userMapper.convertToResponseDto(firstUser)).thenReturn(firstUserDto);
        Mockito.when(userMapper.convertToResponseDto(secondUser)).thenReturn(secondUserDto);
        mvc.perform(MockMvcRequestBuilders.get(ENDPOINT))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }

    @SneakyThrows
    @Test
    @WithMockUser
    public void isForbidden() {
        mvc.perform(MockMvcRequestBuilders.get(ENDPOINT))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}
