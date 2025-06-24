package com.exashare.Exashare.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(
            new Info()
            .title("Exashare API")
            .version("1.0")
            .description("Documentación de la API de Exashare, una plataforma de arriendo de productos" +
                         "Incluyendo la creación, actualización y eliminación de arriendos, herramientas, usuarios, reseñas y membresias."));
    }
}
