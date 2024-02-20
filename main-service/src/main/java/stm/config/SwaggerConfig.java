package stm.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Bean;

@OpenAPIDefinition(
        info = @Info(
                title = "Tickets service",
                description = "сервис билетов", version = "1.0.0",
                contact = @Contact(
                        name = "Чугунов Сергей",
                        email = "chugunse@mail.ru"
                )
        )
)
public class SwaggerConfig {
}