package com.erico.accessmanagement.service;

import com.erico.accessmanagement.dto.CreateUserDto;
import com.erico.accessmanagement.exception.ResourceConflictException;
import com.erico.accessmanagement.exception.ResourceGoneException;
import com.erico.accessmanagement.exception.ResourceNotFoundException;
import com.erico.accessmanagement.mapper.UserMapper;
import com.erico.accessmanagement.model.ConfirmationCode;
import com.erico.accessmanagement.model.Role;
import com.erico.accessmanagement.model.User;
import com.erico.accessmanagement.model.UserStatus;
import com.erico.accessmanagement.repository.ConfirmationCodeRepository;
import com.erico.accessmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ConfirmationCodeRepository confirmationCodeRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final MailService mailService;

    private static final int CONFIRMATION_CODE_EXPIRATION_TIME_MS = 600000;

    @Transactional
    public UUID createUser(CreateUserDto createUserDto) {
        userRepository.findByEmail(createUserDto.email()).ifPresent((u) -> {
            throw new ResourceConflictException("User with email " + u.getEmail() + " already exists");
        });

        User user = userMapper.mapToEntity(createUserDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        user.setStatus(UserStatus.NOT_CONFIRMED);

        userRepository.save(user);

        ConfirmationCode confirmationCode = ConfirmationCode.builder()
                .user(user)
                .expiresIn(Instant.now().plusMillis(CONFIRMATION_CODE_EXPIRATION_TIME_MS))
                .confirmed(false)
                .build();

        confirmationCodeRepository.save(confirmationCode);

        mailService.sendMail(
                user.getEmail(),
                "Confirmação de cadastro",
                "Olá " + user.getName() + ", confirme seu cadastro acessando o link: http://localhost:8080/v1/users/confirm?code=" + confirmationCode.getId()
        );
        return user.getId();
    }

    @Transactional
    public void confirmUser(UUID codeId) {
        ConfirmationCode code = confirmationCodeRepository.findById(codeId)
                .orElseThrow(() -> new ResourceNotFoundException("Código de confirmação inválido"));

        if (code.isConfirmed()) {
            return;
        }

        if (code.getExpiresIn().isBefore(Instant.now())) {
            throw new ResourceGoneException("Usuário com prazo de confirmação expirado");
        }

        code.setConfirmed(true);
        confirmationCodeRepository.save(code);

        User user = code.getUser();
        user.setStatus(UserStatus.PENDING_APPROVAL);
        userRepository.save(user);
    }
}
