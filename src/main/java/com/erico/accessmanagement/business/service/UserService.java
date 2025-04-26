package com.erico.accessmanagement.business.service;

import com.erico.accessmanagement.business.dto.CreateUserDto;
import com.erico.accessmanagement.business.exception.EntityAlreadyExistsException;
import com.erico.accessmanagement.business.mapper.UserMapper;
import com.erico.accessmanagement.business.model.*;
import com.erico.accessmanagement.business.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional
    public UUID createUser(CreateUserDto createUserDto) {
        userRepository.findByEmail(createUserDto.email()).ifPresent((u) -> {
            throw new EntityAlreadyExistsException("User with email " + u.getEmail() + " already exists");
        });

        User user = userMapper.mapToEntity(createUserDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);
        return user.getId();
    }
}
