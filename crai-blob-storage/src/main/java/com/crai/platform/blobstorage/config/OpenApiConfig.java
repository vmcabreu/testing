package com.crai.platform.blobstorage.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
 
    @Bean
    public OpenAPI exampleOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                  .title("CRAI Template - Example Application API")
                  .description("This is a Spring Boot RESTful service using GCF starters for Spring Boot."));
    }



}
