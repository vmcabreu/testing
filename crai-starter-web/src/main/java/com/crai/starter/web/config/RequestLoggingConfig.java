package com.crai.starter.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class RequestLoggingConfig {
    @Autowired
    RequestLoggingProperties requestLoggingProperties;
    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter
                = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(requestLoggingProperties.includeQueryString);
        filter.setIncludePayload(requestLoggingProperties.includePayload);
        filter.setMaxPayloadLength(requestLoggingProperties.getMaxPayloadLength());
        filter.setIncludeHeaders(requestLoggingProperties.includeHeaders);
        filter.setAfterMessagePrefix(requestLoggingProperties.afterMessagePrefix);
        return filter;
    }
}
