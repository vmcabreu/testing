package com.crai.platform.event.webbroker.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.crai.platform.event.webbroker.dto.AlertDto;
import com.crai.platform.event.webbroker.service.SenderService;
import com.crai.platform.starter.event.producer.EventTemplate;

@Component
@Slf4j
public class SenderServiceImpl implements SenderService {

   private static final String ROUTING_KEY_OVERRIDE = "CamelSpringRabbitmqRoutingOverrideKey";
   private static final String SENDER = "senderHandler";
    
   @Autowired
   EventTemplate producer;

   @Override
   public void sendEvent (AlertDto alert, String routingKey) {
      
      log.info("send event  to {} -> event -> {} routingKey -> {}", SENDER, alert, routingKey);
      producer.sendEvent(SENDER, alert, ROUTING_KEY_OVERRIDE, routingKey);
   }
}