package com.crai.rabbitstarter.service;

import org.springframework.beans.factory.annotation.Autowired;
import reactor.rabbitmq.Receiver;

public class CraiRabbitTemplate {

    @Autowired
    Receiver receiver;


}
