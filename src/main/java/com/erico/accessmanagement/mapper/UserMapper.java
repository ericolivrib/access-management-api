package com.erico.accessmanagement.mapper;

import com.erico.accessmanagement.dto.CreateUserDto;
import com.erico.accessmanagement.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User mapToEntity(CreateUserDto dto) {
        return User.builder()
                .name(dto.name())
                .email(dto.email())
                .password(dto.password())
                .build();
    }
}
