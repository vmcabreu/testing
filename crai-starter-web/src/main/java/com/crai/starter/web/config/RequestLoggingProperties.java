package com.crai.starter.web.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "app.logging.properties")
@Configuration
@Data
public class RequestLoggingProperties {

    Boolean includeQueryString = Boolean.FALSE;
    Boolean includePayload= Boolean.FALSE;
    Boolean includeHeaders=Boolean.FALSE;
    Integer maxPayloadLength =10000;
    String afterMessagePrefix = "---------------------> REQUEST DATA: ";
}
