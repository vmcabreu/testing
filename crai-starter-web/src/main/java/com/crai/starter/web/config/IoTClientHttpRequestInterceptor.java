package com.crai.starter.web.config;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;



@NoArgsConstructor

public class IoTClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    private static final String HEADER_NAME="AppKey";


    private String headerValue;

    public IoTClientHttpRequestInterceptor(String headerValue) {
        this.headerValue = headerValue;
    }


    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        request.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        request.getHeaders().add(HEADER_NAME,headerValue);
        return execution.execute(request,body);
    }
}
