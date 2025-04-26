package com.erico.accessmanagement.service;

import com.erico.accessmanagement.dto.CreateUserDto;
import com.erico.accessmanagement.exception.EntityAlreadyExistsException;
import com.erico.accessmanagement.mapper.UserMapper;
import com.erico.accessmanagement.model.Role;
import com.erico.accessmanagement.model.User;
import com.erico.accessmanagement.repository.UserRepository;
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
