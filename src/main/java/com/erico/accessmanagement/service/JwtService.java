package com.erico.accessmanagement.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

import static com.erico.accessmanagement.config.JwtConfig.JWT_EXPIRATION_TIME;

@Service
@AllArgsConstructor
public class JwtService {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    public String generateJwt(User user) {
        String issuerClaim = "access-management";

        String scopesClaim = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(issuerClaim)
                .subject(user.getUsername())
                .expiresAt(Instant.now().plusSeconds(JWT_EXPIRATION_TIME))
                .claim("scopes", scopesClaim)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String getSubject(String token) {
        return jwtDecoder.decode(token).getSubject();
    }
}
