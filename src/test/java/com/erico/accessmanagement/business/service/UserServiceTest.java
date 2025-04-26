package com.erico.accessmanagement.business.service;

import com.erico.accessmanagement.business.dto.NewUserDto;
import com.erico.accessmanagement.business.exception.EntityAlreadyExistsException;
import com.erico.accessmanagement.business.mapper.UserMapper;
import com.erico.accessmanagement.business.model.*;
import com.erico.accessmanagement.business.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService underTest;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @Captor
    ArgumentCaptor<User> userCaptor;

    @Nested
    class CreateUser {

        NewUserDto newUserDto;

        User mappedUser;

        User userWithExistentEmail;

        @BeforeEach
        void setUp() {
            newUserDto = new NewUserDto(
                    "John Doe",
                    "john.doe@test.com",
                    "password123"
            );

            mappedUser = User.builder()
                    .name(newUserDto.name())
                    .email(newUserDto.email())
                    .password(newUserDto.password())
                    .build();

            userWithExistentEmail = User.builder()
                    .id(UUID.randomUUID())
                    .name("Frank Ocean")
                    .email("frank.ocean@test.com")
                    .password("$2a$12$VR7TNH9.v0CEO5nagSMNZ.cBmrjMPeCt92h94q/3DagC70vBKpiPS")
                    .approved(true)
                    .role(Role.USER)
                    .build();
        }

        @Test
        void shouldCreateUser() {
            // Arrange
            when(userRepository.findByEmail(newUserDto.email()))
                    .thenReturn(Optional.empty());

            when(userMapper.mapToEntity(newUserDto))
                    .thenReturn(mappedUser);

            String hashedPassword = passwordEncoder.encode(mappedUser.getPassword());

            when(passwordEncoder.encode(mappedUser.getPassword()))
                    .thenReturn(hashedPassword);

            mappedUser.setRole(Role.USER);
            mappedUser.setPassword(passwordEncoder.encode(mappedUser.getPassword()));
            mappedUser.setId(UUID.randomUUID());
            mappedUser.setStatus(UserStatus.NOT_CONFIRMED);

            when(userRepository.save(mappedUser))
                    .thenReturn(mappedUser);

            // Act
            UUID userId = underTest.createUser(newUserDto);

            // Assert
            verify(userRepository).save(userCaptor.capture());
            assertNotNull(userCaptor.getValue().getId());
            assertEquals(userId, userCaptor.getValue().getId());
            assertEquals(Role.USER, userCaptor.getValue().getRole());
            assertEquals(mappedUser.getPassword(), userCaptor.getValue().getPassword());
            assertEquals(UserStatus.NOT_CONFIRMED, userCaptor.getValue().getStatus());
            assertNull(userCaptor.getValue().getApproved());
        }

        @Test
        void shouldThrowExceptionWhenEmailAlreadyExists() {
            // Arrange
            when(userRepository.findByEmail(newUserDto.email()))
                    .thenReturn(Optional.of(userWithExistentEmail));

            String expectedExceptionMessage = "User with email " + userWithExistentEmail.getEmail() + " already exists";

            // Act and Assert
            Throwable ex = assertThrows(EntityAlreadyExistsException.class, () -> underTest.createUser(newUserDto));

            // Assert
            verify(userRepository).findByEmail(newUserDto.email());
            assertEquals(expectedExceptionMessage, ex.getMessage());
        }
    }
}