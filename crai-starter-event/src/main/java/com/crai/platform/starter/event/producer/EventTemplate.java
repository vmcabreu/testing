package com.crai.platform.starter.event.producer;

import org.apache.camel.Message;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Map;

public class EventTemplate {

    @Autowired
    ProducerTemplate producer;

    public void sendEvent(String sender, Object event, Map<String, Object> headers){
        asyncSend(sender,event,headers);
    }

    public void sendEvent(String sender, Object event, String header,String value){
        asyncSend(sender,event, Collections.singletonMap(header,value));
    }

    public void sendEvent(String sender, Object event){
        asyncSend(sender,event,Collections.emptyMap());
    }

    public void sendEvent(String sender){
        asyncSend(sender,null,Collections.emptyMap());
    }

    private void asyncSend(String sender, Object body, Map<String, Object> headers){
        producer.asyncSend("direct:"+sender, exchange -> {
            Message in = exchange.getIn();
            in.setBody(body);
            if(headers!=null) {
                in.getHeaders().putAll(headers);
            }
        });
    }
}
