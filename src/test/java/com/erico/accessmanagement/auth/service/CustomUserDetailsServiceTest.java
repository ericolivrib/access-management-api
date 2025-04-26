package com.erico.accessmanagement.auth.service;

import com.erico.accessmanagement.business.model.Role;
import com.erico.accessmanagement.business.model.User;
import com.erico.accessmanagement.business.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @InjectMocks
    CustomUserDetailsService underTest;

    @Mock
    UserRepository userRepository;

    @Captor
    ArgumentCaptor<String> emailCaptor;

    String email;

    User user;

    @BeforeEach
    void setUp() {
        email = "erico.ribeiro@email.com";
        user = User.builder()
                .id(UUID.randomUUID())
                .name("Érico Ribeiro")
                .email(email)
                .password(new BCryptPasswordEncoder().encode("123456"))
                .role(Role.USER)
                .approved(true)
                .build();
    }

    @Test
    void shouldReturnUserDetails() {
        // Arrange
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // Act
        UserDetails userDetails = underTest.loadUserByUsername(email);

        // Assert
        verify(userRepository, times(1)).findByEmail(emailCaptor.capture());

        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
        assertTrue(user.getApproved());
        userDetails.getAuthorities().forEach(a -> System.out.println(a.getAuthority()));
        System.out.println(user.getRole());
        assertEquals(Role.USER.name(), userDetails.getAuthorities().iterator().next().getAuthority());
    }


    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // Arrange
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        Exception ex = assertThrows(UsernameNotFoundException.class, () -> underTest.loadUserByUsername(email));

        // Assert
        verify(userRepository, times(1)).findByEmail(email);
        assertEquals("E-mail or password incorrect", ex.getMessage());
    }
}