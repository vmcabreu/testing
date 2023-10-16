package com.crai.platform.event.webbroker.service;

import org.springframework.stereotype.Component;
import com.crai.platform.event.webbroker.dto.AlertDto;

@Component
public interface SenderService {

   /**
    * Envia el mensaje a las distintos routing key de rabbitmq
    * @param event
    */
   public void sendEvent (AlertDto event, String routingKey);
}