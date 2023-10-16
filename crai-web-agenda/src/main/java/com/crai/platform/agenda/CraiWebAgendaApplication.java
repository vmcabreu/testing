package com.crai.platform.agenda;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication()
public class CraiWebAgendaApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context =
                new SpringApplicationBuilder(CraiWebAgendaApplication.class)
                        .web(WebApplicationType.SERVLET)
                        .run(args);
    }
}
