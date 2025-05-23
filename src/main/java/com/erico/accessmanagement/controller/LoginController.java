package com.erico.accessmanagement.controller;

import com.erico.accessmanagement.dto.SignInDto;
import com.erico.accessmanagement.dto.JwtResponseDto;
import com.erico.accessmanagement.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/login")
public class LoginController implements LoginControllerSpec {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping
    public ResponseEntity<JwtResponseDto> signIn(@RequestBody SignInDto signInDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInDto.email(), signInDto.password()));

        User user = (User) authentication.getPrincipal();
        String token = jwtService.generateJwt(user);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new JwtResponseDto(token));
    }
}
