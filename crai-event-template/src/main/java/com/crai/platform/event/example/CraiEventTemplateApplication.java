package com.crai.platform.event.example;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication()
public class CraiEventTemplateApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context =
                new SpringApplicationBuilder(CraiEventTemplateApplication.class)
                        .web(WebApplicationType.SERVLET)
                        .run(args);
    }
}
