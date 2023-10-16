package com.crai.starter.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Configuration
public class RestTemplateConfig {

    @Value("${app.iot.key-value:no_added}")
    private String ioTHeaderValue;
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {

        RestTemplate restTemplate = builder.build();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        return restTemplate;
    }


    @Bean
    public IoTClientHttpRequestInterceptor ioTClientHttpRequestInterceptor() {
        return new IoTClientHttpRequestInterceptor(ioTHeaderValue);
    }

    @Bean("ioTRestTemplate")
    @ConditionalOnBean(IoTClientHttpRequestInterceptor.class)
    public RestTemplate ioTRestTemplate(RestTemplateBuilder builder,IoTClientHttpRequestInterceptor ioTClientHttpRequestInterceptor){
        RestTemplate restTemplate = builder
                .additionalInterceptors(ioTClientHttpRequestInterceptor)
                .build();

        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        return  restTemplate;

    }






}


