package com.crai.platform.event.webbroker.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.crai.platform.event.webbroker.dto.AlertDto;
import com.crai.platform.event.webbroker.service.ProcessAlertService;
import com.crai.platform.event.webbroker.service.SenderService;

@Component("receiverHandler")
@Slf4j
public class ReceiverHandler {
   
   @Autowired
   SenderService sender;

   @Autowired
   ProcessAlertService serviceAlert;

   @Handler
   public void receiveMessage(@Body AlertDto alert, Exchange exchange) {
       
      log.info("Alerta recibida ---> {} ", alert);
      
      String routingKey = serviceAlert.process(alert);
      
      sender.sendEvent(alert, routingKey);
   }
}
