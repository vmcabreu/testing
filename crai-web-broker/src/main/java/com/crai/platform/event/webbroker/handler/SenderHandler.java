package com.crai.platform.event.webbroker.handler;

import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.springframework.stereotype.Component;
import com.crai.platform.event.webbroker.dto.AlertDto;

@Component("senderHandler")
public class SenderHandler {
   
   @Handler
   public AlertDto sendEvent(@Body AlertDto paramMessage, Exchange exchange){

      // TODO de momento no hace falta esto
/*      AlertDto event = AlertDto.builder().id(UUID.randomUUID().toString())
               .priority(paramMessage.getPriority())
               .user(paramMessage.getUser())
               .body(paramMessage.getBody()).build();
      log.info("SenderHandler  ======> {} -> {}",paramMessage, event);*/
      return paramMessage;
   }
}
