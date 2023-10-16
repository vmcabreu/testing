package com.crai.rabbitstarter.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("rabbitmq")
public class RabbitMqController<T> {

    public Flux<String> consumeRabbit(){
        return Flux.just("pepe","Juan");
    }
}
