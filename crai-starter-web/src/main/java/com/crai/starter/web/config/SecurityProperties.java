package com.crai.starter.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@ConfigurationProperties(prefix = "app.security")
@Configuration
@Data
public class SecurityProperties {
    Boolean enabled;
}
