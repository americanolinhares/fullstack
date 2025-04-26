package com.natixis.catalog.backend.controller;

import com.natixis.catalog.backend.dto.UserRegistrationResponseDto;
import com.natixis.catalog.backend.dto.UserRequestDto;
import com.natixis.catalog.backend.service.AuthenticationService;
import com.natixis.catalog.backend.service.JwtService;
import com.natixis.catalog.backend.service.UserDetailsServiceImpl;
import com.natixis.catalog.backend.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @WithMockUser
    @Test
    void whenCreateUser_withValidData_shouldReturnCreated() throws Exception {
        // Given
        UserRegistrationResponseDto userRegistrationResponseDto = UserRegistrationResponseDto.builder().username("validUser").build();
        given(userService.register(any(UserRequestDto.class))).willReturn(userRegistrationResponseDto);

        // When & Then
        mockMvc.perform(post("/register")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"validUser\",\"password\":\"validPassword123\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is("validUser")));
    }

    @WithMockUser
    @Test
    void whenCreateUser_withInvalidData_shouldReturnBadRequest() throws Exception {
        // When & Then
        mockMvc.perform(post("/register")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"withoutPassword\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].message", is("Field: password - Error description: Password is required!")));
    }
}