package com.crai.platform.event.example.handler;

import com.crai.platform.event.example.dto.EventDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.springframework.stereotype.Component;

@Component("receiverHandler")
@Slf4j
public class ReceiverHandler {

    @Handler
    public void receiveMessage(@Body EventDto event, Exchange exchange){
        log.info("exchange --> {}",exchange.getAllProperties().toString() );

        log.info("receiverA ---> receive message >>>>> {}",event);
    }
}
