package com.crai.platform.event.webbroker;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication()
public class CraiWebBrokerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context =
                new SpringApplicationBuilder(CraiWebBrokerApplication.class)
                        .web(WebApplicationType.SERVLET)
                        .run(args);
    }
}
