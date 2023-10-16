package com.crai.platform.watchlist;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication()
public class CraiWebWatchlistApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context =
                new SpringApplicationBuilder(CraiWebWatchlistApplication.class)
                        .web(WebApplicationType.SERVLET)
                        .run(args);
    }
}
