package com.erico.accessmanagement.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Access Management API",
                version = "1.0",
                contact = @Contact(name = "Érico Ribeiro", email = "oliv.ericorib@gmail.com"),
                description = "Documentação da API de controle de acessos temporários"
        ),
        servers = {
                @Server(description = "Local Server", url = "http://localhost:8080/v1")
        }
)
@SecurityScheme(name = OpenApiConfiguration.SECURITY_SCHEME, description = "Autenticação via token JWT", scheme = "bearer", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", in = SecuritySchemeIn.HEADER)
public class OpenApiConfiguration {

    public static final String SECURITY_SCHEME = "bearerAuth";
}
