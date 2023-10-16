package com.crai.platform.event.example.service;

import com.crai.platform.event.example.dto.ParamEvent;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.crai.platform.starter.event.producer.EventTemplate;

@Component
@Slf4j
public class SenderService {

	private static final String ROUTING_KEY_OVERRIDE = "CamelSpringRabbitmqRoutingOverrideKey";
    private static final String SENDER = "senderHandler";
    
    @Autowired
    EventTemplate producer;

    public void sendEvent (ParamEvent event){
        log.info("send event  to {} ---> event ---> {}",SENDER,event);
	    if (!StringUtils.isEmpty(event.getName())) {
           producer.sendEvent(SENDER, event, ROUTING_KEY_OVERRIDE, "notificaciones." + event.getName());
		} else {
           producer.sendEvent(SENDER, event, ROUTING_KEY_OVERRIDE, "notificaciones");
		}        
    }
}