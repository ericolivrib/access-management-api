package com.erico.accessmanagement.auth.service;

import com.erico.accessmanagement.business.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.erico.accessmanagement.business.model.User;

import static org.springframework.security.core.userdetails.User.withUsername;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("E-mail or password incorrect"));

        final String authorities = "ROLE_" + user.getRole().getLabel();

        return withUsername(email).password(user.getPassword())
                .authorities(authorities)
                .build();
    }
}
