package kz.halyk.test.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "OpenAPI тестового проекта",
                version = "1.0.0",
                description = "Описание API",
                contact = @Contact(name = "Утегенов Ержан", email = "erzh896@gmail.com")
        )
)
public class OpenApiConfiguration {
}
