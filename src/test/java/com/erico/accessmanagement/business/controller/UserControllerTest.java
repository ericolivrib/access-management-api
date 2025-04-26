package com.erico.accessmanagement.business.controller;


import com.erico.accessmanagement.business.dto.CreateUserDto;
import com.erico.accessmanagement.business.exception.EntityAlreadyExistsException;
import com.erico.accessmanagement.business.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.springframework.test.context.bean.override.mockito.MockitoBean;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserControllerTest {

    @MockitoBean
    UserService userService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    CreateUserDto createUserDto;

    @BeforeEach()
    void setUp() {
        createUserDto = new CreateUserDto("John Doe", "john.doe@test.com", "password123");
    }

    @Test
    void whenCreateUser_thenReturn201CreatedStatusWithLocationHeader() throws Exception {
        UUID userId = UUID.randomUUID();
        Mockito.when(userService.createUser(Mockito.eq(createUserDto))).thenReturn(userId);

        String requestBody = objectMapper.writeValueAsString(createUserDto);

        mockMvc.perform(post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(content().string(""));
    }

    @Test
    void whenCreateUser_thenThrow409ConflictStatusWithErrorMessage() throws Exception {
        String requestBody = objectMapper.writeValueAsString(createUserDto);
        String expectedErrorMessage = "User with email " + createUserDto.email() + " already exists";

        Mockito.doThrow(new EntityAlreadyExistsException(expectedErrorMessage))
                .when(userService)
                .createUser(Mockito.eq(createUserDto));

        mockMvc.perform(post("/v1/users").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value(expectedErrorMessage));
    }

    @Test
    void whenCreateUser_thenThrow422UnprocessableEntityStatusWithFieldErrors() throws Exception {
        CreateUserDto userWithWrongFields = new CreateUserDto("", "john.doe", "pass");
        String requestBody = objectMapper.writeValueAsString(userWithWrongFields);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/users").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*].field").value(Matchers.hasItems("name", "email", "password")));

    }
}