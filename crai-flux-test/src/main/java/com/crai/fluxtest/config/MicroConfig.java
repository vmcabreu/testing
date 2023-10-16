package com.crai.fluxtest.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MicroConfig {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
