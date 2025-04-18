package com.erico.accessmanagement.business.service;

import com.erico.accessmanagement.business.dto.NewUserDto;
import com.erico.accessmanagement.business.exception.EntityAlreadyExistsException;
import com.erico.accessmanagement.business.mapper.UserMapper;
import com.erico.accessmanagement.business.model.RegistrationStatus;
import com.erico.accessmanagement.business.model.Role;
import com.erico.accessmanagement.business.model.RoleLabel;
import com.erico.accessmanagement.business.model.User;
import com.erico.accessmanagement.business.repository.RoleRepository;
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
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional
    public UUID createUser(NewUserDto newUserDto) {
        userRepository.findByEmail(newUserDto.email()).ifPresent((u) -> {
            throw new EntityAlreadyExistsException("User with email " + u.getEmail() + " already exists");
        });

        Role commonRole = roleRepository.getReferenceByLabel(RoleLabel.USER);

        User user = userMapper.mapToEntity(newUserDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(commonRole);
        user.setRegistrationStatus(RegistrationStatus.NOT_CONFIRMED);

        userRepository.save(user);
        return user.getId();
    }
}
