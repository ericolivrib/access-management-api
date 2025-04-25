package com.erico.accessmanagement.auth.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.*;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
public class JwtConfig {

    public static final int JWT_EXPIRATION_TIME = 86400;

    @Value("${api.jwt.private-key}")
    private RSAPrivateKey privateKey;

    @Value("${api.jwt.public-key}")
    private RSAPublicKey publicKey;

    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .build();
        ImmutableJWKSet<SecurityContext> jwkSet = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSet);
    }

    @Bean
    JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withPublicKey(publicKey)
                .build();

        OAuth2TokenValidator<Jwt> defaultValidator = JwtValidators.createDefault();

        jwtDecoder.setJwtValidator(token -> {
            OAuth2TokenValidatorResult result = defaultValidator.validate(token);

            if (result.hasErrors()) {
                return OAuth2TokenValidatorResult.failure(result.getErrors());
            }

            return OAuth2TokenValidatorResult.success();
        });

        return jwtDecoder;
    }
}
