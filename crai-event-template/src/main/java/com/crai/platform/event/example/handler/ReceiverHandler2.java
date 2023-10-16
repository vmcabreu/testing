package com.crai.platform.event.example.handler;

import com.crai.platform.event.example.dto.EventDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Body;
import org.apache.camel.Handler;
import org.springframework.stereotype.Component;

@Component("receiverHandler2")
@Slf4j
public class ReceiverHandler2 {

    @Handler
    public void receiveMessage(@Body EventDto event){

        log.info("receiverA2 ---> receive message >>>>> {}",event);
    }
}
