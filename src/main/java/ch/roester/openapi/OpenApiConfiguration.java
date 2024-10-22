package ch.roester.openapi;

import ch.roester.security.SecurityConstants;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI configuration for the eCommerce API.
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "eCommerce API",
                version = "1.0.0",
                description = "API for managing eCommerce operations."
        ),
        security = @SecurityRequirement(name = SecurityConstants.AUTHORIZATION_HEADER_NAME),
        servers = {
                @Server(url = "http://localhost:8080", description = "Local server")
        }
)
public class OpenApiConfiguration {
}
