package com.erico.accessmanagement.service;

import com.erico.accessmanagement.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.erico.accessmanagement.model.User;

import static org.springframework.security.core.userdetails.User.withUsername;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("E-mail or password incorrect"));

        String role = user.getRole().name();

        return withUsername(email).password(user.getPassword())
                .roles(role)
                .authorities(role)
                .build();
    }
}
