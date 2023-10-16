package com.crai.platform.event.example.handler;

import com.crai.platform.event.example.dto.EventDto;
import com.crai.platform.event.example.dto.ParamEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.camel.component.springrabbit.SpringRabbitMQConstants;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component("senderHandler")
public class SenderHandler {
    @Handler
    public EventDto sendEvent(@Body ParamEvent paramMessage, Exchange exchange){
        log.info ("exchange.message.body ---> {}", exchange.getMessage().getBody());

//        exchange.getMessage().setHeader(SpringRabbitMQConstants.DELIVERY_MODE, MessageDeliveryMode.PERSISTENT);
        EventDto event = EventDto.builder().uid(UUID.randomUUID().toString())
                .name(paramMessage.getName())
                .body(paramMessage.getBody()).build();
        log.info("SenderHandler  ======> {} -> {}",paramMessage, event);
        return event;
    }
}
