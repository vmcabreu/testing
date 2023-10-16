package com.crai.platform.example.config;

import com.crai.platform.thingworxclient.api.ApiClient;
import com.crai.platform.thingworxclient.api.v1.UserInfoApi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class IotClientConfig {
    @Bean
    ApiClient apiClient (@Qualifier("ioTRestTemplate")RestTemplate restTemplate){
        return new ApiClient(restTemplate);
    }

    @Bean
    UserInfoApi userInfoApi(ApiClient apiClient){
        return new UserInfoApi(apiClient);
    }
}
