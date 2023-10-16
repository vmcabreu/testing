package com.crai.platform.starter.event.routes;

import com.crai.platform.starter.event.autoconfig.EventsProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.ExchangePattern;
import org.apache.camel.LoggingLevel;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import com.crai.platform.starter.event.type.EventTypeMgr;
import java.util.concurrent.ExecutorService;

public class EventRouteBuilder extends BaseRouteBuilder {

    @Autowired
    EventsProperties eventsProperties;

    @Autowired
    EndpointUrlBuilder endpointUrlBuilder;

    @Autowired
    EventTypeMgr eventTypeMgr;

    @Autowired
    ExecutorService senderExecutorService;
/*
    public String exchangeArgsBuilder() {
        Map<String,Object> exchArgs=new HashMap<>();

        exchArgs.put("arg.exchange.x-cache-size", this.xCacheSize);
        exchArgs.put("arg.exchange.x-cache-ttl", this.xCacheTTL);
        exchArgs.put("arg.exchange.x-cache-persistence", this.xCachePersistence);

        public String ctiRequestConcurrentConfig(){
            return "&concurrentConsumers="+ctiRequestConConsumers+"&threadPoolSize="+defaultThreadPoolSize;
        }

        public String defaultConcurrentConfig(){
            return "&concurrentConsumers="+ctiRequestConConsumers+"&threadPoolSize="+defaultThreadPoolSize;
        }

*/
    @Override
    public void configure() throws Exception {
        super.configure();

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        /////////////////////////
        //Event sender => Camel producer
        for (String sender: eventsProperties.getSenders().keySet()) {

            EventsProperties.EventDef eventDef=eventsProperties.getSenders().get(sender);
            log.info("=== Building route for sender {} with eventDef {}",sender,mapper.writeValueAsString(eventDef));

            from("direct:"+sender)
              .routeId("com.crai.platform.event.sender."+sender)
              .setExchangePattern(ExchangePattern.InOnly)
              .threads().executorService(senderExecutorService)
              .process((exchange) -> {
/* Por si necesitamos procesar el mensaje
//String id = exchange.getIn().getHeader("id").toString();
//String newRoutingKey = ROUTING_KEY_PREFIX + (Integer.valueOf(id.split(":")[MESSAGE_NUMBER_IND]) % ROUTING_KEYS_NUMBER);
            	    exchange.getIn().removeHeader("id"); 
log.info("HEADER: " + exchange.getIn().getHeaders());
exchange.getIn().setHeader("spring-rabbitmq.ROUTING_KEY", "crai.notification2.3");
exchange.getIn().setHeader("rabbitmq.ROUTING_KEY", "crai.notification2.3");

                  Map<String, Object> headers=exchange.getIn().getHeaders();
                  if(!headers.containsKey(EventHeaders.ROUTINGKEY) && eventDef.getRoutingKey()!=null){
                      headers.put(EventHeaders.ROUTINGKEY, eventDef.getRoutingKey());
                  }*/
              })
              .bean(sender)
              .marshal().json(JsonLibrary.Jackson)
              .log(LoggingLevel.TRACE,"==== ["+sender+"] Sending event ${body} ${headers}")
              /* Para enviar a distintos routingkey */
              //.dynamicRouter(method(EndpointUrlBuilder.class, "buildUrlDynamic"));
              .to(endpointUrlBuilder.buildUrl(eventDef));
              //.bean("eventSink");
        }

        //Event Receiver => Camel Consumer
        for (String receiver: eventsProperties.getReceivers().keySet()) {

            EventsProperties.EventDef eventDef=eventsProperties.getReceivers().get(receiver);
            log.info("=== Building route for receiver {} with eventDef {}",receiver,mapper.writeValueAsString(eventDef));

            from(endpointUrlBuilder.buildUrl(eventDef))
                    .routeId("com.crai.platform.event.receiver."+receiver)
                    .setExchangePattern(ExchangePattern.InOnly)
                    .unmarshal().json(JsonLibrary.Jackson, eventTypeMgr.resolveEventType(eventDef.getEventType()))
                    .log(LoggingLevel.TRACE,"==== ["+receiver+"] Received event ${body} ${headers}")
                    .bean(receiver);
                    //.bean("eventSink");
        }
        /////////////////////////
    }
}
