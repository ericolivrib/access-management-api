package com.erico.accessmanagement.auth.service;

import com.erico.accessmanagement.business.model.Role;
import com.erico.accessmanagement.business.model.RoleLabel;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @InjectMocks
    CustomUserDetailsService underTest;

    @Mock
    UserRepository userRepository;

    @Captor
    ArgumentCaptor<String> emailCaptor;

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldReturnUserDetails_withSuccess() {
        // Arrange
        String email = "erico.ribeiro@email.com";

        User user = User.builder()
                .id(UUID.randomUUID())
                .name("Érico Ribeiro")
                .email(email)
                .password(new BCryptPasswordEncoder().encode("123456"))
                .role(new Role(2, RoleLabel.COMMON, null))
                .approved(true)
                .build();

        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        // Act
        UserDetails userDetails = underTest.loadUserByUsername(email);

        // Assert
        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());

        verify(userRepository).findByEmail(emailCaptor.capture());
        assertEquals(email, emailCaptor.getValue());
        assertEquals(user.getPassword(), userDetails.getPassword());
        assertTrue(user.isApproved());
        assertTrue(userDetails.getAuthorities()
                .stream()
                .allMatch(authority ->
                        authority.getAuthority().equals("ROLE_" + user.getRole().getLabel())));
    }
}